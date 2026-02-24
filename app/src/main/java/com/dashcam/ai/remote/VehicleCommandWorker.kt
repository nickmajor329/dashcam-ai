package com.dashcam.ai.remote

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.BatteryManager
import android.provider.Settings
import androidx.camera.core.CameraSelector
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dashcam.ai.BuildConfig
import com.dashcam.ai.network.BackendApiClient
import com.dashcam.ai.pairing.PairingManager
import com.dashcam.ai.recording.RecordingService
import com.dashcam.ai.recording.RecordingStateManager
import org.json.JSONObject

class VehicleCommandWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    private val apiClient = BackendApiClient()
    private val pairingManager = PairingManager(appContext)
    private val recordingStateManager = RecordingStateManager(appContext)

    override suspend fun doWork(): Result {
        val pairing = pairingManager.snapshot()
        if (pairing.vehicleId.isBlank()) return Result.success()

        val payload = JSONObject()
            .put("vehicle_id", pairing.vehicleId)
            .put("source_device", sourceDeviceId())

        val response = apiClient.postJson("/api/v1/vehicle/commands/next", payload)
        if (!response.success) return Result.retry()

        val body = runCatching { JSONObject(response.body) }.getOrNull() ?: return Result.success()
        if (!body.optBoolean("has_command", false)) return Result.success()

        val commandId = body.optString("command_id")
        val commandType = body.optString("command_type")
        val commandPayload = body.optJSONObject("payload") ?: JSONObject()
        val outcome = executeCommand(commandType, commandPayload, pairing.vehicleId)

        if (commandId.isNotBlank()) {
            postCommandResult(commandId, outcome)
        }
        if (outcome.rebootAfterAck) {
            rebootAppProcess()
        }
        return Result.success()
    }

    private fun executeCommand(commandType: String, payload: JSONObject, vehicleId: String): CommandOutcome {
        return when (commandType) {
            "SET_LENS" -> {
                val lens = payload.optString("lens")
                if (lens != "front" && lens != "back") {
                    return CommandOutcome.failed("Invalid lens payload")
                }
                val lensFacing = if (lens == "front") CameraSelector.LENS_FACING_FRONT else CameraSelector.LENS_FACING_BACK
                val intent = Intent(applicationContext, RecordingService::class.java).apply {
                    action = RecordingService.ACTION_SET_LENS
                    putExtra(RecordingService.EXTRA_LENS_FACING, lensFacing)
                }
                ContextCompat.startForegroundService(applicationContext, intent)
                CommandOutcome.ok("Lens switched to $lens")
            }

            "START_RECORDING" -> {
                val intent = Intent(applicationContext, RecordingService::class.java).apply {
                    action = RecordingService.ACTION_START
                }
                ContextCompat.startForegroundService(applicationContext, intent)
                CommandOutcome.ok("Recording start requested")
            }

            "STOP_RECORDING" -> {
                val intent = Intent(applicationContext, RecordingService::class.java).apply {
                    action = RecordingService.ACTION_STOP
                }
                applicationContext.startService(intent)
                CommandOutcome.ok("Recording stop requested")
            }

            "LIGHT_RELAY" -> {
                val desired = payload.optString("state")
                if (desired != "on" && desired != "off") {
                    return CommandOutcome.failed("Invalid light relay payload")
                }
                val enabled = desired == "on"
                val torchResult = setTorch(enabled)
                if (torchResult) {
                    CommandOutcome.ok("Torch set $desired")
                } else {
                    CommandOutcome.unsupported("Torch hardware unavailable")
                }
            }

            "HORN_RELAY" -> {
                val durationMs = payload.optInt("duration_ms", 1200).coerceIn(200, 5000)
                val tone = ToneGenerator(AudioManager.STREAM_ALARM, 100)
                try {
                    tone.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, durationMs)
                } finally {
                    tone.release()
                }
                CommandOutcome.ok("Horn pulse sent", JSONObject().put("duration_ms", durationMs))
            }

            "HEALTH_PING" -> {
                val note = payload.optString("note")
                val ok = postHealthPing(vehicleId, note)
                if (ok) CommandOutcome.ok("Health posted") else CommandOutcome.failed("Health post failed")
            }

            "REBOOT_APP" -> {
                CommandOutcome.ok("App reboot scheduled", rebootAfterAck = true)
            }

            else -> CommandOutcome.unsupported("Unknown command $commandType")
        }
    }

    private fun postCommandResult(commandId: String, outcome: CommandOutcome) {
        val payload = JSONObject()
            .put("command_id", commandId)
            .put("status", outcome.status)
            .put("message", outcome.message)
            .put("executed_at", System.currentTimeMillis())
            .put("result_payload", outcome.resultPayload)
        apiClient.postJson("/api/v1/vehicle/commands/result", payload)
    }

    private fun postHealthPing(vehicleId: String, note: String): Boolean {
        val batteryPct = currentBatteryPct()
        val payload = JSONObject()
            .put("vehicle_id", vehicleId)
            .put("source_device", sourceDeviceId())
            .put("recording_active", recordingStateManager.isRecordingActive())
            .put("free_bytes", applicationContext.filesDir.usableSpace)
            .put("battery_pct", if (batteryPct == null) JSONObject.NULL else batteryPct)
            .put("app_version", BuildConfig.VERSION_NAME)
            .put("note", note)
        return apiClient.postJson("/api/v1/vehicle/health", payload).success
    }

    private fun currentBatteryPct(): Int? {
        val batteryIntent = applicationContext.registerReceiver(
            null,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        ) ?: return null
        val level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        if (level < 0 || scale <= 0) return null
        return ((level.toFloat() / scale.toFloat()) * 100f).toInt()
    }

    private fun setTorch(enabled: Boolean): Boolean {
        val manager = applicationContext.getSystemService(CameraManager::class.java) ?: return false
        val cameraId = manager.cameraIdList.firstOrNull { id ->
            val chars = manager.getCameraCharacteristics(id)
            chars.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
        } ?: return false

        return runCatching {
            manager.setTorchMode(cameraId, enabled)
            true
        }.getOrDefault(false)
    }

    private fun rebootAppProcess() {
        val launchIntent = applicationContext.packageManager.getLaunchIntentForPackage(applicationContext.packageName)
            ?: return
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        val flags = PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            9271,
            launchIntent,
            flags
        )
        val alarmManager = applicationContext.getSystemService(AlarmManager::class.java)
        alarmManager?.setExact(AlarmManager.RTC, System.currentTimeMillis() + 1200, pendingIntent)
        android.os.Process.killProcess(android.os.Process.myPid())
    }

    private fun sourceDeviceId(): String {
        return Settings.Secure.getString(applicationContext.contentResolver, Settings.Secure.ANDROID_ID).orEmpty()
    }

    private data class CommandOutcome(
        val status: String,
        val message: String,
        val resultPayload: JSONObject = JSONObject(),
        val rebootAfterAck: Boolean = false
    ) {
        companion object {
            fun ok(message: String, payload: JSONObject = JSONObject(), rebootAfterAck: Boolean = false): CommandOutcome {
                return CommandOutcome(status = "OK", message = message, resultPayload = payload, rebootAfterAck = rebootAfterAck)
            }

            fun failed(message: String): CommandOutcome {
                return CommandOutcome(status = "FAILED", message = message)
            }

            fun unsupported(message: String): CommandOutcome {
                return CommandOutcome(status = "UNSUPPORTED", message = message)
            }
        }
    }
}

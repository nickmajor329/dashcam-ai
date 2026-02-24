package com.dashcam.ai.alerts

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dashcam.ai.BuildConfig
import com.dashcam.ai.data.EventRepository
import com.dashcam.ai.privacy.ClipCryptoManager
import java.io.File

class AlertUploadWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    private val repository = EventRepository(appContext)
    private val apiClient = AlertApiClient(appContext)
    private val clipCryptoManager = ClipCryptoManager()

    override suspend fun doWork(): Result {
        val eventId = inputData.getLong(KEY_EVENT_ID, -1L)
        val eventType = inputData.getString(KEY_EVENT_TYPE).orEmpty()
        val clipPath = inputData.getString(KEY_CLIP_PATH).orEmpty()
        val createdAtMs = inputData.getLong(KEY_CREATED_AT_MS, 0L)
        val ownerId = inputData.getString(KEY_OWNER_ID).orEmpty()
        val vehicleId = inputData.getString(KEY_VEHICLE_ID).orEmpty()
        val appEnabled = inputData.getBoolean(KEY_APP_ENABLED, true)
        val emailEnabled = inputData.getBoolean(KEY_EMAIL_ENABLED, false)
        val smsEnabled = inputData.getBoolean(KEY_SMS_ENABLED, false)
        val appDeviceId = inputData.getString(KEY_APP_DEVICE_ID).orEmpty()
        val emailAddress = inputData.getString(KEY_EMAIL_ADDRESS).orEmpty()
        val phoneNumber = inputData.getString(KEY_PHONE_NUMBER).orEmpty()
        val blurFaces = inputData.getBoolean(KEY_BLUR_FACES, false)
        val blurPlates = inputData.getBoolean(KEY_BLUR_PLATES, false)

        if (eventId <= 0 || eventType.isBlank() || clipPath.isBlank() || createdAtMs <= 0) {
            return Result.failure()
        }

        if (!File(clipPath).exists()) {
            return Result.failure()
        }
        if (BuildConfig.ALERT_API_BASE_URL.isBlank()) {
            return Result.failure()
        }

        val resolvedClipPath = if (clipPath.endsWith(".enc")) {
            val tempFile = File(applicationContext.cacheDir, "event_${eventId}_${System.currentTimeMillis()}.mp4")
            if (!clipCryptoManager.decryptFile(File(clipPath), tempFile)) {
                tempFile.delete()
                return Result.retry()
            }
            tempFile.absolutePath
        } else {
            clipPath
        }

        val uploaded = runCatching {
            apiClient.uploadEvent(
                eventId = eventId,
                eventType = eventType,
                clipPath = resolvedClipPath,
                createdAtMs = createdAtMs,
                ownerId = ownerId,
                vehicleId = vehicleId,
                appEnabled = appEnabled,
                emailEnabled = emailEnabled,
                smsEnabled = smsEnabled,
                appDeviceId = appDeviceId,
                emailAddress = emailAddress,
                phoneNumber = phoneNumber,
                blurFaces = blurFaces,
                blurPlates = blurPlates
            )
        }.getOrDefault(false)

        if (resolvedClipPath != clipPath) {
            runCatching { File(resolvedClipPath).delete() }
        }

        return if (uploaded) {
            repository.markUploaded(eventId)
            Result.success()
        } else {
            Result.retry()
        }
    }

    companion object {
        const val KEY_EVENT_ID = "event_id"
        const val KEY_EVENT_TYPE = "event_type"
        const val KEY_CLIP_PATH = "clip_path"
        const val KEY_CREATED_AT_MS = "created_at_ms"
        const val KEY_OWNER_ID = "owner_id"
        const val KEY_VEHICLE_ID = "vehicle_id"
        const val KEY_APP_ENABLED = "app_enabled"
        const val KEY_EMAIL_ENABLED = "email_enabled"
        const val KEY_SMS_ENABLED = "sms_enabled"
        const val KEY_APP_DEVICE_ID = "app_device_id"
        const val KEY_EMAIL_ADDRESS = "email_address"
        const val KEY_PHONE_NUMBER = "phone_number"
        const val KEY_BLUR_FACES = "blur_faces"
        const val KEY_BLUR_PLATES = "blur_plates"
    }
}

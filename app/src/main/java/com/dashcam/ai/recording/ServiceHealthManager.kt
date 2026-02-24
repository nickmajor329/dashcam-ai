package com.dashcam.ai.recording

import android.content.Context

data class ServiceHealthSnapshot(
    val recordingActive: Boolean,
    val freeBytes: Long,
    val batteryPct: Int?,
    val batteryTempTenthsC: Int?,
    val cameraFailureCount: Int,
    val lastHealthMessage: String,
    val lastUpdatedMs: Long
)

class ServiceHealthManager(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun snapshot(): ServiceHealthSnapshot {
        val batteryPct = if (prefs.contains(KEY_BATTERY_PCT)) prefs.getInt(KEY_BATTERY_PCT, 0) else null
        val batteryTemp = if (prefs.contains(KEY_BATTERY_TEMP_TENTHS_C)) prefs.getInt(KEY_BATTERY_TEMP_TENTHS_C, 0) else null
        return ServiceHealthSnapshot(
            recordingActive = prefs.getBoolean(KEY_RECORDING_ACTIVE, false),
            freeBytes = prefs.getLong(KEY_FREE_BYTES, 0L),
            batteryPct = batteryPct,
            batteryTempTenthsC = batteryTemp,
            cameraFailureCount = prefs.getInt(KEY_CAMERA_FAILURE_COUNT, 0),
            lastHealthMessage = prefs.getString(KEY_LAST_MESSAGE, "") ?: "",
            lastUpdatedMs = prefs.getLong(KEY_LAST_UPDATED_MS, 0L)
        )
    }

    fun update(
        recordingActive: Boolean? = null,
        freeBytes: Long? = null,
        batteryPct: Int? = null,
        hasBatteryPct: Boolean = false,
        batteryTempTenthsC: Int? = null,
        hasBatteryTempTenthsC: Boolean = false,
        cameraFailureCount: Int? = null,
        lastHealthMessage: String? = null
    ) {
        prefs.edit().apply {
            if (recordingActive != null) putBoolean(KEY_RECORDING_ACTIVE, recordingActive)
            if (freeBytes != null) putLong(KEY_FREE_BYTES, freeBytes)
            if (hasBatteryPct) {
                if (batteryPct != null) putInt(KEY_BATTERY_PCT, batteryPct) else remove(KEY_BATTERY_PCT)
            }
            if (hasBatteryTempTenthsC) {
                if (batteryTempTenthsC != null) putInt(KEY_BATTERY_TEMP_TENTHS_C, batteryTempTenthsC) else remove(KEY_BATTERY_TEMP_TENTHS_C)
            }
            if (cameraFailureCount != null) putInt(KEY_CAMERA_FAILURE_COUNT, cameraFailureCount)
            if (lastHealthMessage != null) putString(KEY_LAST_MESSAGE, lastHealthMessage)
            putLong(KEY_LAST_UPDATED_MS, System.currentTimeMillis())
        }.apply()
    }

    companion object {
        private const val PREFS_NAME = "service_health_state"
        private const val KEY_RECORDING_ACTIVE = "recording_active"
        private const val KEY_FREE_BYTES = "free_bytes"
        private const val KEY_BATTERY_PCT = "battery_pct"
        private const val KEY_BATTERY_TEMP_TENTHS_C = "battery_temp_tenths_c"
        private const val KEY_CAMERA_FAILURE_COUNT = "camera_failure_count"
        private const val KEY_LAST_MESSAGE = "last_message"
        private const val KEY_LAST_UPDATED_MS = "last_updated_ms"
    }
}

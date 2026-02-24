package com.dashcam.ai.settings

import android.content.Context
import androidx.camera.core.CameraSelector

data class AppSettings(
    val defaultLensFacing: Int,
    val segmentDurationSeconds: Int,
    val maxStorageGb: Int,
    val idleDetectionEnabled: Boolean,
    val idleThresholdMinutes: Int,
    val defaultDimScreen: Boolean,
    val defaultBlackScreen: Boolean
)

class AppSettingsManager(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun snapshot(): AppSettings {
        val lens = prefs.getInt(KEY_DEFAULT_LENS, CameraSelector.LENS_FACING_BACK)
        val safeLens = if (lens == CameraSelector.LENS_FACING_FRONT) {
            CameraSelector.LENS_FACING_FRONT
        } else {
            CameraSelector.LENS_FACING_BACK
        }

        return AppSettings(
            defaultLensFacing = safeLens,
            segmentDurationSeconds = prefs.getInt(KEY_SEGMENT_SECONDS, 60).coerceIn(15, 300),
            maxStorageGb = prefs.getInt(KEY_MAX_STORAGE_GB, 8).coerceIn(1, 64),
            idleDetectionEnabled = prefs.getBoolean(KEY_IDLE_ENABLED, true),
            idleThresholdMinutes = prefs.getInt(KEY_IDLE_MINUTES, 5).coerceIn(1, 60),
            defaultDimScreen = prefs.getBoolean(KEY_DEFAULT_DIM, false),
            defaultBlackScreen = prefs.getBoolean(KEY_DEFAULT_BLACK, false)
        )
    }

    fun save(
        defaultLensFacing: Int,
        segmentDurationSeconds: Int,
        maxStorageGb: Int,
        idleDetectionEnabled: Boolean,
        idleThresholdMinutes: Int,
        defaultDimScreen: Boolean,
        defaultBlackScreen: Boolean
    ) {
        val safeLens = if (defaultLensFacing == CameraSelector.LENS_FACING_FRONT) {
            CameraSelector.LENS_FACING_FRONT
        } else {
            CameraSelector.LENS_FACING_BACK
        }

        prefs.edit()
            .putInt(KEY_DEFAULT_LENS, safeLens)
            .putInt(KEY_SEGMENT_SECONDS, segmentDurationSeconds.coerceIn(15, 300))
            .putInt(KEY_MAX_STORAGE_GB, maxStorageGb.coerceIn(1, 64))
            .putBoolean(KEY_IDLE_ENABLED, idleDetectionEnabled)
            .putInt(KEY_IDLE_MINUTES, idleThresholdMinutes.coerceIn(1, 60))
            .putBoolean(KEY_DEFAULT_DIM, defaultDimScreen)
            .putBoolean(KEY_DEFAULT_BLACK, defaultBlackScreen)
            .apply()
    }

    companion object {
        private const val PREFS_NAME = "app_settings"
        private const val KEY_DEFAULT_LENS = "default_lens"
        private const val KEY_SEGMENT_SECONDS = "segment_seconds"
        private const val KEY_MAX_STORAGE_GB = "max_storage_gb"
        private const val KEY_IDLE_ENABLED = "idle_enabled"
        private const val KEY_IDLE_MINUTES = "idle_minutes"
        private const val KEY_DEFAULT_DIM = "default_dim"
        private const val KEY_DEFAULT_BLACK = "default_black"
    }
}

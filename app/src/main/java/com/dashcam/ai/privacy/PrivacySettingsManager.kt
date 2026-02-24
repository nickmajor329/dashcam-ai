package com.dashcam.ai.privacy

import android.content.Context

data class PrivacySettings(
    val blurFaces: Boolean,
    val blurPlates: Boolean,
    val encryptEventClips: Boolean,
    val retentionLowDays: Int,
    val retentionMediumDays: Int,
    val retentionHighDays: Int
)

class PrivacySettingsManager(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun snapshot(): PrivacySettings {
        return PrivacySettings(
            blurFaces = prefs.getBoolean(KEY_BLUR_FACES, false),
            blurPlates = prefs.getBoolean(KEY_BLUR_PLATES, false),
            encryptEventClips = prefs.getBoolean(KEY_ENCRYPT_EVENT_CLIPS, false),
            retentionLowDays = prefs.getInt(KEY_RETENTION_LOW_DAYS, 2).coerceIn(1, 30),
            retentionMediumDays = prefs.getInt(KEY_RETENTION_MEDIUM_DAYS, 7).coerceIn(1, 60),
            retentionHighDays = prefs.getInt(KEY_RETENTION_HIGH_DAYS, 21).coerceIn(1, 180)
        )
    }

    fun save(settings: PrivacySettings) {
        prefs.edit()
            .putBoolean(KEY_BLUR_FACES, settings.blurFaces)
            .putBoolean(KEY_BLUR_PLATES, settings.blurPlates)
            .putBoolean(KEY_ENCRYPT_EVENT_CLIPS, settings.encryptEventClips)
            .putInt(KEY_RETENTION_LOW_DAYS, settings.retentionLowDays.coerceIn(1, 30))
            .putInt(KEY_RETENTION_MEDIUM_DAYS, settings.retentionMediumDays.coerceIn(1, 60))
            .putInt(KEY_RETENTION_HIGH_DAYS, settings.retentionHighDays.coerceIn(1, 180))
            .apply()
    }

    companion object {
        private const val PREFS_NAME = "privacy_settings"
        private const val KEY_BLUR_FACES = "blur_faces"
        private const val KEY_BLUR_PLATES = "blur_plates"
        private const val KEY_ENCRYPT_EVENT_CLIPS = "encrypt_event_clips"
        private const val KEY_RETENTION_LOW_DAYS = "retention_low_days"
        private const val KEY_RETENTION_MEDIUM_DAYS = "retention_medium_days"
        private const val KEY_RETENTION_HIGH_DAYS = "retention_high_days"
    }
}

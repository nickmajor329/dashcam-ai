package com.dashcam.ai.recording

import android.content.Context

class RecordingStateManager(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setRecordingActive(active: Boolean) {
        prefs.edit().putBoolean(KEY_RECORDING_ACTIVE, active).apply()
    }

    fun isRecordingActive(): Boolean {
        return prefs.getBoolean(KEY_RECORDING_ACTIVE, false)
    }

    companion object {
        private const val PREFS_NAME = "recording_state"
        private const val KEY_RECORDING_ACTIVE = "recording_active"
    }
}

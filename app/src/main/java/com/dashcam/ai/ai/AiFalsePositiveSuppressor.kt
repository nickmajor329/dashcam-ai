package com.dashcam.ai.ai

import android.content.Context
import com.dashcam.ai.data.EventType

class AiFalsePositiveSuppressor(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun shouldSuppress(event: DetectedEvent, nowMs: Long): Boolean {
        val minConfidence = when (event.eventType) {
            EventType.MOTION_NEAR_VEHICLE -> 0.72f
            EventType.PERSON_NEAR_WINDOW -> if (event.plateLikely || event.doorLikelyOpen) 0.60f else 0.70f
            EventType.IMPACT -> 0.82f
        }
        if (event.confidence < minConfidence) return true

        val minVotes = when (event.eventType) {
            EventType.IMPACT -> 1
            EventType.MOTION_NEAR_VEHICLE -> 2
            EventType.PERSON_NEAR_WINDOW -> 2
        }
        if (event.dominantFrameVotes < minVotes) return true

        val cooldownMs = when (event.eventType) {
            EventType.MOTION_NEAR_VEHICLE -> 25_000L
            EventType.PERSON_NEAR_WINDOW -> 20_000L
            EventType.IMPACT -> 8_000L
        }
        val last = prefs.getLong(keyFor(event.eventType), 0L)
        return nowMs - last < cooldownMs
    }

    fun markAccepted(eventType: EventType, nowMs: Long) {
        prefs.edit().putLong(keyFor(eventType), nowMs).apply()
    }

    private fun keyFor(eventType: EventType): String = "last_${eventType.name}"

    companion object {
        private const val PREFS_NAME = "ai_false_positive_suppression"
    }
}

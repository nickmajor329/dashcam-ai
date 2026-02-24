package com.dashcam.ai.ai

import android.content.Context
import com.dashcam.ai.data.EventType

class AiEventDetector(context: Context) {

    private val detector = TfliteClipEventDetector(context)
    private val suppressor = AiFalsePositiveSuppressor(context)

    fun detectFromClip(clipPath: String): DetectedEvent? {
        val detected = detector.detectFromClip(clipPath) ?: return null
        if (suppressor.shouldSuppress(detected, System.currentTimeMillis())) {
            return null
        }
        suppressor.markAccepted(detected.eventType, System.currentTimeMillis())
        return detected
    }
}

data class DetectedEvent(
    val eventType: EventType,
    val confidence: Float,
    val sampledFrames: Int,
    val dominantFrameVotes: Int,
    val personLikely: Boolean,
    val plateLikely: Boolean,
    val doorLikelyOpen: Boolean
)

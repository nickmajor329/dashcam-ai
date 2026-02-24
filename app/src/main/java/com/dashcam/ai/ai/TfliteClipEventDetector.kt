package com.dashcam.ai.ai

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.util.Log
import com.dashcam.ai.data.EventType
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel

class TfliteClipEventDetector(
    context: Context,
    private val modelAssetName: String = "vehicle_event_detector.tflite"
) {

    private val interpreter: Interpreter? = try {
        Interpreter(loadModelFile(context, modelAssetName))
    } catch (t: Throwable) {
        Log.w(TAG, "TFLite model not loaded: ${t.message}")
        null
    }

    fun detectFromClip(clipPath: String): DetectedEvent? {
        val model = interpreter ?: return null
        val frames = extractFramesForInference(clipPath)
        if (frames.isEmpty()) return null

        val summedScores = FloatArray(OUTPUT_CLASSES)
        val voteCounts = IntArray(OUTPUT_CLASSES)
        for (frame in frames) {
            val input = preprocess(frame)
            val output = Array(1) { FloatArray(OUTPUT_CLASSES) }
            model.run(input, output)
            val scores = output[0]
            var bestIdx = 0
            var bestScore = scores[0]
            for (i in 1 until scores.size) {
                if (scores[i] > bestScore) {
                    bestScore = scores[i]
                    bestIdx = i
                }
            }
            voteCounts[bestIdx] += 1
            for (i in summedScores.indices) {
                summedScores[i] += scores[i]
            }
            runCatching { frame.recycle() }
        }

        val frameCount = frames.size
        val meanScores = FloatArray(OUTPUT_CLASSES) { i -> summedScores[i] / frameCount.toFloat() }
        var bestIndex = 0
        var bestScore = meanScores[0]
        for (i in 1 until meanScores.size) {
            if (meanScores[i] > bestScore) {
                bestScore = meanScores[i]
                bestIndex = i
            }
        }

        val dominantVotes = voteCounts[bestIndex]
        val stability = dominantVotes.toFloat() / frameCount.toFloat()
        val blendedConfidence = (bestScore * 0.7f) + (stability * 0.3f)
        if (blendedConfidence < MIN_CONFIDENCE) return null

        val eventType = EVENT_BY_INDEX.getOrElse(bestIndex) { EventType.MOTION_NEAR_VEHICLE }
        val personScore = meanScores[PERSON_INDEX]
        val motionScore = meanScores[MOTION_INDEX]
        val impactScore = meanScores[IMPACT_INDEX]

        val personLikely = personScore >= 0.58f
        val plateLikely = personScore >= 0.76f && impactScore < 0.45f
        val doorLikelyOpen = motionScore >= 0.52f && personScore >= 0.45f

        return DetectedEvent(
            eventType = eventType,
            confidence = blendedConfidence,
            sampledFrames = frameCount,
            dominantFrameVotes = dominantVotes,
            personLikely = personLikely,
            plateLikely = plateLikely,
            doorLikelyOpen = doorLikelyOpen
        )
    }

    private fun extractFramesForInference(clipPath: String): List<Bitmap> {
        val retriever = MediaMetadataRetriever()
        return try {
            retriever.setDataSource(clipPath)
            val durationMs = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
                ?.toLongOrNull()
                ?.coerceAtLeast(1L)
                ?: 1L
            val frames = mutableListOf<Bitmap>()
            for (index in 0 until SAMPLE_FRAMES) {
                val ratio = (index + 1).toDouble() / (SAMPLE_FRAMES + 1).toDouble()
                val frameUs = (durationMs * 1000L * ratio).toLong()
                val frame = retriever.getFrameAtTime(frameUs, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
                if (frame != null) {
                    frames += frame
                }
            }
            frames
        } catch (t: Throwable) {
            Log.w(TAG, "Could not decode frames for inference: ${t.message}")
            emptyList()
        } finally {
            runCatching { retriever.release() }
        }
    }

    private fun preprocess(bitmap: Bitmap): ByteBuffer {
        val resized = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, true)
        val buffer = ByteBuffer.allocateDirect(4 * INPUT_SIZE * INPUT_SIZE * CHANNELS)
            .order(ByteOrder.nativeOrder())

        val pixels = IntArray(INPUT_SIZE * INPUT_SIZE)
        resized.getPixels(pixels, 0, INPUT_SIZE, 0, 0, INPUT_SIZE, INPUT_SIZE)

        for (pixel in pixels) {
            val r = ((pixel shr 16) and 0xFF) / 255f
            val g = ((pixel shr 8) and 0xFF) / 255f
            val b = (pixel and 0xFF) / 255f

            buffer.putFloat(r)
            buffer.putFloat(g)
            buffer.putFloat(b)
        }
        buffer.rewind()
        return buffer
    }

    private fun loadModelFile(context: Context, assetName: String): ByteBuffer {
        context.assets.openFd(assetName).use { afd ->
            FileInputStream(afd.fileDescriptor).use { input ->
                val channel = input.channel
                return channel.map(
                    FileChannel.MapMode.READ_ONLY,
                    afd.startOffset,
                    afd.declaredLength
                )
            }
        }
    }

    companion object {
        private const val TAG = "TfliteClipDetector"
        private const val INPUT_SIZE = 224
        private const val CHANNELS = 3
        private const val OUTPUT_CLASSES = 3
        private const val MIN_CONFIDENCE = 0.58f
        private const val SAMPLE_FRAMES = 5
        private const val MOTION_INDEX = 0
        private const val PERSON_INDEX = 1
        private const val IMPACT_INDEX = 2

        private val EVENT_BY_INDEX = listOf(
            EventType.MOTION_NEAR_VEHICLE,
            EventType.PERSON_NEAR_WINDOW,
            EventType.IMPACT
        )
    }
}

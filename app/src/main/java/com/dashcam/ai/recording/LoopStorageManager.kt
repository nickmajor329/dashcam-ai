package com.dashcam.ai.recording

import android.content.Context
import com.dashcam.ai.data.EventEntity
import com.dashcam.ai.privacy.ClipCryptoManager
import com.dashcam.ai.privacy.PrivacySettings
import com.dashcam.ai.privacy.retentionMsFor
import java.io.File

class LoopStorageManager(
    context: Context,
    private val maxStorageBytes: Long
) {

    private val storageRoot = File(context.filesDir, "dashcam_clips")
    private val lockedRoot = File(context.filesDir, "locked_clips")

    fun ensureStorageRoot() {
        if (!storageRoot.exists()) {
            storageRoot.mkdirs()
        }
        if (!lockedRoot.exists()) {
            lockedRoot.mkdirs()
        }
    }

    fun reserveNewClipFile(epochMs: Long): File {
        ensureStorageRoot()
        val name = "clip_${epochMs}.mp4"
        return File(storageRoot, name)
    }

    fun enforceRetention() {
        val files = storageRoot.listFiles()?.sortedBy { it.lastModified() } ?: return
        var total = files.sumOf { it.length() }

        for (file in files) {
            if (total <= maxStorageBytes) break
            val size = file.length()
            if (file.delete()) {
                total -= size
            }
        }
    }

    fun lockClip(source: File, encrypt: Boolean = false, cryptoManager: ClipCryptoManager? = null): File? {
        if (!source.exists()) return null
        ensureStorageRoot()

        val suffix = if (encrypt) ".enc" else ".mp4"
        val target = File(lockedRoot, "locked_${System.currentTimeMillis()}_${source.nameWithoutExtension}$suffix")
        return runCatching {
            if (encrypt) {
                val crypto = cryptoManager ?: ClipCryptoManager()
                if (!crypto.encryptFile(source, target)) return null
            } else {
                source.inputStream().use { input ->
                    target.outputStream().use { out ->
                        input.copyTo(out)
                    }
                }
            }
            target
        }.getOrNull()
    }

    fun enforceEventRetention(events: List<EventEntity>, settings: PrivacySettings) {
        ensureStorageRoot()
        val now = System.currentTimeMillis()

        val keepUntilByPath = mutableMapOf<String, Long>()
        for (event in events) {
            val path = event.clipPath
            if (path.isBlank()) continue
            val retentionMs = settings.retentionMsFor(event.eventType)
            val keepUntil = event.createdAtMs + retentionMs
            val existing = keepUntilByPath[path]
            if (existing == null || keepUntil > existing) {
                keepUntilByPath[path] = keepUntil
            }
        }

        for ((path, keepUntil) in keepUntilByPath) {
            if (now > keepUntil) {
                runCatching { File(path).delete() }
            }
        }

        val orphanCutoff = now - settings.retentionLowDays * 24L * 60L * 60L * 1000L
        lockedRoot.listFiles()?.forEach { file ->
            val tracked = keepUntilByPath.containsKey(file.absolutePath)
            if (!tracked && file.lastModified() < orphanCutoff) {
                runCatching { file.delete() }
            }
        }
    }
}

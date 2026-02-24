package com.dashcam.ai.system

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.dashcam.ai.recording.RecordingService

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val action = intent?.action ?: return
        if (action == Intent.ACTION_BOOT_COMPLETED || action == Intent.ACTION_LOCKED_BOOT_COMPLETED) {
            ContextCompat.startForegroundService(
                context,
                Intent(context, RecordingService::class.java).apply {
                    this.action = RecordingService.ACTION_START
                }
            )
        }
    }
}

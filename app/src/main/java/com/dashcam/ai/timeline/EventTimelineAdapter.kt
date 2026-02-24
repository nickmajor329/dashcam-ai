package com.dashcam.ai.timeline

import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.os.Build
import android.provider.MediaStore
import android.util.Size
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dashcam.ai.data.EventEntity
import com.dashcam.ai.databinding.ItemEventTimelineBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EventTimelineAdapter(
    private val onShare: (EventEntity) -> Unit,
    private val onExport: (EventEntity) -> Unit
) : RecyclerView.Adapter<EventTimelineAdapter.EventViewHolder>() {

    private val items = mutableListOf<EventEntity>()
    private val formatter = SimpleDateFormat("MMM d, yyyy h:mm a", Locale.US)

    fun submit(events: List<EventEntity>) {
        items.clear()
        items.addAll(events)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventTimelineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class EventViewHolder(
        private val binding: ItemEventTimelineBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: EventEntity) {
            binding.eventTitle.text = event.eventType.name.replace('_', ' ')
            binding.eventMeta.text = "${formatter.format(Date(event.createdAtMs))} • conf ${(event.confidence * 100).toInt()}%"
            binding.eventUploadState.text = if (event.uploaded) "Uploaded" else "Pending upload"

            val thumbnail = createThumbnail(event.clipPath)
            if (thumbnail != null) {
                binding.eventThumb.setImageBitmap(thumbnail)
            } else {
                binding.eventThumb.setImageResource(android.R.drawable.ic_menu_report_image)
            }

            binding.shareButton.setOnClickListener { onShare(event) }
            binding.exportButton.setOnClickListener { onExport(event) }
        }
    }

    private fun createThumbnail(path: String): Bitmap? {
        val file = File(path)
        if (!file.exists()) return null

        return runCatching {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ThumbnailUtils.createVideoThumbnail(file, Size(320, 180), null)
            } else {
                @Suppress("DEPRECATION")
                ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MINI_KIND)
            }
        }.getOrNull()
    }
}

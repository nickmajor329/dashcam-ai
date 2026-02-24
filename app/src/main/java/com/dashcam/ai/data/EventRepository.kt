package com.dashcam.ai.data

import android.content.Context

class EventRepository(context: Context) {

    private val dao = AppDatabase.instance(context).eventDao()

    suspend fun saveEvent(event: EventEntity): Long = dao.insert(event)

    suspend fun pendingUploads(): List<EventEntity> = dao.pendingUploadEvents()

    suspend fun markUploaded(eventId: Long) = dao.markUploaded(eventId)

    suspend fun timeline(eventType: EventType?, pendingOnly: Boolean): List<EventEntity> {
        return when {
            pendingOnly && eventType != null -> dao.pendingEventsByType(eventType)
            pendingOnly -> dao.pendingEvents()
            eventType != null -> dao.eventsByType(eventType)
            else -> dao.allEvents()
        }
    }
}

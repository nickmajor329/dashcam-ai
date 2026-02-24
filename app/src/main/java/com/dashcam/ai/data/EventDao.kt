package com.dashcam.ai.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EventDao {

    @Insert
    suspend fun insert(event: EventEntity): Long

    @Query("SELECT * FROM events WHERE uploaded = 0 ORDER BY createdAtMs DESC")
    suspend fun pendingUploadEvents(): List<EventEntity>

    @Query("UPDATE events SET uploaded = 1 WHERE id = :eventId")
    suspend fun markUploaded(eventId: Long)

    @Query("SELECT * FROM events ORDER BY createdAtMs DESC")
    suspend fun allEvents(): List<EventEntity>

    @Query("SELECT * FROM events WHERE eventType = :eventType ORDER BY createdAtMs DESC")
    suspend fun eventsByType(eventType: EventType): List<EventEntity>

    @Query("SELECT * FROM events WHERE uploaded = 0 ORDER BY createdAtMs DESC")
    suspend fun pendingEvents(): List<EventEntity>

    @Query("SELECT * FROM events WHERE uploaded = 0 AND eventType = :eventType ORDER BY createdAtMs DESC")
    suspend fun pendingEventsByType(eventType: EventType): List<EventEntity>
}

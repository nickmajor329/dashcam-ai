package com.dashcam.ai.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val eventType: EventType,
    val confidence: Float,
    val createdAtMs: Long,
    val clipPath: String,
    val uploaded: Boolean = false
)

enum class EventType {
    MOTION_NEAR_VEHICLE,
    PERSON_NEAR_WINDOW,
    IMPACT
}

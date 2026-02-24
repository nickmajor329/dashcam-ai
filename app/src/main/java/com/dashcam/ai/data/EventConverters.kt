package com.dashcam.ai.data

import androidx.room.TypeConverter

class EventConverters {

    @TypeConverter
    fun fromEventType(eventType: EventType): String = eventType.name

    @TypeConverter
    fun toEventType(value: String): EventType = EventType.valueOf(value)
}

package com.dashcam.ai.privacy

import com.dashcam.ai.data.EventType

enum class EventSeverity {
    LOW,
    MEDIUM,
    HIGH
}

fun EventType.toSeverity(): EventSeverity {
    return when (this) {
        EventType.IMPACT -> EventSeverity.HIGH
        EventType.PERSON_NEAR_WINDOW -> EventSeverity.MEDIUM
        EventType.MOTION_NEAR_VEHICLE -> EventSeverity.LOW
    }
}

fun PrivacySettings.retentionMsFor(eventType: EventType): Long {
    val days = when (eventType.toSeverity()) {
        EventSeverity.LOW -> retentionLowDays
        EventSeverity.MEDIUM -> retentionMediumDays
        EventSeverity.HIGH -> retentionHighDays
    }
    return days * 24L * 60L * 60L * 1000L
}

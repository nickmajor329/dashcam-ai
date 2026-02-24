package com.dashcam.ai.location

import android.location.Location
import kotlin.math.abs

class AwayStateManager(
    private val distanceThresholdMeters: Float = 150f
) {

    fun isOwnerAway(parkedLat: Double, parkedLon: Double, ownerLocation: Location?): Boolean {
        ownerLocation ?: return false
        val parked = Location("parked").apply {
            latitude = parkedLat
            longitude = parkedLon
        }
        return parked.distanceTo(ownerLocation) > distanceThresholdMeters
    }

    fun isValidParkedLocation(lat: Double, lon: Double): Boolean {
        return abs(lat) <= 90.0 && abs(lon) <= 180.0
    }
}

package com.dashcam.ai.location

import android.content.Context

data class ParkedState(
    val isParked: Boolean,
    val ownerAwayEnabled: Boolean,
    val parkedLat: Double?,
    val parkedLon: Double?,
    val parkedAtMs: Long?,
    val knownCarDeviceAddress: String?
)

class ParkedStateManager(context: Context) {

    private val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun markParked(lat: Double?, lon: Double?, parkedAtMs: Long = System.currentTimeMillis()) {
        prefs.edit()
            .putBoolean(KEY_IS_PARKED, true)
            .putLong(KEY_PARKED_AT_MS, parkedAtMs)
            .putBoolean(KEY_AWAY_ENABLED, true)
            .apply {
                if (lat != null && lon != null) {
                    putLong(KEY_PARKED_LAT_BITS, java.lang.Double.doubleToRawLongBits(lat))
                    putLong(KEY_PARKED_LON_BITS, java.lang.Double.doubleToRawLongBits(lon))
                    putBoolean(KEY_HAS_COORDS, true)
                } else {
                    remove(KEY_PARKED_LAT_BITS)
                    remove(KEY_PARKED_LON_BITS)
                    putBoolean(KEY_HAS_COORDS, false)
                }
            }
            .apply()
    }

    fun markDriving() {
        prefs.edit()
            .putBoolean(KEY_IS_PARKED, false)
            .putBoolean(KEY_AWAY_ENABLED, false)
            .remove(KEY_PARKED_AT_MS)
            .remove(KEY_PARKED_LAT_BITS)
            .remove(KEY_PARKED_LON_BITS)
            .putBoolean(KEY_HAS_COORDS, false)
            .apply()
    }

    fun setOwnerAwayEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_AWAY_ENABLED, enabled).apply()
    }

    fun setKnownCarDeviceAddress(address: String?) {
        prefs.edit().apply {
            if (address.isNullOrBlank()) {
                remove(KEY_KNOWN_CAR_DEVICE)
            } else {
                putString(KEY_KNOWN_CAR_DEVICE, address)
            }
        }.apply()
    }

    fun snapshot(): ParkedState {
        val hasCoords = prefs.getBoolean(KEY_HAS_COORDS, false)
        val lat = if (hasCoords) {
            java.lang.Double.longBitsToDouble(prefs.getLong(KEY_PARKED_LAT_BITS, 0L))
        } else {
            null
        }
        val lon = if (hasCoords) {
            java.lang.Double.longBitsToDouble(prefs.getLong(KEY_PARKED_LON_BITS, 0L))
        } else {
            null
        }
        val parkedAt = if (prefs.contains(KEY_PARKED_AT_MS)) prefs.getLong(KEY_PARKED_AT_MS, 0L) else null

        return ParkedState(
            isParked = prefs.getBoolean(KEY_IS_PARKED, false),
            ownerAwayEnabled = prefs.getBoolean(KEY_AWAY_ENABLED, false),
            parkedLat = lat,
            parkedLon = lon,
            parkedAtMs = parkedAt,
            knownCarDeviceAddress = prefs.getString(KEY_KNOWN_CAR_DEVICE, null)
        )
    }

    companion object {
        private const val PREF_NAME = "parked_state"
        private const val KEY_IS_PARKED = "is_parked"
        private const val KEY_AWAY_ENABLED = "away_enabled"
        private const val KEY_PARKED_AT_MS = "parked_at_ms"
        private const val KEY_PARKED_LAT_BITS = "parked_lat_bits"
        private const val KEY_PARKED_LON_BITS = "parked_lon_bits"
        private const val KEY_HAS_COORDS = "has_coords"
        private const val KEY_KNOWN_CAR_DEVICE = "known_car_device"
    }
}

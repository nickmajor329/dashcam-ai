package com.dashcam.ai.location

import android.content.Context
import android.location.Location

enum class AlertZoneMode {
    ANY,
    HOME_ONLY,
    WORK_ONLY,
    PUBLIC_ONLY
}

data class GeofenceSettings(
    val enabled: Boolean,
    val mode: AlertZoneMode,
    val homeLat: Double?,
    val homeLon: Double?,
    val workLat: Double?,
    val workLon: Double?,
    val radiusMeters: Float
)

class GeofenceSettingsManager(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun snapshot(): GeofenceSettings {
        return GeofenceSettings(
            enabled = prefs.getBoolean(KEY_ENABLED, false),
            mode = runCatching {
                AlertZoneMode.valueOf(prefs.getString(KEY_MODE, AlertZoneMode.ANY.name).orEmpty())
            }.getOrDefault(AlertZoneMode.ANY),
            homeLat = readOptionalDouble(KEY_HOME_LAT),
            homeLon = readOptionalDouble(KEY_HOME_LON),
            workLat = readOptionalDouble(KEY_WORK_LAT),
            workLon = readOptionalDouble(KEY_WORK_LON),
            radiusMeters = prefs.getFloat(KEY_RADIUS, 250f).coerceIn(50f, 2000f)
        )
    }

    fun save(settings: GeofenceSettings) {
        prefs.edit().apply {
            putBoolean(KEY_ENABLED, settings.enabled)
            putString(KEY_MODE, settings.mode.name)
            writeOptionalDouble(this, KEY_HOME_LAT, settings.homeLat)
            writeOptionalDouble(this, KEY_HOME_LON, settings.homeLon)
            writeOptionalDouble(this, KEY_WORK_LAT, settings.workLat)
            writeOptionalDouble(this, KEY_WORK_LON, settings.workLon)
            putFloat(KEY_RADIUS, settings.radiusMeters.coerceIn(50f, 2000f))
        }.apply()
    }

    fun shouldAllowAlert(parkedLat: Double?, parkedLon: Double?): Boolean {
        val settings = snapshot()
        if (!settings.enabled) return true
        if (parkedLat == null || parkedLon == null) return false

        val zone = classifyZone(settings, parkedLat, parkedLon)
        return when (settings.mode) {
            AlertZoneMode.ANY -> true
            AlertZoneMode.HOME_ONLY -> zone == Zone.HOME
            AlertZoneMode.WORK_ONLY -> zone == Zone.WORK
            AlertZoneMode.PUBLIC_ONLY -> zone == Zone.PUBLIC
        }
    }

    private fun classifyZone(settings: GeofenceSettings, parkedLat: Double, parkedLon: Double): Zone {
        if (isWithin(settings.homeLat, settings.homeLon, parkedLat, parkedLon, settings.radiusMeters)) {
            return Zone.HOME
        }
        if (isWithin(settings.workLat, settings.workLon, parkedLat, parkedLon, settings.radiusMeters)) {
            return Zone.WORK
        }
        return Zone.PUBLIC
    }

    private fun isWithin(
        anchorLat: Double?,
        anchorLon: Double?,
        parkedLat: Double,
        parkedLon: Double,
        radiusMeters: Float
    ): Boolean {
        if (anchorLat == null || anchorLon == null) return false

        val a = Location("anchor").apply {
            latitude = anchorLat
            longitude = anchorLon
        }
        val b = Location("parked").apply {
            latitude = parkedLat
            longitude = parkedLon
        }
        return a.distanceTo(b) <= radiusMeters
    }

    private fun readOptionalDouble(key: String): Double? {
        if (!prefs.contains(key)) return null
        return java.lang.Double.longBitsToDouble(prefs.getLong(key, 0L))
    }

    private fun writeOptionalDouble(editor: android.content.SharedPreferences.Editor, key: String, value: Double?) {
        if (value == null) {
            editor.remove(key)
        } else {
            editor.putLong(key, java.lang.Double.doubleToRawLongBits(value))
        }
    }

    private enum class Zone {
        HOME,
        WORK,
        PUBLIC
    }

    companion object {
        private const val PREFS_NAME = "geofence_settings"
        private const val KEY_ENABLED = "enabled"
        private const val KEY_MODE = "mode"
        private const val KEY_HOME_LAT = "home_lat"
        private const val KEY_HOME_LON = "home_lon"
        private const val KEY_WORK_LAT = "work_lat"
        private const val KEY_WORK_LON = "work_lon"
        private const val KEY_RADIUS = "radius"
    }
}

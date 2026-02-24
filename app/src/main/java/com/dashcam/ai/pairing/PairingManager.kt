package com.dashcam.ai.pairing

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

data class FleetVehicle(
    val vehicleId: String,
    val role: String
)

data class PairingState(
    val vehicleId: String,
    val pairedOwnerId: String,
    val paired: Boolean,
    val activeVehicleId: String,
    val activeRole: String,
    val fleet: List<FleetVehicle>
)

class PairingManager(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun snapshot(): PairingState {
        val fleet = decodeFleet(prefs.getString(KEY_FLEET_JSON, "[]") ?: "[]")
        val vehicleId = prefs.getString(KEY_VEHICLE_ID, "") ?: ""
        val activeVehicleId = prefs.getString(KEY_ACTIVE_VEHICLE_ID, "")?.ifBlank {
            fleet.firstOrNull()?.vehicleId ?: vehicleId
        } ?: ""
        val activeRole = fleet.firstOrNull { it.vehicleId == activeVehicleId }?.role ?: "OWNER"
        val ownerId = prefs.getString(KEY_OWNER_ID, "") ?: ""
        val paired = prefs.getBoolean(KEY_PAIRED, false) || fleet.isNotEmpty()
        return PairingState(
            vehicleId = activeVehicleId.ifBlank { vehicleId },
            pairedOwnerId = ownerId,
            paired = paired,
            activeVehicleId = activeVehicleId,
            activeRole = activeRole,
            fleet = fleet
        )
    }

    fun setVehicleId(vehicleId: String) {
        val clean = vehicleId.trim()
        prefs.edit()
            .putString(KEY_VEHICLE_ID, clean)
            .putString(KEY_ACTIVE_VEHICLE_ID, clean)
            .apply()
    }

    fun markPaired(ownerId: String) {
        prefs.edit()
            .putBoolean(KEY_PAIRED, true)
            .putString(KEY_OWNER_ID, ownerId.trim())
            .apply()
    }

    fun setOwnerId(ownerId: String) {
        prefs.edit()
            .putString(KEY_OWNER_ID, ownerId.trim())
            .apply()
    }

    fun clearOwner() {
        prefs.edit()
            .remove(KEY_OWNER_ID)
            .apply()
    }

    fun addOrUpdateFleetVehicle(vehicleId: String, role: String) {
        val cleanVehicle = vehicleId.trim()
        if (cleanVehicle.isBlank()) return
        val cleanRole = role.trim().ifBlank { "OWNER" }
        val current = decodeFleet(prefs.getString(KEY_FLEET_JSON, "[]") ?: "[]").toMutableList()
        val index = current.indexOfFirst { it.vehicleId == cleanVehicle }
        val next = FleetVehicle(cleanVehicle, cleanRole)
        if (index >= 0) current[index] = next else current.add(next)

        prefs.edit()
            .putString(KEY_FLEET_JSON, encodeFleet(current))
            .putString(KEY_ACTIVE_VEHICLE_ID, cleanVehicle)
            .putString(KEY_VEHICLE_ID, cleanVehicle)
            .putBoolean(KEY_PAIRED, true)
            .apply()
    }

    fun setActiveVehicle(vehicleId: String) {
        val clean = vehicleId.trim()
        if (clean.isBlank()) return
        prefs.edit()
            .putString(KEY_ACTIVE_VEHICLE_ID, clean)
            .putString(KEY_VEHICLE_ID, clean)
            .apply()
    }

    fun clearPairing() {
        prefs.edit()
            .putBoolean(KEY_PAIRED, false)
            .remove(KEY_OWNER_ID)
            .remove(KEY_ACTIVE_VEHICLE_ID)
            .remove(KEY_FLEET_JSON)
            .apply()
    }

    private fun decodeFleet(raw: String): List<FleetVehicle> {
        return runCatching {
            val array = JSONArray(raw)
            buildList {
                for (i in 0 until array.length()) {
                    val item = array.optJSONObject(i) ?: continue
                    val vehicleId = item.optString("vehicle_id")
                    if (vehicleId.isBlank()) continue
                    val role = item.optString("role").ifBlank { "OWNER" }
                    add(FleetVehicle(vehicleId, role))
                }
            }
        }.getOrDefault(emptyList())
    }

    private fun encodeFleet(fleet: List<FleetVehicle>): String {
        val array = JSONArray()
        fleet.forEach { vehicle ->
            array.put(
                JSONObject()
                    .put("vehicle_id", vehicle.vehicleId)
                    .put("role", vehicle.role)
            )
        }
        return array.toString()
    }

    companion object {
        private const val PREFS_NAME = "pairing_state"
        private const val KEY_VEHICLE_ID = "vehicle_id"
        private const val KEY_OWNER_ID = "owner_id"
        private const val KEY_PAIRED = "paired"
        private const val KEY_ACTIVE_VEHICLE_ID = "active_vehicle_id"
        private const val KEY_FLEET_JSON = "fleet_json"
    }
}

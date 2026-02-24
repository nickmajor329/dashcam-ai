package com.dashcam.ai.alerts

import android.content.Context
import com.dashcam.ai.pairing.PairingManager

data class AlertRoutingConfig(
    val appEnabled: Boolean,
    val emailEnabled: Boolean,
    val smsEnabled: Boolean,
    val appDeviceId: String,
    val emailAddress: String,
    val phoneNumber: String
)

class AlertRoutingManager(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val pairingManager = PairingManager(context)

    fun snapshot(): AlertRoutingConfig {
        val activeVehicleId = pairingManager.snapshot().activeVehicleId
        return AlertRoutingConfig(
            appEnabled = readBoolean(KEY_APP_ENABLED, activeVehicleId, true),
            emailEnabled = readBoolean(KEY_EMAIL_ENABLED, activeVehicleId, false),
            smsEnabled = readBoolean(KEY_SMS_ENABLED, activeVehicleId, false),
            appDeviceId = readString(KEY_APP_DEVICE_ID, activeVehicleId),
            emailAddress = readString(KEY_EMAIL_ADDRESS, activeVehicleId),
            phoneNumber = readString(KEY_PHONE_NUMBER, activeVehicleId)
        )
    }

    fun setAppEnabled(enabled: Boolean) {
        writeBoolean(KEY_APP_ENABLED, enabled)
    }

    fun setEmailEnabled(enabled: Boolean) {
        writeBoolean(KEY_EMAIL_ENABLED, enabled)
    }

    fun setSmsEnabled(enabled: Boolean) {
        writeBoolean(KEY_SMS_ENABLED, enabled)
    }

    fun setAppDeviceId(deviceId: String) {
        writeString(KEY_APP_DEVICE_ID, deviceId.trim())
    }

    fun setEmailAddress(email: String) {
        writeString(KEY_EMAIL_ADDRESS, email.trim())
    }

    fun setPhoneNumber(phone: String) {
        writeString(KEY_PHONE_NUMBER, phone.trim())
    }

    private fun readBoolean(baseKey: String, vehicleId: String, fallback: Boolean): Boolean {
        val scoped = scopedKey(baseKey, vehicleId)
        return when {
            prefs.contains(scoped) -> prefs.getBoolean(scoped, fallback)
            else -> prefs.getBoolean(baseKey, fallback)
        }
    }

    private fun readString(baseKey: String, vehicleId: String): String {
        val scoped = scopedKey(baseKey, vehicleId)
        return when {
            prefs.contains(scoped) -> prefs.getString(scoped, "") ?: ""
            else -> prefs.getString(baseKey, "") ?: ""
        }
    }

    private fun writeBoolean(baseKey: String, value: Boolean) {
        val vehicleId = pairingManager.snapshot().activeVehicleId
        prefs.edit()
            .putBoolean(baseKey, value)
            .putBoolean(scopedKey(baseKey, vehicleId), value)
            .apply()
    }

    private fun writeString(baseKey: String, value: String) {
        val vehicleId = pairingManager.snapshot().activeVehicleId
        prefs.edit()
            .putString(baseKey, value)
            .putString(scopedKey(baseKey, vehicleId), value)
            .apply()
    }

    private fun scopedKey(base: String, vehicleId: String): String {
        val safeVehicle = vehicleId.ifBlank { "default" }
        return "${base}__vehicle_${safeVehicle}"
    }

    companion object {
        private const val PREFS_NAME = "alert_routing"
        private const val KEY_APP_ENABLED = "app_enabled"
        private const val KEY_EMAIL_ENABLED = "email_enabled"
        private const val KEY_SMS_ENABLED = "sms_enabled"
        private const val KEY_APP_DEVICE_ID = "app_device_id"
        private const val KEY_EMAIL_ADDRESS = "email_address"
        private const val KEY_PHONE_NUMBER = "phone_number"
    }
}

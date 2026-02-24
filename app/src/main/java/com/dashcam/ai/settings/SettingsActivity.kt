package com.dashcam.ai.settings

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import com.dashcam.ai.alerts.AlertRoutingManager
import com.dashcam.ai.auth.AuthSessionManager
import com.dashcam.ai.databinding.ActivitySettingsBinding
import com.dashcam.ai.location.AlertZoneMode
import com.dashcam.ai.location.GeofenceSettings
import com.dashcam.ai.location.GeofenceSettingsManager
import com.dashcam.ai.network.BackendApiClient
import com.dashcam.ai.pairing.PairingManager
import com.dashcam.ai.privacy.PrivacySettings
import com.dashcam.ai.privacy.PrivacySettingsManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var settingsManager: AppSettingsManager
    private lateinit var routingManager: AlertRoutingManager
    private lateinit var geofenceManager: GeofenceSettingsManager
    private lateinit var privacySettingsManager: PrivacySettingsManager
    private lateinit var pairingManager: PairingManager
    private lateinit var authSessionManager: AuthSessionManager
    private val apiClient = BackendApiClient()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingsManager = AppSettingsManager(this)
        routingManager = AlertRoutingManager(this)
        geofenceManager = GeofenceSettingsManager(this)
        privacySettingsManager = PrivacySettingsManager(this)
        pairingManager = PairingManager(this)
        authSessionManager = AuthSessionManager(this)
        seedUi()

        binding.backButton.setOnClickListener { finish() }
        binding.setActiveVehicleButton.setOnClickListener { setActiveVehicleFromInput() }
        binding.saveFleetRoleButton.setOnClickListener { saveFleetRoleFromInput() }

        binding.saveSettingsButton.setOnClickListener {
            val activeVehicle = binding.activeVehicleIdInput.text?.toString().orEmpty().trim()
            if (activeVehicle.isNotBlank()) {
                pairingManager.setActiveVehicle(activeVehicle)
            }
            val useFrontLens = binding.defaultFrontLensSwitch.isChecked
            val lens = if (useFrontLens) CameraSelector.LENS_FACING_FRONT else CameraSelector.LENS_FACING_BACK
            val segmentSeconds = binding.segmentSecondsInput.text?.toString()?.toIntOrNull() ?: 60
            val maxStorageGb = binding.maxStorageGbInput.text?.toString()?.toIntOrNull() ?: 8
            val idleEnabled = binding.idleDetectionSwitch.isChecked
            val idleMinutes = binding.idleMinutesInput.text?.toString()?.toIntOrNull() ?: 5
            val defaultDim = binding.defaultDimSwitch.isChecked
            val defaultBlack = binding.defaultBlackSwitch.isChecked

            settingsManager.save(
                defaultLensFacing = lens,
                segmentDurationSeconds = segmentSeconds,
                maxStorageGb = maxStorageGb,
                idleDetectionEnabled = idleEnabled,
                idleThresholdMinutes = idleMinutes,
                defaultDimScreen = defaultDim,
                defaultBlackScreen = defaultBlack
            )

            geofenceManager.save(
                GeofenceSettings(
                    enabled = binding.geofenceEnabledSwitch.isChecked,
                    mode = selectedZoneMode(),
                    homeLat = binding.homeLatInput.text?.toString()?.toDoubleOrNull(),
                    homeLon = binding.homeLonInput.text?.toString()?.toDoubleOrNull(),
                    workLat = binding.workLatInput.text?.toString()?.toDoubleOrNull(),
                    workLon = binding.workLonInput.text?.toString()?.toDoubleOrNull(),
                    radiusMeters = (binding.geofenceRadiusInput.text?.toString()?.toFloatOrNull() ?: 250f)
                )
            )

            routingManager.setAppEnabled(binding.appAlertSwitch.isChecked)
            routingManager.setEmailEnabled(binding.emailAlertSwitch.isChecked)
            routingManager.setSmsEnabled(binding.smsAlertSwitch.isChecked)
            routingManager.setAppDeviceId(binding.appDeviceIdInput.text?.toString().orEmpty())
            routingManager.setEmailAddress(binding.emailInput.text?.toString().orEmpty())
            routingManager.setPhoneNumber(binding.phoneInput.text?.toString().orEmpty())

            privacySettingsManager.save(
                PrivacySettings(
                    blurFaces = binding.blurFacesSwitch.isChecked,
                    blurPlates = binding.blurPlatesSwitch.isChecked,
                    encryptEventClips = binding.encryptEventClipsSwitch.isChecked,
                    retentionLowDays = binding.retentionLowDaysInput.text?.toString()?.toIntOrNull() ?: 2,
                    retentionMediumDays = binding.retentionMediumDaysInput.text?.toString()?.toIntOrNull() ?: 7,
                    retentionHighDays = binding.retentionHighDaysInput.text?.toString()?.toIntOrNull() ?: 21
                )
            )

            Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }

    private fun seedUi() {
        val settings = settingsManager.snapshot()
        binding.defaultFrontLensSwitch.isChecked = settings.defaultLensFacing == CameraSelector.LENS_FACING_FRONT
        binding.segmentSecondsInput.setText(settings.segmentDurationSeconds.toString())
        binding.maxStorageGbInput.setText(settings.maxStorageGb.toString())
        binding.idleDetectionSwitch.isChecked = settings.idleDetectionEnabled
        binding.idleMinutesInput.setText(settings.idleThresholdMinutes.toString())
        binding.defaultDimSwitch.isChecked = settings.defaultDimScreen
        binding.defaultBlackSwitch.isChecked = settings.defaultBlackScreen

        val routing = routingManager.snapshot()
        binding.appAlertSwitch.isChecked = routing.appEnabled
        binding.emailAlertSwitch.isChecked = routing.emailEnabled
        binding.smsAlertSwitch.isChecked = routing.smsEnabled
        binding.appDeviceIdInput.setText(routing.appDeviceId)
        binding.emailInput.setText(routing.emailAddress)
        binding.phoneInput.setText(routing.phoneNumber)

        val pair = pairingManager.snapshot()
        binding.activeVehicleIdInput.setText(pair.activeVehicleId.ifBlank { pair.vehicleId })
        binding.activeRoleInput.setText(pair.activeRole)
        refreshFleetUi(pair)

        val privacy = privacySettingsManager.snapshot()
        binding.blurFacesSwitch.isChecked = privacy.blurFaces
        binding.blurPlatesSwitch.isChecked = privacy.blurPlates
        binding.encryptEventClipsSwitch.isChecked = privacy.encryptEventClips
        binding.retentionLowDaysInput.setText(privacy.retentionLowDays.toString())
        binding.retentionMediumDaysInput.setText(privacy.retentionMediumDays.toString())
        binding.retentionHighDaysInput.setText(privacy.retentionHighDays.toString())

        val geo = geofenceManager.snapshot()
        binding.geofenceEnabledSwitch.isChecked = geo.enabled
        binding.homeLatInput.setText(geo.homeLat?.toString().orEmpty())
        binding.homeLonInput.setText(geo.homeLon?.toString().orEmpty())
        binding.workLatInput.setText(geo.workLat?.toString().orEmpty())
        binding.workLonInput.setText(geo.workLon?.toString().orEmpty())
        binding.geofenceRadiusInput.setText(geo.radiusMeters.toInt().toString())
        when (geo.mode) {
            AlertZoneMode.ANY -> binding.geofenceModeAny.isChecked = true
            AlertZoneMode.HOME_ONLY -> binding.geofenceModeHome.isChecked = true
            AlertZoneMode.WORK_ONLY -> binding.geofenceModeWork.isChecked = true
            AlertZoneMode.PUBLIC_ONLY -> binding.geofenceModePublic.isChecked = true
        }
    }

    private fun selectedZoneMode(): AlertZoneMode {
        return when (binding.geofenceModeGroup.checkedRadioButtonId) {
            binding.geofenceModeHome.id -> AlertZoneMode.HOME_ONLY
            binding.geofenceModeWork.id -> AlertZoneMode.WORK_ONLY
            binding.geofenceModePublic.id -> AlertZoneMode.PUBLIC_ONLY
            else -> AlertZoneMode.ANY
        }
    }

    private fun setActiveVehicleFromInput() {
        val vehicleId = binding.activeVehicleIdInput.text?.toString().orEmpty().trim()
        if (vehicleId.isBlank()) {
            Toast.makeText(this, "Enter vehicle ID", Toast.LENGTH_SHORT).show()
            return
        }
        pairingManager.setActiveVehicle(vehicleId)
        val routing = routingManager.snapshot()
        binding.appAlertSwitch.isChecked = routing.appEnabled
        binding.emailAlertSwitch.isChecked = routing.emailEnabled
        binding.smsAlertSwitch.isChecked = routing.smsEnabled
        binding.appDeviceIdInput.setText(routing.appDeviceId)
        binding.emailInput.setText(routing.emailAddress)
        binding.phoneInput.setText(routing.phoneNumber)
        refreshFleetUi(pairingManager.snapshot())
        Toast.makeText(this, "Active vehicle updated", Toast.LENGTH_SHORT).show()
    }

    private fun saveFleetRoleFromInput() {
        val vehicleId = binding.activeVehicleIdInput.text?.toString().orEmpty().trim()
        if (vehicleId.isBlank()) {
            Toast.makeText(this, "Enter vehicle ID", Toast.LENGTH_SHORT).show()
            return
        }
        val role = normalizeRole(binding.activeRoleInput.text?.toString().orEmpty())
        binding.activeRoleInput.setText(role)
        pairingManager.addOrUpdateFleetVehicle(vehicleId, role)
        val state = pairingManager.snapshot()
        refreshFleetUi(state)

        val ownerId = state.pairedOwnerId.ifBlank { authSessionManager.snapshot().userId }
        if (ownerId.isBlank()) {
            Toast.makeText(this, "Saved locally (no owner ID for backend sync)", Toast.LENGTH_SHORT).show()
            return
        }

        scope.launch {
            val payload = JSONObject()
                .put("owner_id", ownerId)
                .put("vehicle_id", vehicleId)
                .put("role", role)
            val result = withContext(Dispatchers.IO) {
                apiClient.postJson("/api/v1/owner/fleet/role/update", payload, authSessionManager.snapshot().token)
            }
            val message = if (result.success) {
                "Role saved"
            } else {
                "Role saved locally; backend sync failed"
            }
            Toast.makeText(this@SettingsActivity, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun normalizeRole(raw: String): String {
        return when (raw.trim().uppercase()) {
            "OWNER", "ADMIN", "DRIVER", "VIEWER" -> raw.trim().uppercase()
            else -> "OWNER"
        }
    }

    private fun refreshFleetUi(state: com.dashcam.ai.pairing.PairingState) {
        val active = state.activeVehicleId.ifBlank { state.vehicleId.ifBlank { "none" } }
        binding.fleetPolicyTargetText.text = "Routing policy target: $active"
        binding.fleetSummaryText.text = if (state.fleet.isEmpty()) {
            "Fleet: none"
        } else {
            "Fleet: " + state.fleet.joinToString(" | ") { "${it.vehicleId}:${it.role}" }
        }
    }
}

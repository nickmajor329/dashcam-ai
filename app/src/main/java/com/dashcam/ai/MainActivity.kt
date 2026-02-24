package com.dashcam.ai

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.dashcam.ai.auth.AuthSessionManager
import com.dashcam.ai.auth.LoginActivity
import com.dashcam.ai.databinding.ActivityMainBinding
import com.dashcam.ai.live.LiveViewActivity
import com.dashcam.ai.location.ParkedStateManager
import com.dashcam.ai.pairing.PairingActivity
import com.dashcam.ai.remote.VehicleCommandWorker
import com.dashcam.ai.recording.RecordingService
import com.dashcam.ai.recording.ServiceHealthManager
import com.dashcam.ai.settings.AppSettingsManager
import com.dashcam.ai.settings.SettingsActivity
import com.dashcam.ai.timeline.TimelineActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var parkedStateManager: ParkedStateManager
    private lateinit var appSettingsManager: AppSettingsManager
    private lateinit var serviceHealthManager: ServiceHealthManager
    private lateinit var authSessionManager: AuthSessionManager

    private var cameraProvider: ProcessCameraProvider? = null
    private var previewUseCase: Preview? = null
    private var previewStatus = "Preview idle"
    private var selectedLensFacing = CameraSelector.LENS_FACING_BACK
    private val uiRefreshHandler = Handler(Looper.getMainLooper())
    private val uiRefreshRunnable = object : Runnable {
        override fun run() {
            updateUi()
            uiRefreshHandler.postDelayed(this, HEALTH_REFRESH_INTERVAL_MS)
        }
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        startCameraPreviewIfPossible()
        updateUi()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parkedStateManager = ParkedStateManager(this)
        appSettingsManager = AppSettingsManager(this)
        serviceHealthManager = ServiceHealthManager(this)
        authSessionManager = AuthSessionManager(this)
        applyDefaultsFromSettings()

        binding.settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        binding.pairDeviceButton.setOnClickListener {
            if (!authSessionManager.snapshot().loggedIn) {
                Toast.makeText(this, "Sign in first to pair", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                startActivity(Intent(this, PairingActivity::class.java))
            }
        }
        binding.liveViewButton.setOnClickListener {
            startActivity(Intent(this, LiveViewActivity::class.java))
        }
        binding.timelineButton.setOnClickListener {
            startActivity(Intent(this, TimelineActivity::class.java))
        }
        binding.accountButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.startButton.setOnClickListener {
            if (!hasRequiredPermissions()) {
                requestRequiredPermissionsIfNeeded()
                return@setOnClickListener
            }
            ContextCompat.startForegroundService(
                this,
                Intent(this, RecordingService::class.java).apply {
                    action = RecordingService.ACTION_START
                    putExtra(RecordingService.EXTRA_LENS_FACING, selectedLensFacing)
                }
            )
            startCameraPreviewIfPossible()
            updateUi()
        }

        binding.stopButton.setOnClickListener {
            startService(Intent(this, RecordingService::class.java).apply {
                action = RecordingService.ACTION_STOP
            })
            updateUi()
        }

        binding.parkButton.setOnClickListener {
            val location = lastKnownLocation()
            parkedStateManager.markParked(location?.latitude, location?.longitude)
            binding.awaySwitch.isChecked = true
            updateUi()
        }

        binding.driveButton.setOnClickListener {
            parkedStateManager.markDriving()
            binding.awaySwitch.isChecked = false
            updateUi()
        }

        binding.awaySwitch.setOnCheckedChangeListener { _, isChecked ->
            parkedStateManager.setOwnerAwayEnabled(isChecked)
            updateUi()
        }

        binding.cameraToggleButton.setOnClickListener {
            selectedLensFacing = if (selectedLensFacing == CameraSelector.LENS_FACING_BACK) {
                CameraSelector.LENS_FACING_FRONT
            } else {
                CameraSelector.LENS_FACING_BACK
            }
            updateCameraToggleLabel()
            startCameraPreviewIfPossible()
            updateUi()
        }

        binding.dimScreenSwitch.setOnCheckedChangeListener { _, _ ->
            applyScreenMode()
            updateUi()
        }

        binding.blackScreenSwitch.setOnCheckedChangeListener { _, isChecked ->
            applyScreenMode()
            if (isChecked) {
                Toast.makeText(this, "Black screen enabled. Long press anywhere to exit.", Toast.LENGTH_SHORT).show()
            }
            updateUi()
        }

        binding.blackOverlay.setOnLongClickListener {
            binding.blackScreenSwitch.isChecked = false
            true
        }

        requestRequiredPermissionsIfNeeded()
        ensureRemoteCommandPolling()
        triggerImmediateCommandSync()
        syncSwitchState()
        updateCameraToggleLabel()
        applyScreenMode()
        startCameraPreviewIfPossible()
        updateUi()
        maybePromptForLogin()
    }

    override fun onResume() {
        super.onResume()
        applyDefaultsFromSettings()
        updateCameraToggleLabel()
        applyScreenMode()
        triggerImmediateCommandSync()
        startCameraPreviewIfPossible()
        updateUi()
        uiRefreshHandler.removeCallbacks(uiRefreshRunnable)
        uiRefreshHandler.postDelayed(uiRefreshRunnable, HEALTH_REFRESH_INTERVAL_MS)
    }

    override fun onPause() {
        uiRefreshHandler.removeCallbacks(uiRefreshRunnable)
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        runCatching {
            previewUseCase?.let { cameraProvider?.unbind(it) }
        }
    }

    private fun requestRequiredPermissionsIfNeeded() {
        val permissions = buildList {
            add(Manifest.permission.CAMERA)
            add(Manifest.permission.RECORD_AUDIO)
            add(Manifest.permission.ACCESS_FINE_LOCATION)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                add(Manifest.permission.BLUETOOTH_CONNECT)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                add(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        val missing = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (missing.isNotEmpty()) {
            permissionLauncher.launch(missing.toTypedArray())
        }
    }

    private fun startCameraPreviewIfPossible() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            previewStatus = "Preview waiting for camera permission"
            return
        }

        val future = ProcessCameraProvider.getInstance(this)
        future.addListener(
            {
                runCatching {
                    val provider = future.get()
                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(binding.previewView.surfaceProvider)
                    }

                    val selector = CameraSelector.Builder()
                        .requireLensFacing(selectedLensFacing)
                        .build()

                    previewUseCase?.let { provider.unbind(it) }
                    provider.bindToLifecycle(this, selector, preview)
                    cameraProvider = provider
                    previewUseCase = preview
                    previewStatus = "Preview active"
                    updateUi()
                }.onFailure {
                    previewStatus = "Preview unavailable (camera may be busy)"
                    updateUi()
                }
            },
            ContextCompat.getMainExecutor(this)
        )
    }

    private fun applyScreenMode() {
        val isBlack = binding.blackScreenSwitch.isChecked
        val isDim = binding.dimScreenSwitch.isChecked

        val params = window.attributes
        params.screenBrightness = when {
            isBlack -> 0f
            isDim -> 0.01f
            else -> WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE
        }
        window.attributes = params

        binding.blackOverlay.visibility = if (isBlack) View.VISIBLE else View.GONE
    }

    private fun updateUi() {
        val allGranted = hasRequiredPermissions()
        val state = parkedStateManager.snapshot()

        val parkedLabel = if (state.isParked) {
            if (state.parkedLat != null && state.parkedLon != null) {
                "Parked mode ON (${"%.5f".format(state.parkedLat)}, ${"%.5f".format(state.parkedLon)})"
            } else {
                "Parked mode ON (no GPS fix yet)"
            }
        } else {
            "Driving mode"
        }

        val alertGate = if (state.isParked && state.ownerAwayEnabled) {
            "Alerts armed"
        } else {
            "Alerts disarmed"
        }

        val screenMode = when {
            binding.blackScreenSwitch.isChecked -> "Screen black"
            binding.dimScreenSwitch.isChecked -> "Screen dim"
            else -> "Screen normal"
        }
        val lensLabel = if (selectedLensFacing == CameraSelector.LENS_FACING_FRONT) {
            "Front lens"
        } else {
            "Back lens"
        }

        val permissionStatus = if (allGranted) {
            "Permissions OK"
        } else {
            "Missing permissions"
        }

        val session = authSessionManager.snapshot()
        val accountLabel = if (session.loggedIn) {
            "Account ${session.email}"
        } else {
            "Account not signed in"
        }

        binding.statusText.text =
            "$permissionStatus | $parkedLabel | $alertGate | $previewStatus | $lensLabel | $screenMode | $accountLabel"

        val health = serviceHealthManager.snapshot()
        val tempLabel = health.batteryTempTenthsC?.let { "${"%.1f".format(it / 10.0)}C" } ?: "n/a"
        val batteryLabel = health.batteryPct?.let { "$it%" } ?: "n/a"
        val storageLabel = formatBytes(health.freeBytes)
        val recordLabel = if (health.recordingActive) "active" else "inactive"
        val updatedLabel = if (health.lastUpdatedMs > 0L) {
            SimpleDateFormat("h:mm:ss a", Locale.US).format(Date(health.lastUpdatedMs))
        } else {
            "n/a"
        }
        val note = if (health.lastHealthMessage.isBlank()) "no health note" else health.lastHealthMessage
        binding.healthText.text =
            "Health | Rec: $recordLabel | Temp: $tempLabel | Battery: $batteryLabel | Free: $storageLabel | CamFail: ${health.cameraFailureCount} | Updated: $updatedLabel | $note"
    }

    private fun updateCameraToggleLabel() {
        binding.cameraToggleButton.text = if (selectedLensFacing == CameraSelector.LENS_FACING_BACK) {
            "Use Front Camera"
        } else {
            "Use Back Camera"
        }
    }

    private fun maybePromptForLogin() {
        if (authSessionManager.snapshot().loggedIn) return
        Toast.makeText(this, "Sign in to pair this dashcam to your account", Toast.LENGTH_LONG).show()
    }

    private fun syncSwitchState() {
        val state = parkedStateManager.snapshot()
        binding.awaySwitch.isChecked = state.ownerAwayEnabled
    }

    private fun applyDefaultsFromSettings() {
        val settings = appSettingsManager.snapshot()
        selectedLensFacing = settings.defaultLensFacing
        binding.dimScreenSwitch.isChecked = settings.defaultDimScreen
        binding.blackScreenSwitch.isChecked = settings.defaultBlackScreen
    }

    private fun hasRequiredPermissions(): Boolean {
        return REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun lastKnownLocation(): Location? {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            return null
        }

        val manager = getSystemService(LocationManager::class.java) ?: return null
        val providers = listOf(LocationManager.GPS_PROVIDER, LocationManager.NETWORK_PROVIDER)

        return providers
            .asSequence()
            .mapNotNull { provider -> runCatching { manager.getLastKnownLocation(provider) }.getOrNull() }
            .maxByOrNull { it.time }
    }

    companion object {
        private const val REMOTE_COMMAND_WORK = "remote_command_poll"
        private const val HEALTH_REFRESH_INTERVAL_MS = 5_000L
        private val REQUIRED_PERMISSIONS = buildList {
            add(Manifest.permission.CAMERA)
            add(Manifest.permission.RECORD_AUDIO)
            add(Manifest.permission.ACCESS_FINE_LOCATION)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                add(Manifest.permission.BLUETOOTH_CONNECT)
            }
        }
    }

    private fun ensureRemoteCommandPolling() {
        val request = PeriodicWorkRequestBuilder<VehicleCommandWorker>(15, TimeUnit.MINUTES).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            REMOTE_COMMAND_WORK,
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }

    private fun triggerImmediateCommandSync() {
        WorkManager.getInstance(this).enqueue(
            OneTimeWorkRequestBuilder<VehicleCommandWorker>().build()
        )
    }

    private fun formatBytes(bytes: Long): String {
        if (bytes <= 0) return "0 B"
        val kb = 1024L
        val mb = kb * 1024L
        val gb = mb * 1024L
        return when {
            bytes >= gb -> String.format(Locale.US, "%.2f GB", bytes.toDouble() / gb.toDouble())
            bytes >= mb -> String.format(Locale.US, "%.1f MB", bytes.toDouble() / mb.toDouble())
            bytes >= kb -> String.format(Locale.US, "%.1f KB", bytes.toDouble() / kb.toDouble())
            else -> "$bytes B"
        }
    }
}

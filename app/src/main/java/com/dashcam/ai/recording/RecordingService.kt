package com.dashcam.ai.recording

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.BatteryManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.FallbackStrategy
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.PendingRecording
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleService
import com.dashcam.ai.ai.AiEventDetector
import com.dashcam.ai.alerts.AlertDispatcher
import com.dashcam.ai.alerts.AlertRoutingManager
import com.dashcam.ai.data.EventEntity
import com.dashcam.ai.data.EventRepository
import com.dashcam.ai.data.EventType
import com.dashcam.ai.location.ImpactDetector
import com.dashcam.ai.location.MotionIdleDetector
import com.dashcam.ai.location.ParkedStateManager
import com.dashcam.ai.location.GeofenceSettingsManager
import com.dashcam.ai.network.BackendApiClient
import com.dashcam.ai.pairing.PairingManager
import com.dashcam.ai.privacy.ClipCryptoManager
import com.dashcam.ai.privacy.PrivacySettingsManager
import com.dashcam.ai.settings.AppSettingsManager
import java.io.File
import java.util.ArrayDeque
import org.json.JSONObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class RecordingService : LifecycleService() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    @Volatile
    private var isRecording = false

    private lateinit var loopStorageManager: LoopStorageManager
    private lateinit var aiEventDetector: AiEventDetector
    private lateinit var eventRepository: EventRepository
    private lateinit var alertDispatcher: AlertDispatcher
    private lateinit var parkedStateManager: ParkedStateManager
    private lateinit var geofenceSettingsManager: GeofenceSettingsManager
    private lateinit var pairingManager: PairingManager
    private lateinit var routingManager: AlertRoutingManager
    private lateinit var backendApiClient: BackendApiClient
    private lateinit var privacySettingsManager: PrivacySettingsManager
    private lateinit var clipCryptoManager: ClipCryptoManager
    private lateinit var motionIdleDetector: MotionIdleDetector
    private lateinit var impactDetector: ImpactDetector
    private lateinit var settingsManager: AppSettingsManager
    private lateinit var recordingStateManager: RecordingStateManager
    private lateinit var serviceHealthManager: ServiceHealthManager
    private var segmentDurationMs: Long = 60_000L
    private var idleDetectionEnabled: Boolean = true
    private var cameraProvider: ProcessCameraProvider? = null
    private var videoCapture: VideoCapture<Recorder>? = null
    private var activeRecording: Recording? = null
    private var segmentJob: Job? = null
    private var watchdogJob: Job? = null
    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK
    private val recentSegments = ArrayDeque<SegmentWindow>()
    private var postImpactWindowStartMs: Long? = null
    private var postImpactWindowEndMs: Long? = null
    private var currentSegmentFile: File? = null
    private var currentSegmentStartedAtMs: Long? = null
    private var consecutiveCameraFailures = 0
    private val watchdogLastAlertMs = mutableMapOf<String, Long>()

    override fun onCreate() {
        super.onCreate()
        settingsManager = AppSettingsManager(applicationContext)
        recordingStateManager = RecordingStateManager(applicationContext)
        serviceHealthManager = ServiceHealthManager(applicationContext)
        lensFacing = settingsManager.snapshot().defaultLensFacing
        aiEventDetector = AiEventDetector(applicationContext)
        eventRepository = EventRepository(applicationContext)
        alertDispatcher = AlertDispatcher(applicationContext)
        parkedStateManager = ParkedStateManager(applicationContext)
        geofenceSettingsManager = GeofenceSettingsManager(applicationContext)
        pairingManager = PairingManager(applicationContext)
        routingManager = AlertRoutingManager(applicationContext)
        backendApiClient = BackendApiClient()
        privacySettingsManager = PrivacySettingsManager(applicationContext)
        clipCryptoManager = ClipCryptoManager()
        impactDetector = ImpactDetector(
            context = applicationContext,
            onImpact = { handleImpactDetected() }
        )
        refreshSettings()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val requestedLens = intent?.getIntExtra(EXTRA_LENS_FACING, lensFacing) ?: lensFacing
        if (requestedLens == CameraSelector.LENS_FACING_BACK || requestedLens == CameraSelector.LENS_FACING_FRONT) {
            val lensChanged = lensFacing != requestedLens
            lensFacing = requestedLens
            if (lensChanged && isRecording) {
                restartCameraBindingForCurrentLens()
            }
        }

        when (intent?.action) {
            ACTION_START -> startRecordingFlow()
            ACTION_STOP -> stopRecordingFlow()
            ACTION_SET_LENS -> {
                if (!isRecording) {
                    startRecordingFlow()
                }
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        stopRecordingFlow()
        serviceScope.coroutineContext.cancel()
        super.onDestroy()
    }

    private fun startRecordingFlow() {
        if (isRecording) return
        if (!hasPermission(Manifest.permission.CAMERA)) {
            Log.e(TAG, "Cannot start recording: CAMERA permission missing")
            stopSelf()
            return
        }

        isRecording = true
        recordingStateManager.setRecordingActive(true)
        serviceHealthManager.update(recordingActive = true, lastHealthMessage = "Recording active")
        refreshSettings()
        loopStorageManager.ensureStorageRoot()

        ServiceCompat.startForeground(
            this,
            NOTIFICATION_ID,
            buildNotification("Dashcam recording active"),
            FOREGROUND_SERVICE_TYPE_MASK
        )

        if (idleDetectionEnabled) {
            motionIdleDetector.start()
        }
        impactDetector.start()
        startWatchdogLoop()
        initializeCameraAndStartSegments()
    }

    private fun initializeCameraAndStartSegments() {
        val future = ProcessCameraProvider.getInstance(this)
        future.addListener(
            {
                try {
                    val provider = future.get()
                    val recorder = Recorder.Builder()
                        .setExecutor(ContextCompat.getMainExecutor(this))
                        .setQualitySelector(
                            QualitySelector.fromOrderedList(
                                listOf(Quality.FHD, Quality.HD, Quality.SD),
                                FallbackStrategy.lowerQualityOrHigherThan(Quality.SD)
                            )
                        )
                        .build()

                    val capture = VideoCapture.withOutput(recorder)
                    provider.unbindAll()
                    provider.bindToLifecycle(this, selectorForCurrentLens(), capture)

                    cameraProvider = provider
                    videoCapture = capture

                    startNewSegment()
                    startSegmentRotationLoop()
                } catch (t: Throwable) {
                    Log.e(TAG, "Failed to initialize camera recording", t)
                    handleCameraFailure("init_failed")
                }
            },
            ContextCompat.getMainExecutor(this)
        )
    }

    private fun startSegmentRotationLoop() {
        segmentJob?.cancel()
        segmentJob = serviceScope.launch {
            while (isActive && isRecording) {
                delay(segmentDurationMs)
                if (!isRecording) break
                startNewSegment()
            }
        }
    }

    private fun startNewSegment() {
        val capture = videoCapture ?: return

        stopActiveRecording()

        val segmentStartMs = System.currentTimeMillis()
        val file = loopStorageManager.reserveNewClipFile(segmentStartMs)
        currentSegmentFile = file
        currentSegmentStartedAtMs = segmentStartMs
        val outputOptions = FileOutputOptions.Builder(file).build()

        var pending: PendingRecording = capture.output.prepareRecording(this, outputOptions)
        if (hasPermission(Manifest.permission.RECORD_AUDIO)) {
            pending = pending.withAudioEnabled()
        }

        activeRecording = pending.start(ContextCompat.getMainExecutor(this)) { event ->
            when (event) {
                is VideoRecordEvent.Start -> {
                    Log.d(TAG, "Segment started: ${file.absolutePath}")
                }

                is VideoRecordEvent.Finalize -> {
                    val segmentEndMs = System.currentTimeMillis()
                    if (event.hasError()) {
                        Log.e(TAG, "Segment finalize error code=${event.error}")
                        handleCameraFailure("segment_error_${event.error}")
                    } else {
                        consecutiveCameraFailures = 0
                        serviceHealthManager.update(
                            cameraFailureCount = consecutiveCameraFailures,
                            lastHealthMessage = "Segment recorded"
                        )
                    }
                    registerRecentSegment(file, segmentStartMs, segmentEndMs)
                    lockPostImpactIfNeeded(file, segmentStartMs, segmentEndMs)
                    loopStorageManager.enforceRetention()
                    maybeHandleAiEvent(file.absolutePath, System.currentTimeMillis())
                }
            }
        }
    }

    private fun maybeHandleAiEvent(clipPath: String, timestampMs: Long) {
        serviceScope.launch(Dispatchers.IO) {
            val detected = aiEventDetector.detectFromClip(clipPath) ?: return@launch
            val parkedState = parkedStateManager.snapshot()
            if (!parkedState.isParked || !parkedState.ownerAwayEnabled) return@launch
            if (!geofenceSettingsManager.shouldAllowAlert(parkedState.parkedLat, parkedState.parkedLon)) return@launch

            val privacy = privacySettingsManager.snapshot()
            val eventClipPath = persistEventClip(clipPath, privacy.encryptEventClips)
            val eventToSave = EventEntity(
                eventType = detected.eventType,
                confidence = detected.confidence,
                createdAtMs = timestampMs,
                clipPath = eventClipPath
            )
            val savedId = eventRepository.saveEvent(eventToSave)
            alertDispatcher.enqueueAlert(eventToSave.copy(id = savedId))
            enforceEventRetentionPolicy()
        }
    }

    private fun stopRecordingFlow() {
        if (!isRecording) return
        isRecording = false
        recordingStateManager.setRecordingActive(false)
        serviceHealthManager.update(recordingActive = false, lastHealthMessage = "Recording stopped")

        if (idleDetectionEnabled) {
            motionIdleDetector.stop()
        }
        impactDetector.stop()
        segmentJob?.cancel()
        segmentJob = null
        watchdogJob?.cancel()
        watchdogJob = null

        stopActiveRecording()
        cameraProvider?.unbindAll()
        cameraProvider = null
        videoCapture = null

        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun restartCameraBindingForCurrentLens() {
        stopActiveRecording()
        segmentJob?.cancel()
        segmentJob = null
        cameraProvider?.unbindAll()
        initializeCameraAndStartSegments()
    }

    private fun startWatchdogLoop() {
        watchdogJob?.cancel()
        watchdogJob = serviceScope.launch(Dispatchers.IO) {
            while (isActive && isRecording) {
                delay(WATCHDOG_INTERVAL_MS)
                if (!isRecording) break
                runCatching { runWatchdogChecks() }.onFailure {
                    Log.e(TAG, "Watchdog loop failure", it)
                }
            }
        }
    }

    private fun runWatchdogChecks() {
        val freeBytes = filesDir.usableSpace
        val tempTenths = currentBatteryTempTenthsC()
        val batteryPct = currentBatteryPct()
        serviceHealthManager.update(
            recordingActive = isRecording,
            freeBytes = freeBytes,
            batteryPct = batteryPct,
            hasBatteryPct = true,
            batteryTempTenthsC = tempTenths,
            hasBatteryTempTenthsC = true,
            cameraFailureCount = consecutiveCameraFailures,
            lastHealthMessage = "Watchdog check OK"
        )

        if (freeBytes < WATCHDOG_LOW_STORAGE_BYTES) {
            loopStorageManager.enforceRetention()
            serviceHealthManager.update(lastHealthMessage = "Low storage detected")
            maybeSendWatchdogAlert(
                key = "LOW_STORAGE",
                reason = "LOW_STORAGE",
                severity = "WARN",
                message = "Storage critically low: ${freeBytes / (1024 * 1024)} MB free",
                details = JSONObject().put("free_bytes", freeBytes)
            )
        }

        if (tempTenths != null && tempTenths >= WATCHDOG_HIGH_TEMP_TENTHS_C) {
            val tempC = tempTenths / 10.0
            serviceHealthManager.update(lastHealthMessage = "High temperature detected")
            maybeSendWatchdogAlert(
                key = "HIGH_TEMP",
                reason = "HIGH_TEMP",
                severity = "CRITICAL",
                message = "Device temperature high: ${"%.1f".format(tempC)}C",
                details = JSONObject().put("temp_tenths_c", tempTenths)
            )
            serviceScope.launch(Dispatchers.Main) {
                if (isRecording) restartCameraBindingForCurrentLens()
            }
        }

        if (cameraProvider == null || videoCapture == null) {
            handleCameraFailure("watchdog_camera_unbound")
        }

        postHealthStatus()
    }

    private fun handleCameraFailure(reason: String) {
        consecutiveCameraFailures += 1
        Log.e(TAG, "Camera failure [$reason], count=$consecutiveCameraFailures")
        serviceHealthManager.update(
            cameraFailureCount = consecutiveCameraFailures,
            lastHealthMessage = "Camera failure: $reason"
        )

        val severity = if (consecutiveCameraFailures >= 3) "CRITICAL" else "WARN"
        maybeSendWatchdogAlert(
            key = "CAMERA_FAILURE",
            reason = "CAMERA_FAILURE",
            severity = severity,
            message = "Camera pipeline failure ($reason), attempts=$consecutiveCameraFailures",
            details = JSONObject()
                .put("reason", reason)
                .put("attempts", consecutiveCameraFailures)
        )

        if (!isRecording) return
        serviceScope.launch(Dispatchers.Main) {
            if (!isRecording) return@launch
            if (consecutiveCameraFailures >= 3) {
                serviceHealthManager.update(lastHealthMessage = "Service recovery triggered")
                maybeSendWatchdogAlert(
                    key = "SERVICE_RECOVERY",
                    reason = "SERVICE_RECOVERY",
                    severity = "CRITICAL",
                    message = "Restarting recording service after repeated camera failures",
                    details = JSONObject().put("attempts", consecutiveCameraFailures)
                )
                scheduleSelfRecoveryRestart()
            } else {
                restartCameraBindingForCurrentLens()
            }
        }
    }

    private fun scheduleSelfRecoveryRestart() {
        val restartIntent = Intent(this, RecordingService::class.java).apply {
            action = ACTION_START
            putExtra(EXTRA_LENS_FACING, lensFacing)
        }
        ContextCompat.startForegroundService(this, restartIntent)
        stopRecordingFlow()
    }

    private fun postHealthStatus() {
        val pairing = pairingManager.snapshot()
        if (pairing.vehicleId.isBlank()) return
        val batteryPct = currentBatteryPct()
        val payload = JSONObject()
            .put("vehicle_id", pairing.vehicleId)
            .put("source_device", sourceDeviceId())
            .put("recording_active", isRecording)
            .put("free_bytes", filesDir.usableSpace)
            .put("battery_pct", if (batteryPct == null) JSONObject.NULL else batteryPct)
            .put("app_version", com.dashcam.ai.BuildConfig.VERSION_NAME)
            .put("note", "watchdog")
        backendApiClient.postJson("/api/v1/vehicle/health", payload)
    }

    private fun maybeSendWatchdogAlert(
        key: String,
        reason: String,
        severity: String,
        message: String,
        details: JSONObject
    ) {
        val now = System.currentTimeMillis()
        val last = watchdogLastAlertMs[key] ?: 0L
        if (now - last < WATCHDOG_ALERT_MIN_INTERVAL_MS) return
        watchdogLastAlertMs[key] = now

        val pairing = pairingManager.snapshot()
        if (pairing.vehicleId.isBlank()) return
        val routing = routingManager.snapshot()
        val payload = JSONObject()
            .put("vehicle_id", pairing.vehicleId)
            .put("owner_id", pairing.pairedOwnerId)
            .put("source_device", sourceDeviceId())
            .put("reason", reason)
            .put("severity", severity)
            .put("message", message)
            .put("details", details)
            .put("route_app_enabled", routing.appEnabled)
            .put("route_email_enabled", routing.emailEnabled)
            .put("route_sms_enabled", routing.smsEnabled)
            .put("target_app_device_id", routing.appDeviceId)
            .put("target_email", routing.emailAddress)
            .put("target_phone", routing.phoneNumber)

        backendApiClient.postJson("/api/v1/vehicle/watchdog/alert", payload)
    }

    private fun currentBatteryTempTenthsC(): Int? {
        val batteryIntent = registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED)) ?: return null
        val temp = batteryIntent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, Int.MIN_VALUE)
        return if (temp == Int.MIN_VALUE) null else temp
    }

    private fun currentBatteryPct(): Int? {
        val batteryIntent = registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED)) ?: return null
        val level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        if (level < 0 || scale <= 0) return null
        return ((level.toFloat() / scale.toFloat()) * 100f).toInt()
    }

    private fun sourceDeviceId(): String {
        return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID).orEmpty()
    }

    private fun stopActiveRecording() {
        activeRecording?.stop()
        activeRecording = null
    }

    private fun buildNotification(contentText: String): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.presence_video_online)
            .setContentTitle("Dashcam AI")
            .setContentText(contentText)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val channel = NotificationChannel(
            CHANNEL_ID,
            "Dashcam Recording",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "Persistent notification for active dashcam recording"
        }

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    private fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        const val ACTION_START = "com.dashcam.ai.action.START_RECORDING"
        const val ACTION_STOP = "com.dashcam.ai.action.STOP_RECORDING"
        const val ACTION_SET_LENS = "com.dashcam.ai.action.SET_LENS"
        const val EXTRA_LENS_FACING = "com.dashcam.ai.extra.LENS_FACING"

        private const val CHANNEL_ID = "dashcam_recording"
        private const val NOTIFICATION_ID = 1001
        private const val TAG = "RecordingService"
        private const val WATCHDOG_INTERVAL_MS = 60_000L
        private const val WATCHDOG_ALERT_MIN_INTERVAL_MS = 10 * 60_000L
        private const val WATCHDOG_LOW_STORAGE_BYTES = 350L * 1024L * 1024L
        private const val WATCHDOG_HIGH_TEMP_TENTHS_C = 470
        private const val IMPACT_PRE_WINDOW_MS = 30_000L
        private const val IMPACT_POST_WINDOW_MS = 30_000L

        private val FOREGROUND_SERVICE_TYPE_MASK =
            android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_CAMERA or
                android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_MICROPHONE or
                android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION
    }

    private fun selectorForCurrentLens(): CameraSelector {
        return CameraSelector.Builder()
            .requireLensFacing(lensFacing)
            .build()
    }

    private fun refreshSettings() {
        val settings = settingsManager.snapshot()
        segmentDurationMs = settings.segmentDurationSeconds * 1000L
        idleDetectionEnabled = settings.idleDetectionEnabled
        loopStorageManager = LoopStorageManager(
            context = applicationContext,
            maxStorageBytes = settings.maxStorageGb * 1024L * 1024L * 1024L
        )
        rebuildMotionIdleDetector(settings.idleThresholdMinutes)
    }

    private fun rebuildMotionIdleDetector(idleThresholdMinutes: Int) {
        if (::motionIdleDetector.isInitialized) {
            motionIdleDetector.stop()
        }

        motionIdleDetector = MotionIdleDetector(
            context = applicationContext,
            scope = serviceScope,
            idleThresholdMs = idleThresholdMinutes * 60_000L,
            onIdleDetected = {
                val state = parkedStateManager.snapshot()
                if (!state.isParked) {
                    parkedStateManager.markParked(lat = null, lon = null)
                    Log.d(TAG, "Idle threshold hit: auto-marked parked")
                }
            },
            onMotionResumed = {
                val state = parkedStateManager.snapshot()
                if (state.isParked) {
                    parkedStateManager.markDriving()
                    Log.d(TAG, "Motion resumed: auto-marked driving")
                }
            }
        )
    }

    private fun registerRecentSegment(file: File, startMs: Long, endMs: Long) {
        recentSegments.addLast(SegmentWindow(file = file, startMs = startMs, endMs = endMs))
        while (recentSegments.size > 6) {
            recentSegments.removeFirst()
        }
    }

    private fun lockPostImpactIfNeeded(file: File, segmentStartMs: Long, segmentEndMs: Long) {
        val postStart = postImpactWindowStartMs ?: return
        val postEnd = postImpactWindowEndMs ?: return
        if (!overlaps(segmentStartMs, segmentEndMs, postStart, postEnd)) {
            if (segmentEndMs >= postEnd) {
                postImpactWindowStartMs = null
                postImpactWindowEndMs = null
            }
            return
        }

        loopStorageManager.lockClip(
            source = file,
            encrypt = privacySettingsManager.snapshot().encryptEventClips,
            cryptoManager = clipCryptoManager
        )
        if (segmentEndMs >= postEnd) {
            postImpactWindowStartMs = null
            postImpactWindowEndMs = null
        }
    }

    private fun handleImpactDetected() {
        val impactMs = System.currentTimeMillis()
        val preWindowStartMs = impactMs - IMPACT_PRE_WINDOW_MS
        val postWindowEndMs = impactMs + IMPACT_POST_WINDOW_MS

        postImpactWindowStartMs = impactMs
        postImpactWindowEndMs = postWindowEndMs

        val filesToLock = buildList {
            recentSegments
                .filter { segment -> overlaps(segment.startMs, segment.endMs, preWindowStartMs, impactMs) }
                .forEach { add(it.file) }

            val currentFile = currentSegmentFile
            val currentStart = currentSegmentStartedAtMs
            if (currentFile != null && currentStart != null && currentStart <= impactMs) {
                add(currentFile)
            }
        }.distinct()

        if (filesToLock.isEmpty()) return
        var representativeLockedPath = filesToLock.first().absolutePath
        for (file in filesToLock) {
            val locked = loopStorageManager.lockClip(
                source = file,
                encrypt = privacySettingsManager.snapshot().encryptEventClips,
                cryptoManager = clipCryptoManager
            )
            if (locked != null) {
                representativeLockedPath = locked.absolutePath
            }
        }

        serviceScope.launch(Dispatchers.IO) {
            val parked = parkedStateManager.snapshot()
            if (!parked.isParked || !parked.ownerAwayEnabled) return@launch
            if (!geofenceSettingsManager.shouldAllowAlert(parked.parkedLat, parked.parkedLon)) return@launch

            val event = EventEntity(
                eventType = EventType.IMPACT,
                confidence = 1.0f,
                createdAtMs = System.currentTimeMillis(),
                clipPath = representativeLockedPath
            )
            val id = eventRepository.saveEvent(event)
            alertDispatcher.enqueueAlert(event.copy(id = id))
            enforceEventRetentionPolicy()
        }
    }

    private suspend fun enforceEventRetentionPolicy() {
        val privacy = privacySettingsManager.snapshot()
        val events = eventRepository.timeline(eventType = null, pendingOnly = false)
        loopStorageManager.enforceEventRetention(events, privacy)
    }

    private fun persistEventClip(sourcePath: String, encrypt: Boolean): String {
        val source = File(sourcePath)
        if (!source.exists()) return sourcePath
        val locked = loopStorageManager.lockClip(
            source = source,
            encrypt = encrypt,
            cryptoManager = clipCryptoManager
        )
        return locked?.absolutePath ?: sourcePath
    }

    private fun overlaps(aStart: Long, aEnd: Long, bStart: Long, bEnd: Long): Boolean {
        return aStart <= bEnd && bStart <= aEnd
    }

    private data class SegmentWindow(
        val file: File,
        val startMs: Long,
        val endMs: Long
    )
}

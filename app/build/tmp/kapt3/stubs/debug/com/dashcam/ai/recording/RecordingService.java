package com.dashcam.ai.recording;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00ec\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 \u0082\u00012\u00020\u0001:\u0004\u0082\u0001\u0083\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020BH\u0002J\b\u0010F\u001a\u00020GH\u0002J\u000f\u0010H\u001a\u0004\u0018\u00010\u0010H\u0002\u00a2\u0006\u0002\u0010IJ\u000f\u0010J\u001a\u0004\u0018\u00010\u0010H\u0002\u00a2\u0006\u0002\u0010IJ\u000e\u0010K\u001a\u00020GH\u0082@\u00a2\u0006\u0002\u0010LJ\u0010\u0010M\u001a\u00020G2\u0006\u0010N\u001a\u00020BH\u0002J\b\u0010O\u001a\u00020GH\u0002J\u0010\u0010P\u001a\u00020\u001b2\u0006\u0010Q\u001a\u00020BH\u0002J\b\u0010R\u001a\u00020GH\u0002J \u0010S\u001a\u00020G2\u0006\u0010T\u001a\u00020\u00122\u0006\u0010U\u001a\u00020\u00142\u0006\u0010V\u001a\u00020\u0014H\u0002J\u0018\u0010W\u001a\u00020G2\u0006\u0010X\u001a\u00020B2\u0006\u0010Y\u001a\u00020\u0014H\u0002J0\u0010Z\u001a\u00020G2\u0006\u0010[\u001a\u00020B2\u0006\u0010N\u001a\u00020B2\u0006\u0010\\\u001a\u00020B2\u0006\u0010]\u001a\u00020B2\u0006\u0010^\u001a\u00020_H\u0002J\b\u0010`\u001a\u00020GH\u0016J\b\u0010a\u001a\u00020GH\u0016J\"\u0010b\u001a\u00020\u00102\b\u0010c\u001a\u0004\u0018\u00010d2\u0006\u0010e\u001a\u00020\u00102\u0006\u0010f\u001a\u00020\u0010H\u0016J(\u0010g\u001a\u00020\u001b2\u0006\u0010h\u001a\u00020\u00142\u0006\u0010i\u001a\u00020\u00142\u0006\u0010j\u001a\u00020\u00142\u0006\u0010k\u001a\u00020\u0014H\u0002J\u0018\u0010l\u001a\u00020B2\u0006\u0010m\u001a\u00020B2\u0006\u0010n\u001a\u00020\u001bH\u0002J\b\u0010o\u001a\u00020GH\u0002J\u0010\u0010p\u001a\u00020G2\u0006\u0010q\u001a\u00020\u0010H\u0002J\b\u0010r\u001a\u00020GH\u0002J \u0010s\u001a\u00020G2\u0006\u0010T\u001a\u00020\u00122\u0006\u0010t\u001a\u00020\u00142\u0006\u0010u\u001a\u00020\u0014H\u0002J\b\u0010v\u001a\u00020GH\u0002J\b\u0010w\u001a\u00020GH\u0002J\b\u0010x\u001a\u00020GH\u0002J\b\u0010y\u001a\u00020zH\u0002J\b\u0010{\u001a\u00020BH\u0002J\b\u0010|\u001a\u00020GH\u0002J\b\u0010}\u001a\u00020GH\u0002J\b\u0010~\u001a\u00020GH\u0002J\b\u0010\u007f\u001a\u00020GH\u0002J\t\u0010\u0080\u0001\u001a\u00020GH\u0002J\t\u0010\u0081\u0001\u001a\u00020GH\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020#X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\'X\u0082.\u00a2\u0006\u0002\n\u0000R\u0012\u0010(\u001a\u0004\u0018\u00010\u0014X\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\u0015R\u0012\u0010)\u001a\u0004\u0018\u00010\u0014X\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\u0015R\u000e\u0010*\u001a\u00020+X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010,\u001a\b\u0012\u0004\u0012\u00020.0-X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u000200X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u00101\u001a\u000202X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u00103\u001a\u00020\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u00104\u001a\u0004\u0018\u000105X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00106\u001a\u000207X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u00108\u001a\u000209X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010:\u001a\u00020;X\u0082.\u00a2\u0006\u0002\n\u0000R\u0016\u0010<\u001a\n\u0012\u0004\u0012\u00020>\u0018\u00010=X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010?\u001a\u0004\u0018\u000105X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010@\u001a\u000e\u0012\u0004\u0012\u00020B\u0012\u0004\u0012\u00020\u00140AX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0084\u0001"}, d2 = {"Lcom/dashcam/ai/recording/RecordingService;", "Landroidx/lifecycle/LifecycleService;", "()V", "activeRecording", "Landroidx/camera/video/Recording;", "aiEventDetector", "Lcom/dashcam/ai/ai/AiEventDetector;", "alertDispatcher", "Lcom/dashcam/ai/alerts/AlertDispatcher;", "backendApiClient", "Lcom/dashcam/ai/network/BackendApiClient;", "cameraProvider", "Landroidx/camera/lifecycle/ProcessCameraProvider;", "clipCryptoManager", "Lcom/dashcam/ai/privacy/ClipCryptoManager;", "consecutiveCameraFailures", "", "currentSegmentFile", "Ljava/io/File;", "currentSegmentStartedAtMs", "", "Ljava/lang/Long;", "eventRepository", "Lcom/dashcam/ai/data/EventRepository;", "geofenceSettingsManager", "Lcom/dashcam/ai/location/GeofenceSettingsManager;", "idleDetectionEnabled", "", "impactDetector", "Lcom/dashcam/ai/location/ImpactDetector;", "isRecording", "lensFacing", "loopStorageManager", "Lcom/dashcam/ai/recording/LoopStorageManager;", "motionIdleDetector", "Lcom/dashcam/ai/location/MotionIdleDetector;", "pairingManager", "Lcom/dashcam/ai/pairing/PairingManager;", "parkedStateManager", "Lcom/dashcam/ai/location/ParkedStateManager;", "postImpactWindowEndMs", "postImpactWindowStartMs", "privacySettingsManager", "Lcom/dashcam/ai/privacy/PrivacySettingsManager;", "recentSegments", "Ljava/util/ArrayDeque;", "Lcom/dashcam/ai/recording/RecordingService$SegmentWindow;", "recordingStateManager", "Lcom/dashcam/ai/recording/RecordingStateManager;", "routingManager", "Lcom/dashcam/ai/alerts/AlertRoutingManager;", "segmentDurationMs", "segmentJob", "Lkotlinx/coroutines/Job;", "serviceHealthManager", "Lcom/dashcam/ai/recording/ServiceHealthManager;", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "settingsManager", "Lcom/dashcam/ai/settings/AppSettingsManager;", "videoCapture", "Landroidx/camera/video/VideoCapture;", "Landroidx/camera/video/Recorder;", "watchdogJob", "watchdogLastAlertMs", "", "", "buildNotification", "Landroid/app/Notification;", "contentText", "createNotificationChannel", "", "currentBatteryPct", "()Ljava/lang/Integer;", "currentBatteryTempTenthsC", "enforceEventRetentionPolicy", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "handleCameraFailure", "reason", "handleImpactDetected", "hasPermission", "permission", "initializeCameraAndStartSegments", "lockPostImpactIfNeeded", "file", "segmentStartMs", "segmentEndMs", "maybeHandleAiEvent", "clipPath", "timestampMs", "maybeSendWatchdogAlert", "key", "severity", "message", "details", "Lorg/json/JSONObject;", "onCreate", "onDestroy", "onStartCommand", "intent", "Landroid/content/Intent;", "flags", "startId", "overlaps", "aStart", "aEnd", "bStart", "bEnd", "persistEventClip", "sourcePath", "encrypt", "postHealthStatus", "rebuildMotionIdleDetector", "idleThresholdMinutes", "refreshSettings", "registerRecentSegment", "startMs", "endMs", "restartCameraBindingForCurrentLens", "runWatchdogChecks", "scheduleSelfRecoveryRestart", "selectorForCurrentLens", "Landroidx/camera/core/CameraSelector;", "sourceDeviceId", "startNewSegment", "startRecordingFlow", "startSegmentRotationLoop", "startWatchdogLoop", "stopActiveRecording", "stopRecordingFlow", "Companion", "SegmentWindow", "app_debug"})
public final class RecordingService extends androidx.lifecycle.LifecycleService {
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    @kotlin.jvm.Volatile()
    private volatile boolean isRecording = false;
    private com.dashcam.ai.recording.LoopStorageManager loopStorageManager;
    private com.dashcam.ai.ai.AiEventDetector aiEventDetector;
    private com.dashcam.ai.data.EventRepository eventRepository;
    private com.dashcam.ai.alerts.AlertDispatcher alertDispatcher;
    private com.dashcam.ai.location.ParkedStateManager parkedStateManager;
    private com.dashcam.ai.location.GeofenceSettingsManager geofenceSettingsManager;
    private com.dashcam.ai.pairing.PairingManager pairingManager;
    private com.dashcam.ai.alerts.AlertRoutingManager routingManager;
    private com.dashcam.ai.network.BackendApiClient backendApiClient;
    private com.dashcam.ai.privacy.PrivacySettingsManager privacySettingsManager;
    private com.dashcam.ai.privacy.ClipCryptoManager clipCryptoManager;
    private com.dashcam.ai.location.MotionIdleDetector motionIdleDetector;
    private com.dashcam.ai.location.ImpactDetector impactDetector;
    private com.dashcam.ai.settings.AppSettingsManager settingsManager;
    private com.dashcam.ai.recording.RecordingStateManager recordingStateManager;
    private com.dashcam.ai.recording.ServiceHealthManager serviceHealthManager;
    private long segmentDurationMs = 60000L;
    private boolean idleDetectionEnabled = true;
    @org.jetbrains.annotations.Nullable()
    private androidx.camera.lifecycle.ProcessCameraProvider cameraProvider;
    @org.jetbrains.annotations.Nullable()
    private androidx.camera.video.VideoCapture<androidx.camera.video.Recorder> videoCapture;
    @org.jetbrains.annotations.Nullable()
    private androidx.camera.video.Recording activeRecording;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job segmentJob;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job watchdogJob;
    private int lensFacing = androidx.camera.core.CameraSelector.LENS_FACING_BACK;
    @org.jetbrains.annotations.NotNull()
    private final java.util.ArrayDeque<com.dashcam.ai.recording.RecordingService.SegmentWindow> recentSegments = null;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Long postImpactWindowStartMs;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Long postImpactWindowEndMs;
    @org.jetbrains.annotations.Nullable()
    private java.io.File currentSegmentFile;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Long currentSegmentStartedAtMs;
    private int consecutiveCameraFailures = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, java.lang.Long> watchdogLastAlertMs = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_START = "com.dashcam.ai.action.START_RECORDING";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_STOP = "com.dashcam.ai.action.STOP_RECORDING";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_SET_LENS = "com.dashcam.ai.action.SET_LENS";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_LENS_FACING = "com.dashcam.ai.extra.LENS_FACING";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CHANNEL_ID = "dashcam_recording";
    private static final int NOTIFICATION_ID = 1001;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "RecordingService";
    private static final long WATCHDOG_INTERVAL_MS = 60000L;
    private static final long WATCHDOG_ALERT_MIN_INTERVAL_MS = 600000L;
    private static final long WATCHDOG_LOW_STORAGE_BYTES = 367001600L;
    private static final int WATCHDOG_HIGH_TEMP_TENTHS_C = 470;
    private static final long IMPACT_PRE_WINDOW_MS = 30000L;
    private static final long IMPACT_POST_WINDOW_MS = 30000L;
    private static final int FOREGROUND_SERVICE_TYPE_MASK = 200;
    @org.jetbrains.annotations.NotNull()
    public static final com.dashcam.ai.recording.RecordingService.Companion Companion = null;
    
    public RecordingService() {
        super();
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @java.lang.Override()
    public int onStartCommand(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    private final void startRecordingFlow() {
    }
    
    private final void initializeCameraAndStartSegments() {
    }
    
    private final void startSegmentRotationLoop() {
    }
    
    private final void startNewSegment() {
    }
    
    private final void maybeHandleAiEvent(java.lang.String clipPath, long timestampMs) {
    }
    
    private final void stopRecordingFlow() {
    }
    
    private final void restartCameraBindingForCurrentLens() {
    }
    
    private final void startWatchdogLoop() {
    }
    
    private final void runWatchdogChecks() {
    }
    
    private final void handleCameraFailure(java.lang.String reason) {
    }
    
    private final void scheduleSelfRecoveryRestart() {
    }
    
    private final void postHealthStatus() {
    }
    
    private final void maybeSendWatchdogAlert(java.lang.String key, java.lang.String reason, java.lang.String severity, java.lang.String message, org.json.JSONObject details) {
    }
    
    private final java.lang.Integer currentBatteryTempTenthsC() {
        return null;
    }
    
    private final java.lang.Integer currentBatteryPct() {
        return null;
    }
    
    private final java.lang.String sourceDeviceId() {
        return null;
    }
    
    private final void stopActiveRecording() {
    }
    
    private final android.app.Notification buildNotification(java.lang.String contentText) {
        return null;
    }
    
    private final void createNotificationChannel() {
    }
    
    private final boolean hasPermission(java.lang.String permission) {
        return false;
    }
    
    private final androidx.camera.core.CameraSelector selectorForCurrentLens() {
        return null;
    }
    
    private final void refreshSettings() {
    }
    
    private final void rebuildMotionIdleDetector(int idleThresholdMinutes) {
    }
    
    private final void registerRecentSegment(java.io.File file, long startMs, long endMs) {
    }
    
    private final void lockPostImpactIfNeeded(java.io.File file, long segmentStartMs, long segmentEndMs) {
    }
    
    private final void handleImpactDetected() {
    }
    
    private final java.lang.Object enforceEventRetentionPolicy(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.String persistEventClip(java.lang.String sourcePath, boolean encrypt) {
        return null;
    }
    
    private final boolean overlaps(long aStart, long aEnd, long bStart, long bEnd) {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\nX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\fX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\nX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\fX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\fX\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/dashcam/ai/recording/RecordingService$Companion;", "", "()V", "ACTION_SET_LENS", "", "ACTION_START", "ACTION_STOP", "CHANNEL_ID", "EXTRA_LENS_FACING", "FOREGROUND_SERVICE_TYPE_MASK", "", "IMPACT_POST_WINDOW_MS", "", "IMPACT_PRE_WINDOW_MS", "NOTIFICATION_ID", "TAG", "WATCHDOG_ALERT_MIN_INTERVAL_MS", "WATCHDOG_HIGH_TEMP_TENTHS_C", "WATCHDOG_INTERVAL_MS", "WATCHDOG_LOW_STORAGE_BYTES", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000e\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u000f\u001a\u00020\u0005H\u00c6\u0003J\'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t\u00a8\u0006\u0018"}, d2 = {"Lcom/dashcam/ai/recording/RecordingService$SegmentWindow;", "", "file", "Ljava/io/File;", "startMs", "", "endMs", "(Ljava/io/File;JJ)V", "getEndMs", "()J", "getFile", "()Ljava/io/File;", "getStartMs", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
    static final class SegmentWindow {
        @org.jetbrains.annotations.NotNull()
        private final java.io.File file = null;
        private final long startMs = 0L;
        private final long endMs = 0L;
        
        public SegmentWindow(@org.jetbrains.annotations.NotNull()
        java.io.File file, long startMs, long endMs) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.io.File getFile() {
            return null;
        }
        
        public final long getStartMs() {
            return 0L;
        }
        
        public final long getEndMs() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.io.File component1() {
            return null;
        }
        
        public final long component2() {
            return 0L;
        }
        
        public final long component3() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.dashcam.ai.recording.RecordingService.SegmentWindow copy(@org.jetbrains.annotations.NotNull()
        java.io.File file, long startMs, long endMs) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}
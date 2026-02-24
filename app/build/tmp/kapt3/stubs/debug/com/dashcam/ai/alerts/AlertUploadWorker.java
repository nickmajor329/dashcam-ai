package com.dashcam.ai.alerts;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\r\u001a\u00020\u000eH\u0096@\u00a2\u0006\u0002\u0010\u000fR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/dashcam/ai/alerts/AlertUploadWorker;", "Landroidx/work/CoroutineWorker;", "appContext", "Landroid/content/Context;", "params", "Landroidx/work/WorkerParameters;", "(Landroid/content/Context;Landroidx/work/WorkerParameters;)V", "apiClient", "Lcom/dashcam/ai/alerts/AlertApiClient;", "clipCryptoManager", "Lcom/dashcam/ai/privacy/ClipCryptoManager;", "repository", "Lcom/dashcam/ai/data/EventRepository;", "doWork", "Landroidx/work/ListenableWorker$Result;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
public final class AlertUploadWorker extends androidx.work.CoroutineWorker {
    @org.jetbrains.annotations.NotNull()
    private final com.dashcam.ai.data.EventRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.dashcam.ai.alerts.AlertApiClient apiClient = null;
    @org.jetbrains.annotations.NotNull()
    private final com.dashcam.ai.privacy.ClipCryptoManager clipCryptoManager = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_EVENT_ID = "event_id";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_EVENT_TYPE = "event_type";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_CLIP_PATH = "clip_path";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_CREATED_AT_MS = "created_at_ms";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_OWNER_ID = "owner_id";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_VEHICLE_ID = "vehicle_id";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_APP_ENABLED = "app_enabled";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_EMAIL_ENABLED = "email_enabled";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_SMS_ENABLED = "sms_enabled";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_APP_DEVICE_ID = "app_device_id";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_EMAIL_ADDRESS = "email_address";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_PHONE_NUMBER = "phone_number";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_BLUR_FACES = "blur_faces";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_BLUR_PLATES = "blur_plates";
    @org.jetbrains.annotations.NotNull()
    public static final com.dashcam.ai.alerts.AlertUploadWorker.Companion Companion = null;
    
    public AlertUploadWorker(@org.jetbrains.annotations.NotNull()
    android.content.Context appContext, @org.jetbrains.annotations.NotNull()
    androidx.work.WorkerParameters params) {
        super(null, null);
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object doWork(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super androidx.work.ListenableWorker.Result> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/dashcam/ai/alerts/AlertUploadWorker$Companion;", "", "()V", "KEY_APP_DEVICE_ID", "", "KEY_APP_ENABLED", "KEY_BLUR_FACES", "KEY_BLUR_PLATES", "KEY_CLIP_PATH", "KEY_CREATED_AT_MS", "KEY_EMAIL_ADDRESS", "KEY_EMAIL_ENABLED", "KEY_EVENT_ID", "KEY_EVENT_TYPE", "KEY_OWNER_ID", "KEY_PHONE_NUMBER", "KEY_SMS_ENABLED", "KEY_VEHICLE_ID", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}
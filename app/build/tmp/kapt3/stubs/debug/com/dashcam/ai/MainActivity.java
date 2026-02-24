package com.dashcam.ai;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u0000 42\u00020\u0001:\u00014B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u001c\u001a\u00020\u001dH\u0002J\b\u0010\u001e\u001a\u00020\u001dH\u0002J\b\u0010\u001f\u001a\u00020\u001dH\u0002J\u0010\u0010 \u001a\u00020\u00102\u0006\u0010!\u001a\u00020\"H\u0002J\b\u0010#\u001a\u00020$H\u0002J\n\u0010%\u001a\u0004\u0018\u00010&H\u0002J\b\u0010\'\u001a\u00020\u001dH\u0002J\u0012\u0010(\u001a\u00020\u001d2\b\u0010)\u001a\u0004\u0018\u00010*H\u0014J\b\u0010+\u001a\u00020\u001dH\u0014J\b\u0010,\u001a\u00020\u001dH\u0014J\b\u0010-\u001a\u00020\u001dH\u0014J\b\u0010.\u001a\u00020\u001dH\u0002J\b\u0010/\u001a\u00020\u001dH\u0002J\b\u00100\u001a\u00020\u001dH\u0002J\b\u00101\u001a\u00020\u001dH\u0002J\b\u00102\u001a\u00020\u001dH\u0002J\b\u00103\u001a\u00020\u001dH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00065"}, d2 = {"Lcom/dashcam/ai/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "appSettingsManager", "Lcom/dashcam/ai/settings/AppSettingsManager;", "authSessionManager", "Lcom/dashcam/ai/auth/AuthSessionManager;", "binding", "Lcom/dashcam/ai/databinding/ActivityMainBinding;", "cameraProvider", "Landroidx/camera/lifecycle/ProcessCameraProvider;", "parkedStateManager", "Lcom/dashcam/ai/location/ParkedStateManager;", "permissionLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "", "", "previewStatus", "previewUseCase", "Landroidx/camera/core/Preview;", "selectedLensFacing", "", "serviceHealthManager", "Lcom/dashcam/ai/recording/ServiceHealthManager;", "uiRefreshHandler", "Landroid/os/Handler;", "uiRefreshRunnable", "Ljava/lang/Runnable;", "applyDefaultsFromSettings", "", "applyScreenMode", "ensureRemoteCommandPolling", "formatBytes", "bytes", "", "hasRequiredPermissions", "", "lastKnownLocation", "Landroid/location/Location;", "maybePromptForLogin", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onPause", "onResume", "requestRequiredPermissionsIfNeeded", "startCameraPreviewIfPossible", "syncSwitchState", "triggerImmediateCommandSync", "updateCameraToggleLabel", "updateUi", "Companion", "app_debug"})
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.dashcam.ai.databinding.ActivityMainBinding binding;
    private com.dashcam.ai.location.ParkedStateManager parkedStateManager;
    private com.dashcam.ai.settings.AppSettingsManager appSettingsManager;
    private com.dashcam.ai.recording.ServiceHealthManager serviceHealthManager;
    private com.dashcam.ai.auth.AuthSessionManager authSessionManager;
    @org.jetbrains.annotations.Nullable()
    private androidx.camera.lifecycle.ProcessCameraProvider cameraProvider;
    @org.jetbrains.annotations.Nullable()
    private androidx.camera.core.Preview previewUseCase;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String previewStatus = "Preview idle";
    private int selectedLensFacing = androidx.camera.core.CameraSelector.LENS_FACING_BACK;
    @org.jetbrains.annotations.NotNull()
    private final android.os.Handler uiRefreshHandler = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.Runnable uiRefreshRunnable = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String[]> permissionLauncher = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String REMOTE_COMMAND_WORK = "remote_command_poll";
    private static final long HEALTH_REFRESH_INTERVAL_MS = 5000L;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> REQUIRED_PERMISSIONS = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.dashcam.ai.MainActivity.Companion Companion = null;
    
    public MainActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    @java.lang.Override()
    protected void onPause() {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    private final void requestRequiredPermissionsIfNeeded() {
    }
    
    private final void startCameraPreviewIfPossible() {
    }
    
    private final void applyScreenMode() {
    }
    
    private final void updateUi() {
    }
    
    private final void updateCameraToggleLabel() {
    }
    
    private final void maybePromptForLogin() {
    }
    
    private final void syncSwitchState() {
    }
    
    private final void applyDefaultsFromSettings() {
    }
    
    private final boolean hasRequiredPermissions() {
        return false;
    }
    
    private final android.location.Location lastKnownLocation() {
        return null;
    }
    
    private final void ensureRemoteCommandPolling() {
    }
    
    private final void triggerImmediateCommandSync() {
    }
    
    private final java.lang.String formatBytes(long bytes) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/dashcam/ai/MainActivity$Companion;", "", "()V", "HEALTH_REFRESH_INTERVAL_MS", "", "REMOTE_COMMAND_WORK", "", "REQUIRED_PERMISSIONS", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}
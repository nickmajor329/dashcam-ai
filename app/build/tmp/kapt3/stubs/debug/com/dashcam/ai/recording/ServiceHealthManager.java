package com.dashcam.ai.recording;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\b\u001a\u00020\tJg\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\b\b\u0002\u0010\u0012\u001a\u00020\r2\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00112\b\b\u0002\u0010\u0014\u001a\u00020\r2\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00112\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0017\u00a2\u0006\u0002\u0010\u0018R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/dashcam/ai/recording/ServiceHealthManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "prefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "snapshot", "Lcom/dashcam/ai/recording/ServiceHealthSnapshot;", "update", "", "recordingActive", "", "freeBytes", "", "batteryPct", "", "hasBatteryPct", "batteryTempTenthsC", "hasBatteryTempTenthsC", "cameraFailureCount", "lastHealthMessage", "", "(Ljava/lang/Boolean;Ljava/lang/Long;Ljava/lang/Integer;ZLjava/lang/Integer;ZLjava/lang/Integer;Ljava/lang/String;)V", "Companion", "app_debug"})
public final class ServiceHealthManager {
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFS_NAME = "service_health_state";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_RECORDING_ACTIVE = "recording_active";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_FREE_BYTES = "free_bytes";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_BATTERY_PCT = "battery_pct";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_BATTERY_TEMP_TENTHS_C = "battery_temp_tenths_c";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_CAMERA_FAILURE_COUNT = "camera_failure_count";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_LAST_MESSAGE = "last_message";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_LAST_UPDATED_MS = "last_updated_ms";
    @org.jetbrains.annotations.NotNull()
    public static final com.dashcam.ai.recording.ServiceHealthManager.Companion Companion = null;
    
    public ServiceHealthManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.dashcam.ai.recording.ServiceHealthSnapshot snapshot() {
        return null;
    }
    
    public final void update(@org.jetbrains.annotations.Nullable()
    java.lang.Boolean recordingActive, @org.jetbrains.annotations.Nullable()
    java.lang.Long freeBytes, @org.jetbrains.annotations.Nullable()
    java.lang.Integer batteryPct, boolean hasBatteryPct, @org.jetbrains.annotations.Nullable()
    java.lang.Integer batteryTempTenthsC, boolean hasBatteryTempTenthsC, @org.jetbrains.annotations.Nullable()
    java.lang.Integer cameraFailureCount, @org.jetbrains.annotations.Nullable()
    java.lang.String lastHealthMessage) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/dashcam/ai/recording/ServiceHealthManager$Companion;", "", "()V", "KEY_BATTERY_PCT", "", "KEY_BATTERY_TEMP_TENTHS_C", "KEY_CAMERA_FAILURE_COUNT", "KEY_FREE_BYTES", "KEY_LAST_MESSAGE", "KEY_LAST_UPDATED_MS", "KEY_RECORDING_ACTIVE", "PREFS_NAME", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}
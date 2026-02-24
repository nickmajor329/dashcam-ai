package com.dashcam.ai.settings;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J>\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u000fJ\u0006\u0010\u0013\u001a\u00020\u0014R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/dashcam/ai/settings/AppSettingsManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "prefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "save", "", "defaultLensFacing", "", "segmentDurationSeconds", "maxStorageGb", "idleDetectionEnabled", "", "idleThresholdMinutes", "defaultDimScreen", "defaultBlackScreen", "snapshot", "Lcom/dashcam/ai/settings/AppSettings;", "Companion", "app_debug"})
public final class AppSettingsManager {
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFS_NAME = "app_settings";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_DEFAULT_LENS = "default_lens";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_SEGMENT_SECONDS = "segment_seconds";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_MAX_STORAGE_GB = "max_storage_gb";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_IDLE_ENABLED = "idle_enabled";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_IDLE_MINUTES = "idle_minutes";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_DEFAULT_DIM = "default_dim";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_DEFAULT_BLACK = "default_black";
    @org.jetbrains.annotations.NotNull()
    public static final com.dashcam.ai.settings.AppSettingsManager.Companion Companion = null;
    
    public AppSettingsManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.dashcam.ai.settings.AppSettings snapshot() {
        return null;
    }
    
    public final void save(int defaultLensFacing, int segmentDurationSeconds, int maxStorageGb, boolean idleDetectionEnabled, int idleThresholdMinutes, boolean defaultDimScreen, boolean defaultBlackScreen) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/dashcam/ai/settings/AppSettingsManager$Companion;", "", "()V", "KEY_DEFAULT_BLACK", "", "KEY_DEFAULT_DIM", "KEY_DEFAULT_LENS", "KEY_IDLE_ENABLED", "KEY_IDLE_MINUTES", "KEY_MAX_STORAGE_GB", "KEY_SEGMENT_SECONDS", "PREFS_NAME", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}
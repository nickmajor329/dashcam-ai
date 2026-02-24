package com.dashcam.ai.privacy;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \r2\u00020\u0001:\u0001\rB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u0006\u0010\f\u001a\u00020\u000bR\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/dashcam/ai/privacy/PrivacySettingsManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "prefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "save", "", "settings", "Lcom/dashcam/ai/privacy/PrivacySettings;", "snapshot", "Companion", "app_debug"})
public final class PrivacySettingsManager {
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFS_NAME = "privacy_settings";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_BLUR_FACES = "blur_faces";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_BLUR_PLATES = "blur_plates";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_ENCRYPT_EVENT_CLIPS = "encrypt_event_clips";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_RETENTION_LOW_DAYS = "retention_low_days";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_RETENTION_MEDIUM_DAYS = "retention_medium_days";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_RETENTION_HIGH_DAYS = "retention_high_days";
    @org.jetbrains.annotations.NotNull()
    public static final com.dashcam.ai.privacy.PrivacySettingsManager.Companion Companion = null;
    
    public PrivacySettingsManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.dashcam.ai.privacy.PrivacySettings snapshot() {
        return null;
    }
    
    public final void save(@org.jetbrains.annotations.NotNull()
    com.dashcam.ai.privacy.PrivacySettings settings) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/dashcam/ai/privacy/PrivacySettingsManager$Companion;", "", "()V", "KEY_BLUR_FACES", "", "KEY_BLUR_PLATES", "KEY_ENCRYPT_EVENT_CLIPS", "KEY_RETENTION_HIGH_DAYS", "KEY_RETENTION_LOW_DAYS", "KEY_RETENTION_MEDIUM_DAYS", "PREFS_NAME", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}
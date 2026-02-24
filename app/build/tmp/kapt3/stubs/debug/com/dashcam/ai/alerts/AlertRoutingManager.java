package com.dashcam.ai.alerts;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 #2\u00020\u0001:\u0001#B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J \u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u000bH\u0002J\u0018\u0010\u0010\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH\u0002J\u0018\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH\u0002J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\rJ\u000e\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u000bJ\u000e\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\rJ\u000e\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u000bJ\u000e\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u001c\u001a\u00020\rJ\u000e\u0010\u001d\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u000bJ\u0006\u0010\u001e\u001a\u00020\u001fJ\u0018\u0010 \u001a\u00020\u00142\u0006\u0010\f\u001a\u00020\r2\u0006\u0010!\u001a\u00020\u000bH\u0002J\u0018\u0010\"\u001a\u00020\u00142\u0006\u0010\f\u001a\u00020\r2\u0006\u0010!\u001a\u00020\rH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n \t*\u0004\u0018\u00010\b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006$"}, d2 = {"Lcom/dashcam/ai/alerts/AlertRoutingManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "pairingManager", "Lcom/dashcam/ai/pairing/PairingManager;", "prefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "readBoolean", "", "baseKey", "", "vehicleId", "fallback", "readString", "scopedKey", "base", "setAppDeviceId", "", "deviceId", "setAppEnabled", "enabled", "setEmailAddress", "email", "setEmailEnabled", "setPhoneNumber", "phone", "setSmsEnabled", "snapshot", "Lcom/dashcam/ai/alerts/AlertRoutingConfig;", "writeBoolean", "value", "writeString", "Companion", "app_debug"})
public final class AlertRoutingManager {
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private final com.dashcam.ai.pairing.PairingManager pairingManager = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFS_NAME = "alert_routing";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_APP_ENABLED = "app_enabled";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_EMAIL_ENABLED = "email_enabled";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_SMS_ENABLED = "sms_enabled";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_APP_DEVICE_ID = "app_device_id";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_EMAIL_ADDRESS = "email_address";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_PHONE_NUMBER = "phone_number";
    @org.jetbrains.annotations.NotNull()
    public static final com.dashcam.ai.alerts.AlertRoutingManager.Companion Companion = null;
    
    public AlertRoutingManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.dashcam.ai.alerts.AlertRoutingConfig snapshot() {
        return null;
    }
    
    public final void setAppEnabled(boolean enabled) {
    }
    
    public final void setEmailEnabled(boolean enabled) {
    }
    
    public final void setSmsEnabled(boolean enabled) {
    }
    
    public final void setAppDeviceId(@org.jetbrains.annotations.NotNull()
    java.lang.String deviceId) {
    }
    
    public final void setEmailAddress(@org.jetbrains.annotations.NotNull()
    java.lang.String email) {
    }
    
    public final void setPhoneNumber(@org.jetbrains.annotations.NotNull()
    java.lang.String phone) {
    }
    
    private final boolean readBoolean(java.lang.String baseKey, java.lang.String vehicleId, boolean fallback) {
        return false;
    }
    
    private final java.lang.String readString(java.lang.String baseKey, java.lang.String vehicleId) {
        return null;
    }
    
    private final void writeBoolean(java.lang.String baseKey, boolean value) {
    }
    
    private final void writeString(java.lang.String baseKey, java.lang.String value) {
    }
    
    private final java.lang.String scopedKey(java.lang.String base, java.lang.String vehicleId) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/dashcam/ai/alerts/AlertRoutingManager$Companion;", "", "()V", "KEY_APP_DEVICE_ID", "", "KEY_APP_ENABLED", "KEY_EMAIL_ADDRESS", "KEY_EMAIL_ENABLED", "KEY_PHONE_NUMBER", "KEY_SMS_ENABLED", "PREFS_NAME", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}
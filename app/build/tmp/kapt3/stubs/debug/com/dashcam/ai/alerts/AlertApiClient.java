package com.dashcam.ai.alerts;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 $2\u00020\u0001:\u0001$B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0002Jv\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\nJ(\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020\b2\u0006\u0010 \u001a\u00020!H\u0002J(\u0010\"\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020\b2\u0006\u0010#\u001a\u00020\bH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/dashcam/ai/alerts/AlertApiClient;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "authSessionManager", "Lcom/dashcam/ai/auth/AuthSessionManager;", "sourceDeviceId", "", "uploadEvent", "", "eventId", "", "eventType", "clipPath", "createdAtMs", "ownerId", "vehicleId", "appEnabled", "emailEnabled", "smsEnabled", "appDeviceId", "emailAddress", "phoneNumber", "blurFaces", "blurPlates", "writeFileField", "", "stream", "Ljava/io/BufferedOutputStream;", "boundary", "name", "file", "Ljava/io/File;", "writeFormField", "value", "Companion", "app_debug"})
public final class AlertApiClient {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.dashcam.ai.auth.AuthSessionManager authSessionManager = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "AlertApiClient";
    @org.jetbrains.annotations.NotNull()
    public static final com.dashcam.ai.alerts.AlertApiClient.Companion Companion = null;
    
    public AlertApiClient(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    public final boolean uploadEvent(long eventId, @org.jetbrains.annotations.NotNull()
    java.lang.String eventType, @org.jetbrains.annotations.NotNull()
    java.lang.String clipPath, long createdAtMs, @org.jetbrains.annotations.NotNull()
    java.lang.String ownerId, @org.jetbrains.annotations.NotNull()
    java.lang.String vehicleId, boolean appEnabled, boolean emailEnabled, boolean smsEnabled, @org.jetbrains.annotations.NotNull()
    java.lang.String appDeviceId, @org.jetbrains.annotations.NotNull()
    java.lang.String emailAddress, @org.jetbrains.annotations.NotNull()
    java.lang.String phoneNumber, boolean blurFaces, boolean blurPlates) {
        return false;
    }
    
    private final java.lang.String sourceDeviceId() {
        return null;
    }
    
    private final void writeFormField(java.io.BufferedOutputStream stream, java.lang.String boundary, java.lang.String name, java.lang.String value) {
    }
    
    private final void writeFileField(java.io.BufferedOutputStream stream, java.lang.String boundary, java.lang.String name, java.io.File file) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/dashcam/ai/alerts/AlertApiClient$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}
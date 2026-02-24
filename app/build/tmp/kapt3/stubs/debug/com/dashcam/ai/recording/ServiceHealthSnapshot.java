package com.dashcam.ai.recording;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u001d\b\u0086\b\u0018\u00002\u00020\u0001BA\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\rJ\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0005H\u00c6\u0003J\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u000fJ\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u000fJ\t\u0010\u001f\u001a\u00020\u0007H\u00c6\u0003J\t\u0010 \u001a\u00020\u000bH\u00c6\u0003J\t\u0010!\u001a\u00020\u0005H\u00c6\u0003JX\u0010\"\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\t\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u0005H\u00c6\u0001\u00a2\u0006\u0002\u0010#J\u0013\u0010$\u001a\u00020\u00032\b\u0010%\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010&\u001a\u00020\u0007H\u00d6\u0001J\t\u0010\'\u001a\u00020\u000bH\u00d6\u0001R\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\n\n\u0002\u0010\u0010\u001a\u0004\b\u000e\u0010\u000fR\u0015\u0010\b\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\n\n\u0002\u0010\u0010\u001a\u0004\b\u0011\u0010\u000fR\u0011\u0010\t\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\f\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006("}, d2 = {"Lcom/dashcam/ai/recording/ServiceHealthSnapshot;", "", "recordingActive", "", "freeBytes", "", "batteryPct", "", "batteryTempTenthsC", "cameraFailureCount", "lastHealthMessage", "", "lastUpdatedMs", "(ZJLjava/lang/Integer;Ljava/lang/Integer;ILjava/lang/String;J)V", "getBatteryPct", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getBatteryTempTenthsC", "getCameraFailureCount", "()I", "getFreeBytes", "()J", "getLastHealthMessage", "()Ljava/lang/String;", "getLastUpdatedMs", "getRecordingActive", "()Z", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "(ZJLjava/lang/Integer;Ljava/lang/Integer;ILjava/lang/String;J)Lcom/dashcam/ai/recording/ServiceHealthSnapshot;", "equals", "other", "hashCode", "toString", "app_debug"})
public final class ServiceHealthSnapshot {
    private final boolean recordingActive = false;
    private final long freeBytes = 0L;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer batteryPct = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer batteryTempTenthsC = null;
    private final int cameraFailureCount = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String lastHealthMessage = null;
    private final long lastUpdatedMs = 0L;
    
    public ServiceHealthSnapshot(boolean recordingActive, long freeBytes, @org.jetbrains.annotations.Nullable()
    java.lang.Integer batteryPct, @org.jetbrains.annotations.Nullable()
    java.lang.Integer batteryTempTenthsC, int cameraFailureCount, @org.jetbrains.annotations.NotNull()
    java.lang.String lastHealthMessage, long lastUpdatedMs) {
        super();
    }
    
    public final boolean getRecordingActive() {
        return false;
    }
    
    public final long getFreeBytes() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getBatteryPct() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getBatteryTempTenthsC() {
        return null;
    }
    
    public final int getCameraFailureCount() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLastHealthMessage() {
        return null;
    }
    
    public final long getLastUpdatedMs() {
        return 0L;
    }
    
    public final boolean component1() {
        return false;
    }
    
    public final long component2() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component4() {
        return null;
    }
    
    public final int component5() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    public final long component7() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.dashcam.ai.recording.ServiceHealthSnapshot copy(boolean recordingActive, long freeBytes, @org.jetbrains.annotations.Nullable()
    java.lang.Integer batteryPct, @org.jetbrains.annotations.Nullable()
    java.lang.Integer batteryTempTenthsC, int cameraFailureCount, @org.jetbrains.annotations.NotNull()
    java.lang.String lastHealthMessage, long lastUpdatedMs) {
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
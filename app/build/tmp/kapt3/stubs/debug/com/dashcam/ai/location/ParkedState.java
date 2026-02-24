package com.dashcam.ai.location;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0017\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\u0010\u0010\u001c\u001a\u0004\u0018\u00010\tH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003JR\u0010\u001e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000bH\u00c6\u0001\u00a2\u0006\u0002\u0010\u001fJ\u0013\u0010 \u001a\u00020\u00032\b\u0010!\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\"\u001a\u00020#H\u00d6\u0001J\t\u0010$\u001a\u00020\u000bH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\rR\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0015\u0010\b\u001a\u0004\u0018\u00010\t\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0011\u0010\u0012R\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u0014\u0010\u0015R\u0015\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u0017\u0010\u0015\u00a8\u0006%"}, d2 = {"Lcom/dashcam/ai/location/ParkedState;", "", "isParked", "", "ownerAwayEnabled", "parkedLat", "", "parkedLon", "parkedAtMs", "", "knownCarDeviceAddress", "", "(ZZLjava/lang/Double;Ljava/lang/Double;Ljava/lang/Long;Ljava/lang/String;)V", "()Z", "getKnownCarDeviceAddress", "()Ljava/lang/String;", "getOwnerAwayEnabled", "getParkedAtMs", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getParkedLat", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getParkedLon", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "(ZZLjava/lang/Double;Ljava/lang/Double;Ljava/lang/Long;Ljava/lang/String;)Lcom/dashcam/ai/location/ParkedState;", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class ParkedState {
    private final boolean isParked = false;
    private final boolean ownerAwayEnabled = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double parkedLat = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double parkedLon = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long parkedAtMs = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String knownCarDeviceAddress = null;
    
    public ParkedState(boolean isParked, boolean ownerAwayEnabled, @org.jetbrains.annotations.Nullable()
    java.lang.Double parkedLat, @org.jetbrains.annotations.Nullable()
    java.lang.Double parkedLon, @org.jetbrains.annotations.Nullable()
    java.lang.Long parkedAtMs, @org.jetbrains.annotations.Nullable()
    java.lang.String knownCarDeviceAddress) {
        super();
    }
    
    public final boolean isParked() {
        return false;
    }
    
    public final boolean getOwnerAwayEnabled() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getParkedLat() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getParkedLon() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getParkedAtMs() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getKnownCarDeviceAddress() {
        return null;
    }
    
    public final boolean component1() {
        return false;
    }
    
    public final boolean component2() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.dashcam.ai.location.ParkedState copy(boolean isParked, boolean ownerAwayEnabled, @org.jetbrains.annotations.Nullable()
    java.lang.Double parkedLat, @org.jetbrains.annotations.Nullable()
    java.lang.Double parkedLon, @org.jetbrains.annotations.Nullable()
    java.lang.Long parkedAtMs, @org.jetbrains.annotations.Nullable()
    java.lang.String knownCarDeviceAddress) {
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
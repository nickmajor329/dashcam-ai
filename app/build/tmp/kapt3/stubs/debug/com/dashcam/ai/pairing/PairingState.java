package com.dashcam.ai.pairing;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u00c6\u0003JK\u0010\u001c\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u00c6\u0001J\u0013\u0010\u001d\u001a\u00020\u00062\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001f\u001a\u00020 H\u00d6\u0001J\t\u0010!\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000e\u00a8\u0006\""}, d2 = {"Lcom/dashcam/ai/pairing/PairingState;", "", "vehicleId", "", "pairedOwnerId", "paired", "", "activeVehicleId", "activeRole", "fleet", "", "Lcom/dashcam/ai/pairing/FleetVehicle;", "(Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;Ljava/lang/String;Ljava/util/List;)V", "getActiveRole", "()Ljava/lang/String;", "getActiveVehicleId", "getFleet", "()Ljava/util/List;", "getPaired", "()Z", "getPairedOwnerId", "getVehicleId", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class PairingState {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String vehicleId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String pairedOwnerId = null;
    private final boolean paired = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String activeVehicleId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String activeRole = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.dashcam.ai.pairing.FleetVehicle> fleet = null;
    
    public PairingState(@org.jetbrains.annotations.NotNull()
    java.lang.String vehicleId, @org.jetbrains.annotations.NotNull()
    java.lang.String pairedOwnerId, boolean paired, @org.jetbrains.annotations.NotNull()
    java.lang.String activeVehicleId, @org.jetbrains.annotations.NotNull()
    java.lang.String activeRole, @org.jetbrains.annotations.NotNull()
    java.util.List<com.dashcam.ai.pairing.FleetVehicle> fleet) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getVehicleId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPairedOwnerId() {
        return null;
    }
    
    public final boolean getPaired() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getActiveVehicleId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getActiveRole() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.dashcam.ai.pairing.FleetVehicle> getFleet() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    public final boolean component3() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.dashcam.ai.pairing.FleetVehicle> component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.dashcam.ai.pairing.PairingState copy(@org.jetbrains.annotations.NotNull()
    java.lang.String vehicleId, @org.jetbrains.annotations.NotNull()
    java.lang.String pairedOwnerId, boolean paired, @org.jetbrains.annotations.NotNull()
    java.lang.String activeVehicleId, @org.jetbrains.annotations.NotNull()
    java.lang.String activeRole, @org.jetbrains.annotations.NotNull()
    java.util.List<com.dashcam.ai.pairing.FleetVehicle> fleet) {
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
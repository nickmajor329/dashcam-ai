package com.dashcam.ai.pairing;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bJ\u0006\u0010\r\u001a\u00020\tJ\u0006\u0010\u000e\u001a\u00020\tJ\u0016\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0012\u001a\u00020\u000bH\u0002J\u0016\u0010\u0013\u001a\u00020\u000b2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0002J\u000e\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\u000bJ\u000e\u0010\u0017\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\u000bJ\u000e\u0010\u0019\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u0006\u0010\u001a\u001a\u00020\u001bR\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lcom/dashcam/ai/pairing/PairingManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "prefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "addOrUpdateFleetVehicle", "", "vehicleId", "", "role", "clearOwner", "clearPairing", "decodeFleet", "", "Lcom/dashcam/ai/pairing/FleetVehicle;", "raw", "encodeFleet", "fleet", "markPaired", "ownerId", "setActiveVehicle", "setOwnerId", "setVehicleId", "snapshot", "Lcom/dashcam/ai/pairing/PairingState;", "Companion", "app_debug"})
public final class PairingManager {
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFS_NAME = "pairing_state";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_VEHICLE_ID = "vehicle_id";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_OWNER_ID = "owner_id";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_PAIRED = "paired";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_ACTIVE_VEHICLE_ID = "active_vehicle_id";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_FLEET_JSON = "fleet_json";
    @org.jetbrains.annotations.NotNull()
    public static final com.dashcam.ai.pairing.PairingManager.Companion Companion = null;
    
    public PairingManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.dashcam.ai.pairing.PairingState snapshot() {
        return null;
    }
    
    public final void setVehicleId(@org.jetbrains.annotations.NotNull()
    java.lang.String vehicleId) {
    }
    
    public final void markPaired(@org.jetbrains.annotations.NotNull()
    java.lang.String ownerId) {
    }
    
    public final void setOwnerId(@org.jetbrains.annotations.NotNull()
    java.lang.String ownerId) {
    }
    
    public final void clearOwner() {
    }
    
    public final void addOrUpdateFleetVehicle(@org.jetbrains.annotations.NotNull()
    java.lang.String vehicleId, @org.jetbrains.annotations.NotNull()
    java.lang.String role) {
    }
    
    public final void setActiveVehicle(@org.jetbrains.annotations.NotNull()
    java.lang.String vehicleId) {
    }
    
    public final void clearPairing() {
    }
    
    private final java.util.List<com.dashcam.ai.pairing.FleetVehicle> decodeFleet(java.lang.String raw) {
        return null;
    }
    
    private final java.lang.String encodeFleet(java.util.List<com.dashcam.ai.pairing.FleetVehicle> fleet) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/dashcam/ai/pairing/PairingManager$Companion;", "", "()V", "KEY_ACTIVE_VEHICLE_ID", "", "KEY_FLEET_JSON", "KEY_OWNER_ID", "KEY_PAIRED", "KEY_VEHICLE_ID", "PREFS_NAME", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}
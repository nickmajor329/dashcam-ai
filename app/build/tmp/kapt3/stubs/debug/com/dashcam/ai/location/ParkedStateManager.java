package com.dashcam.ai.location;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\b\u001a\u00020\tJ)\u0010\n\u001a\u00020\t2\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\u0010\r\u001a\u0004\u0018\u00010\f2\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\u0002\u0010\u0010J\u0010\u0010\u0011\u001a\u00020\t2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013J\u000e\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u0016J\u0006\u0010\u0017\u001a\u00020\u0018R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/dashcam/ai/location/ParkedStateManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "prefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "markDriving", "", "markParked", "lat", "", "lon", "parkedAtMs", "", "(Ljava/lang/Double;Ljava/lang/Double;J)V", "setKnownCarDeviceAddress", "address", "", "setOwnerAwayEnabled", "enabled", "", "snapshot", "Lcom/dashcam/ai/location/ParkedState;", "Companion", "app_debug"})
public final class ParkedStateManager {
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREF_NAME = "parked_state";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_IS_PARKED = "is_parked";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_AWAY_ENABLED = "away_enabled";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_PARKED_AT_MS = "parked_at_ms";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_PARKED_LAT_BITS = "parked_lat_bits";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_PARKED_LON_BITS = "parked_lon_bits";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_HAS_COORDS = "has_coords";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_KNOWN_CAR_DEVICE = "known_car_device";
    @org.jetbrains.annotations.NotNull()
    public static final com.dashcam.ai.location.ParkedStateManager.Companion Companion = null;
    
    public ParkedStateManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    public final void markParked(@org.jetbrains.annotations.Nullable()
    java.lang.Double lat, @org.jetbrains.annotations.Nullable()
    java.lang.Double lon, long parkedAtMs) {
    }
    
    public final void markDriving() {
    }
    
    public final void setOwnerAwayEnabled(boolean enabled) {
    }
    
    public final void setKnownCarDeviceAddress(@org.jetbrains.annotations.Nullable()
    java.lang.String address) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.dashcam.ai.location.ParkedState snapshot() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/dashcam/ai/location/ParkedStateManager$Companion;", "", "()V", "KEY_AWAY_ENABLED", "", "KEY_HAS_COORDS", "KEY_IS_PARKED", "KEY_KNOWN_CAR_DEVICE", "KEY_PARKED_AT_MS", "KEY_PARKED_LAT_BITS", "KEY_PARKED_LON_BITS", "PREF_NAME", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}
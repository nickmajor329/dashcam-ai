package com.dashcam.ai.location;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\b\u0010\n\u001a\u0004\u0018\u00010\u000bJ\u0016\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/dashcam/ai/location/AwayStateManager;", "", "distanceThresholdMeters", "", "(F)V", "isOwnerAway", "", "parkedLat", "", "parkedLon", "ownerLocation", "Landroid/location/Location;", "isValidParkedLocation", "lat", "lon", "app_debug"})
public final class AwayStateManager {
    private final float distanceThresholdMeters = 0.0F;
    
    public AwayStateManager(float distanceThresholdMeters) {
        super();
    }
    
    public final boolean isOwnerAway(double parkedLat, double parkedLon, @org.jetbrains.annotations.Nullable()
    android.location.Location ownerLocation) {
        return false;
    }
    
    public final boolean isValidParkedLocation(double lat, double lon) {
        return false;
    }
    
    public AwayStateManager() {
        super();
    }
}
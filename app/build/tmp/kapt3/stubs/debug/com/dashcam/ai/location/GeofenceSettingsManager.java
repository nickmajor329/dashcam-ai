package com.dashcam.ai.location;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 $2\u00020\u0001:\u0002$%B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH\u0002J9\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\r2\b\u0010\u0012\u001a\u0004\u0018\u00010\r2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002\u00a2\u0006\u0002\u0010\u0015J\u0017\u0010\u0016\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0017\u001a\u00020\u0018H\u0002\u00a2\u0006\u0002\u0010\u0019J\u000e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\n\u001a\u00020\u000bJ\u001f\u0010\u001c\u001a\u00020\u00102\b\u0010\f\u001a\u0004\u0018\u00010\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\r\u00a2\u0006\u0002\u0010\u001dJ\u0006\u0010\u001e\u001a\u00020\u000bJ\'\u0010\u001f\u001a\u00020\u001b2\u0006\u0010 \u001a\u00020!2\u0006\u0010\u0017\u001a\u00020\u00182\b\u0010\"\u001a\u0004\u0018\u00010\rH\u0002\u00a2\u0006\u0002\u0010#R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2 = {"Lcom/dashcam/ai/location/GeofenceSettingsManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "prefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "classifyZone", "Lcom/dashcam/ai/location/GeofenceSettingsManager$Zone;", "settings", "Lcom/dashcam/ai/location/GeofenceSettings;", "parkedLat", "", "parkedLon", "isWithin", "", "anchorLat", "anchorLon", "radiusMeters", "", "(Ljava/lang/Double;Ljava/lang/Double;DDF)Z", "readOptionalDouble", "key", "", "(Ljava/lang/String;)Ljava/lang/Double;", "save", "", "shouldAllowAlert", "(Ljava/lang/Double;Ljava/lang/Double;)Z", "snapshot", "writeOptionalDouble", "editor", "Landroid/content/SharedPreferences$Editor;", "value", "(Landroid/content/SharedPreferences$Editor;Ljava/lang/String;Ljava/lang/Double;)V", "Companion", "Zone", "app_debug"})
public final class GeofenceSettingsManager {
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFS_NAME = "geofence_settings";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_ENABLED = "enabled";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_MODE = "mode";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_HOME_LAT = "home_lat";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_HOME_LON = "home_lon";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_WORK_LAT = "work_lat";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_WORK_LON = "work_lon";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_RADIUS = "radius";
    @org.jetbrains.annotations.NotNull()
    public static final com.dashcam.ai.location.GeofenceSettingsManager.Companion Companion = null;
    
    public GeofenceSettingsManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.dashcam.ai.location.GeofenceSettings snapshot() {
        return null;
    }
    
    public final void save(@org.jetbrains.annotations.NotNull()
    com.dashcam.ai.location.GeofenceSettings settings) {
    }
    
    public final boolean shouldAllowAlert(@org.jetbrains.annotations.Nullable()
    java.lang.Double parkedLat, @org.jetbrains.annotations.Nullable()
    java.lang.Double parkedLon) {
        return false;
    }
    
    private final com.dashcam.ai.location.GeofenceSettingsManager.Zone classifyZone(com.dashcam.ai.location.GeofenceSettings settings, double parkedLat, double parkedLon) {
        return null;
    }
    
    private final boolean isWithin(java.lang.Double anchorLat, java.lang.Double anchorLon, double parkedLat, double parkedLon, float radiusMeters) {
        return false;
    }
    
    private final java.lang.Double readOptionalDouble(java.lang.String key) {
        return null;
    }
    
    private final void writeOptionalDouble(android.content.SharedPreferences.Editor editor, java.lang.String key, java.lang.Double value) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/dashcam/ai/location/GeofenceSettingsManager$Companion;", "", "()V", "KEY_ENABLED", "", "KEY_HOME_LAT", "KEY_HOME_LON", "KEY_MODE", "KEY_RADIUS", "KEY_WORK_LAT", "KEY_WORK_LON", "PREFS_NAME", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/dashcam/ai/location/GeofenceSettingsManager$Zone;", "", "(Ljava/lang/String;I)V", "HOME", "WORK", "PUBLIC", "app_debug"})
    static enum Zone {
        /*public static final*/ HOME /* = new HOME() */,
        /*public static final*/ WORK /* = new WORK() */,
        /*public static final*/ PUBLIC /* = new PUBLIC() */;
        
        Zone() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public static kotlin.enums.EnumEntries<com.dashcam.ai.location.GeofenceSettingsManager.Zone> getEntries() {
            return null;
        }
    }
}
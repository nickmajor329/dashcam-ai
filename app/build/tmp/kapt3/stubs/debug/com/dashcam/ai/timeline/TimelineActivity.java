package com.dashcam.ai.timeline;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010 \n\u0002\b\u0005\u0018\u0000 =2\u00020\u0001:\u0003=>?B\u0005\u00a2\u0006\u0002\u0010\u0002J\n\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J \u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001bH\u0002J\u0018\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001f\u001a\u00020\u001bH\u0002J\b\u0010 \u001a\u00020!H\u0002J\b\u0010\"\u001a\u00020#H\u0002J\u0010\u0010$\u001a\u00020%2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0010\u0010&\u001a\u00020#2\u0006\u0010\'\u001a\u00020\u001bH\u0002J\u0010\u0010(\u001a\u00020#2\u0006\u0010\'\u001a\u00020\u001bH\u0002J\u0010\u0010)\u001a\u00020#2\u0006\u0010\'\u001a\u00020\u001bH\u0002J\u0010\u0010*\u001a\u00020#2\u0006\u0010+\u001a\u00020#H\u0002J\u0010\u0010,\u001a\u00020#2\u0006\u0010-\u001a\u00020\u0016H\u0002J\b\u0010.\u001a\u00020%H\u0002J\u0012\u0010/\u001a\u00020%2\b\u00100\u001a\u0004\u0018\u000101H\u0014J\b\u00102\u001a\u00020%H\u0002J\u0010\u00103\u001a\u00020#2\u0006\u00104\u001a\u00020\u001bH\u0002J\u0010\u00105\u001a\u00020%2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0018\u00106\u001a\u00020%2\u0006\u00107\u001a\u00020\u001b2\u0006\u00108\u001a\u00020#H\u0002J\u001e\u00109\u001a\u00020%2\f\u0010:\u001a\b\u0012\u0004\u0012\u00020\u001b0;2\u0006\u0010<\u001a\u00020\u001bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006@"}, d2 = {"Lcom/dashcam/ai/timeline/TimelineActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Lcom/dashcam/ai/timeline/EventTimelineAdapter;", "binding", "Lcom/dashcam/ai/databinding/ActivityTimelineBinding;", "pairingManager", "Lcom/dashcam/ai/pairing/PairingManager;", "parkedStateManager", "Lcom/dashcam/ai/location/ParkedStateManager;", "pendingOnly", "", "repository", "Lcom/dashcam/ai/data/EventRepository;", "selectedType", "Lcom/dashcam/ai/data/EventType;", "bestLastKnownGps", "Lcom/dashcam/ai/timeline/TimelineActivity$GpsPoint;", "buildGpsGeoJson", "Lorg/json/JSONObject;", "timestampMs", "", "buildMetadataJson", "event", "Lcom/dashcam/ai/data/EventEntity;", "originalClip", "Ljava/io/File;", "packagedClip", "createEvidenceBundle", "Lcom/dashcam/ai/timeline/TimelineActivity$EvidenceExportResult;", "clip", "evidenceKeyBytes", "", "evidenceKeyId", "", "exportEvidencePackage", "", "exportZipForUser", "source", "exportZipToAppExternal", "exportZipToMediaStore", "hmacSha256Base64", "payload", "iso", "ms", "loadTimeline", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "setupFilters", "sha256Hex", "file", "shareClip", "shareEvidenceBundle", "bundleFile", "exportName", "zipFiles", "files", "", "target", "Companion", "EvidenceExportResult", "GpsPoint", "app_debug"})
public final class TimelineActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.dashcam.ai.databinding.ActivityTimelineBinding binding;
    private com.dashcam.ai.data.EventRepository repository;
    private com.dashcam.ai.timeline.EventTimelineAdapter adapter;
    private com.dashcam.ai.location.ParkedStateManager parkedStateManager;
    private com.dashcam.ai.pairing.PairingManager pairingManager;
    @org.jetbrains.annotations.Nullable()
    private com.dashcam.ai.data.EventType selectedType;
    private boolean pendingOnly = false;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String EVIDENCE_PREFS = "evidence_signing";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_EVIDENCE_SECRET_B64 = "secret_b64";
    @org.jetbrains.annotations.NotNull()
    public static final com.dashcam.ai.timeline.TimelineActivity.Companion Companion = null;
    
    public TimelineActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupFilters() {
    }
    
    private final void loadTimeline() {
    }
    
    private final void shareClip(com.dashcam.ai.data.EventEntity event) {
    }
    
    private final void exportEvidencePackage(com.dashcam.ai.data.EventEntity event) {
    }
    
    private final void shareEvidenceBundle(java.io.File bundleFile, java.lang.String exportName) {
    }
    
    private final com.dashcam.ai.timeline.TimelineActivity.EvidenceExportResult createEvidenceBundle(com.dashcam.ai.data.EventEntity event, java.io.File clip) {
        return null;
    }
    
    private final java.lang.String exportZipForUser(java.io.File source) {
        return null;
    }
    
    private final java.lang.String exportZipToMediaStore(java.io.File source) {
        return null;
    }
    
    private final java.lang.String exportZipToAppExternal(java.io.File source) {
        return null;
    }
    
    private final org.json.JSONObject buildMetadataJson(com.dashcam.ai.data.EventEntity event, java.io.File originalClip, java.io.File packagedClip) {
        return null;
    }
    
    private final org.json.JSONObject buildGpsGeoJson(long timestampMs) {
        return null;
    }
    
    private final com.dashcam.ai.timeline.TimelineActivity.GpsPoint bestLastKnownGps() {
        return null;
    }
    
    private final void zipFiles(java.util.List<? extends java.io.File> files, java.io.File target) {
    }
    
    private final java.lang.String sha256Hex(java.io.File file) {
        return null;
    }
    
    private final java.lang.String hmacSha256Base64(java.lang.String payload) {
        return null;
    }
    
    private final byte[] evidenceKeyBytes() {
        return null;
    }
    
    private final java.lang.String evidenceKeyId() {
        return null;
    }
    
    private final java.lang.String iso(long ms) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/dashcam/ai/timeline/TimelineActivity$Companion;", "", "()V", "EVIDENCE_PREFS", "", "KEY_EVIDENCE_SECRET_B64", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0014"}, d2 = {"Lcom/dashcam/ai/timeline/TimelineActivity$EvidenceExportResult;", "", "shareFile", "Ljava/io/File;", "exportName", "", "(Ljava/io/File;Ljava/lang/String;)V", "getExportName", "()Ljava/lang/String;", "getShareFile", "()Ljava/io/File;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    static final class EvidenceExportResult {
        @org.jetbrains.annotations.NotNull()
        private final java.io.File shareFile = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String exportName = null;
        
        public EvidenceExportResult(@org.jetbrains.annotations.NotNull()
        java.io.File shareFile, @org.jetbrains.annotations.NotNull()
        java.lang.String exportName) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.io.File getShareFile() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getExportName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.io.File component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.dashcam.ai.timeline.TimelineActivity.EvidenceExportResult copy(@org.jetbrains.annotations.NotNull()
        java.io.File shareFile, @org.jetbrains.annotations.NotNull()
        java.lang.String exportName) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000f\u001a\u00020\u0006H\u00c6\u0003J\'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u00c6\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0018"}, d2 = {"Lcom/dashcam/ai/timeline/TimelineActivity$GpsPoint;", "", "lat", "", "lon", "timestampMs", "", "(DDJ)V", "getLat", "()D", "getLon", "getTimestampMs", "()J", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
    static final class GpsPoint {
        private final double lat = 0.0;
        private final double lon = 0.0;
        private final long timestampMs = 0L;
        
        public GpsPoint(double lat, double lon, long timestampMs) {
            super();
        }
        
        public final double getLat() {
            return 0.0;
        }
        
        public final double getLon() {
            return 0.0;
        }
        
        public final long getTimestampMs() {
            return 0L;
        }
        
        public final double component1() {
            return 0.0;
        }
        
        public final double component2() {
            return 0.0;
        }
        
        public final long component3() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.dashcam.ai.timeline.TimelineActivity.GpsPoint copy(double lat, double lon, long timestampMs) {
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
}
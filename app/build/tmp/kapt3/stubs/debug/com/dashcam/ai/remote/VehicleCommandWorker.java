package com.dashcam.ai.remote;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001%B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000f\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0002\u00a2\u0006\u0002\u0010\u000fJ\u000e\u0010\u0010\u001a\u00020\u0011H\u0096@\u00a2\u0006\u0002\u0010\u0012J \u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0016H\u0002J\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u001d\u001a\u00020\u0014H\u0002J\u0018\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0019\u001a\u00020\u00162\u0006\u0010 \u001a\u00020\u0016H\u0002J\b\u0010!\u001a\u00020\u001bH\u0002J\u0010\u0010\"\u001a\u00020\u001f2\u0006\u0010#\u001a\u00020\u001fH\u0002J\b\u0010$\u001a\u00020\u0016H\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2 = {"Lcom/dashcam/ai/remote/VehicleCommandWorker;", "Landroidx/work/CoroutineWorker;", "appContext", "Landroid/content/Context;", "params", "Landroidx/work/WorkerParameters;", "(Landroid/content/Context;Landroidx/work/WorkerParameters;)V", "apiClient", "Lcom/dashcam/ai/network/BackendApiClient;", "pairingManager", "Lcom/dashcam/ai/pairing/PairingManager;", "recordingStateManager", "Lcom/dashcam/ai/recording/RecordingStateManager;", "currentBatteryPct", "", "()Ljava/lang/Integer;", "doWork", "Landroidx/work/ListenableWorker$Result;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "executeCommand", "Lcom/dashcam/ai/remote/VehicleCommandWorker$CommandOutcome;", "commandType", "", "payload", "Lorg/json/JSONObject;", "vehicleId", "postCommandResult", "", "commandId", "outcome", "postHealthPing", "", "note", "rebootAppProcess", "setTorch", "enabled", "sourceDeviceId", "CommandOutcome", "app_debug"})
public final class VehicleCommandWorker extends androidx.work.CoroutineWorker {
    @org.jetbrains.annotations.NotNull()
    private final com.dashcam.ai.network.BackendApiClient apiClient = null;
    @org.jetbrains.annotations.NotNull()
    private final com.dashcam.ai.pairing.PairingManager pairingManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.dashcam.ai.recording.RecordingStateManager recordingStateManager = null;
    
    public VehicleCommandWorker(@org.jetbrains.annotations.NotNull()
    android.content.Context appContext, @org.jetbrains.annotations.NotNull()
    androidx.work.WorkerParameters params) {
        super(null, null);
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object doWork(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super androidx.work.ListenableWorker.Result> $completion) {
        return null;
    }
    
    private final com.dashcam.ai.remote.VehicleCommandWorker.CommandOutcome executeCommand(java.lang.String commandType, org.json.JSONObject payload, java.lang.String vehicleId) {
        return null;
    }
    
    private final void postCommandResult(java.lang.String commandId, com.dashcam.ai.remote.VehicleCommandWorker.CommandOutcome outcome) {
    }
    
    private final boolean postHealthPing(java.lang.String vehicleId, java.lang.String note) {
        return false;
    }
    
    private final java.lang.Integer currentBatteryPct() {
        return null;
    }
    
    private final boolean setTorch(boolean enabled) {
        return false;
    }
    
    private final void rebootAppProcess() {
    }
    
    private final java.lang.String sourceDeviceId() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0010\n\u0002\u0010\b\n\u0002\b\u0003\b\u0082\b\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\bH\u00c6\u0003J1\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u00c6\u0001J\u0013\u0010\u0016\u001a\u00020\b2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001J\t\u0010\u001a\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000b\u00a8\u0006\u001c"}, d2 = {"Lcom/dashcam/ai/remote/VehicleCommandWorker$CommandOutcome;", "", "status", "", "message", "resultPayload", "Lorg/json/JSONObject;", "rebootAfterAck", "", "(Ljava/lang/String;Ljava/lang/String;Lorg/json/JSONObject;Z)V", "getMessage", "()Ljava/lang/String;", "getRebootAfterAck", "()Z", "getResultPayload", "()Lorg/json/JSONObject;", "getStatus", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "", "toString", "Companion", "app_debug"})
    static final class CommandOutcome {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String status = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String message = null;
        @org.jetbrains.annotations.NotNull()
        private final org.json.JSONObject resultPayload = null;
        private final boolean rebootAfterAck = false;
        @org.jetbrains.annotations.NotNull()
        public static final com.dashcam.ai.remote.VehicleCommandWorker.CommandOutcome.Companion Companion = null;
        
        public CommandOutcome(@org.jetbrains.annotations.NotNull()
        java.lang.String status, @org.jetbrains.annotations.NotNull()
        java.lang.String message, @org.jetbrains.annotations.NotNull()
        org.json.JSONObject resultPayload, boolean rebootAfterAck) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getStatus() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getMessage() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final org.json.JSONObject getResultPayload() {
            return null;
        }
        
        public final boolean getRebootAfterAck() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final org.json.JSONObject component3() {
            return null;
        }
        
        public final boolean component4() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.dashcam.ai.remote.VehicleCommandWorker.CommandOutcome copy(@org.jetbrains.annotations.NotNull()
        java.lang.String status, @org.jetbrains.annotations.NotNull()
        java.lang.String message, @org.jetbrains.annotations.NotNull()
        org.json.JSONObject resultPayload, boolean rebootAfterAck) {
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
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\"\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000bJ\u000e\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\r"}, d2 = {"Lcom/dashcam/ai/remote/VehicleCommandWorker$CommandOutcome$Companion;", "", "()V", "failed", "Lcom/dashcam/ai/remote/VehicleCommandWorker$CommandOutcome;", "message", "", "ok", "payload", "Lorg/json/JSONObject;", "rebootAfterAck", "", "unsupported", "app_debug"})
        public static final class Companion {
            
            private Companion() {
                super();
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.dashcam.ai.remote.VehicleCommandWorker.CommandOutcome ok(@org.jetbrains.annotations.NotNull()
            java.lang.String message, @org.jetbrains.annotations.NotNull()
            org.json.JSONObject payload, boolean rebootAfterAck) {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.dashcam.ai.remote.VehicleCommandWorker.CommandOutcome failed(@org.jetbrains.annotations.NotNull()
            java.lang.String message) {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.dashcam.ai.remote.VehicleCommandWorker.CommandOutcome unsupported(@org.jetbrains.annotations.NotNull()
            java.lang.String message) {
                return null;
            }
        }
    }
}
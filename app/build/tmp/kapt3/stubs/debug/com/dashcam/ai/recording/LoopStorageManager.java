package com.dashcam.ai.recording;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0011\u001a\u00020\u000bJ\u0006\u0010\u0012\u001a\u00020\u000bJ&\u0010\u0013\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0014\u001a\u00020\b2\b\b\u0002\u0010\u0015\u001a\u00020\u00162\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0018J\u000e\u0010\u0019\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\u0005R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/dashcam/ai/recording/LoopStorageManager;", "", "context", "Landroid/content/Context;", "maxStorageBytes", "", "(Landroid/content/Context;J)V", "lockedRoot", "Ljava/io/File;", "storageRoot", "enforceEventRetention", "", "events", "", "Lcom/dashcam/ai/data/EventEntity;", "settings", "Lcom/dashcam/ai/privacy/PrivacySettings;", "enforceRetention", "ensureStorageRoot", "lockClip", "source", "encrypt", "", "cryptoManager", "Lcom/dashcam/ai/privacy/ClipCryptoManager;", "reserveNewClipFile", "epochMs", "app_debug"})
public final class LoopStorageManager {
    private final long maxStorageBytes = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.io.File storageRoot = null;
    @org.jetbrains.annotations.NotNull()
    private final java.io.File lockedRoot = null;
    
    public LoopStorageManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context, long maxStorageBytes) {
        super();
    }
    
    public final void ensureStorageRoot() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.io.File reserveNewClipFile(long epochMs) {
        return null;
    }
    
    public final void enforceRetention() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.io.File lockClip(@org.jetbrains.annotations.NotNull()
    java.io.File source, boolean encrypt, @org.jetbrains.annotations.Nullable()
    com.dashcam.ai.privacy.ClipCryptoManager cryptoManager) {
        return null;
    }
    
    public final void enforceEventRetention(@org.jetbrains.annotations.NotNull()
    java.util.List<com.dashcam.ai.data.EventEntity> events, @org.jetbrains.annotations.NotNull()
    com.dashcam.ai.privacy.PrivacySettings settings) {
    }
}
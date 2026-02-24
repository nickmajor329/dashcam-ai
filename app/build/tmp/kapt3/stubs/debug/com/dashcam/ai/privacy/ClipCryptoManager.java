package com.dashcam.ai.privacy;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006J\u0016\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006J\b\u0010\t\u001a\u00020\nH\u0002\u00a8\u0006\f"}, d2 = {"Lcom/dashcam/ai/privacy/ClipCryptoManager;", "", "()V", "decryptFile", "", "input", "Ljava/io/File;", "output", "encryptFile", "getOrCreateKey", "Ljavax/crypto/SecretKey;", "Companion", "app_debug"})
public final class ClipCryptoManager {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String ANDROID_KEYSTORE = "AndroidKeyStore";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_ALIAS = "dashcam_clip_key_v1";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int TAG_BITS = 128;
    @org.jetbrains.annotations.NotNull()
    public static final com.dashcam.ai.privacy.ClipCryptoManager.Companion Companion = null;
    
    public ClipCryptoManager() {
        super();
    }
    
    public final boolean encryptFile(@org.jetbrains.annotations.NotNull()
    java.io.File input, @org.jetbrains.annotations.NotNull()
    java.io.File output) {
        return false;
    }
    
    public final boolean decryptFile(@org.jetbrains.annotations.NotNull()
    java.io.File input, @org.jetbrains.annotations.NotNull()
    java.io.File output) {
        return false;
    }
    
    private final javax.crypto.SecretKey getOrCreateKey() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/dashcam/ai/privacy/ClipCryptoManager$Companion;", "", "()V", "ANDROID_KEYSTORE", "", "KEY_ALIAS", "TAG_BITS", "", "TRANSFORMATION", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}
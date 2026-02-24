package com.dashcam.ai.pairing;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0014H\u0002J\b\u0010\u0018\u001a\u00020\u0012H\u0002J\u0010\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u0014H\u0002J\u0012\u0010\u001b\u001a\u00020\u00122\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0014J\b\u0010\u001e\u001a\u00020\u0012H\u0002J\b\u0010\u001f\u001a\u00020\u0012H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u0010\u0012\f\u0012\n \u000e*\u0004\u0018\u00010\r0\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/dashcam/ai/pairing/PairingActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "apiClient", "Lcom/dashcam/ai/network/BackendApiClient;", "authSessionManager", "Lcom/dashcam/ai/auth/AuthSessionManager;", "binding", "Lcom/dashcam/ai/databinding/ActivityPairingBinding;", "pairingManager", "Lcom/dashcam/ai/pairing/PairingManager;", "scanLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "kotlin.jvm.PlatformType", "scope", "Lkotlinx/coroutines/CoroutineScope;", "completePairing", "", "qrPayload", "", "createQrBitmap", "Landroid/graphics/Bitmap;", "content", "generatePairQr", "normalizeRole", "raw", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "scanPairQr", "updateStateText", "app_debug"})
public final class PairingActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.dashcam.ai.databinding.ActivityPairingBinding binding;
    private com.dashcam.ai.pairing.PairingManager pairingManager;
    private com.dashcam.ai.auth.AuthSessionManager authSessionManager;
    @org.jetbrains.annotations.NotNull()
    private final com.dashcam.ai.network.BackendApiClient apiClient = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> scanLauncher = null;
    
    public PairingActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void generatePairQr() {
    }
    
    private final void scanPairQr() {
    }
    
    private final void completePairing(java.lang.String qrPayload) {
    }
    
    private final void updateStateText() {
    }
    
    private final java.lang.String normalizeRole(java.lang.String raw) {
        return null;
    }
    
    private final android.graphics.Bitmap createQrBitmap(java.lang.String content) {
        return null;
    }
}
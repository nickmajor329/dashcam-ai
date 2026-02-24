package com.dashcam.ai.live;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\n\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0002J\u0012\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0014J\b\u0010\u0013\u001a\u00020\u0010H\u0014J\u0010\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010\u0017\u001a\u00020\u0010H\u0002J\u0010\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u000eH\u0002J\b\u0010\u001a\u001a\u00020\u0010H\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lcom/dashcam/ai/live/LiveViewActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "apiClient", "Lcom/dashcam/ai/network/BackendApiClient;", "authSessionManager", "Lcom/dashcam/ai/auth/AuthSessionManager;", "binding", "Lcom/dashcam/ai/databinding/ActivityLiveViewBinding;", "pairingManager", "Lcom/dashcam/ai/pairing/PairingManager;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "captureWebViewBitmap", "Landroid/graphics/Bitmap;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "requestAndOpenLiveUrl", "role", "", "requestAndOpenNativeLiveViewer", "saveBitmapToGallery", "bitmap", "saveSnapshot", "selectedBitrateKbps", "", "app_debug"})
public final class LiveViewActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.dashcam.ai.databinding.ActivityLiveViewBinding binding;
    private com.dashcam.ai.pairing.PairingManager pairingManager;
    private com.dashcam.ai.auth.AuthSessionManager authSessionManager;
    @org.jetbrains.annotations.NotNull()
    private final com.dashcam.ai.network.BackendApiClient apiClient = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    
    public LiveViewActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void requestAndOpenLiveUrl(java.lang.String role) {
    }
    
    private final void requestAndOpenNativeLiveViewer() {
    }
    
    private final int selectedBitrateKbps() {
        return 0;
    }
    
    private final void saveSnapshot() {
    }
    
    private final android.graphics.Bitmap captureWebViewBitmap() {
        return null;
    }
    
    private final java.lang.String saveBitmapToGallery(android.graphics.Bitmap bitmap) {
        return null;
    }
}
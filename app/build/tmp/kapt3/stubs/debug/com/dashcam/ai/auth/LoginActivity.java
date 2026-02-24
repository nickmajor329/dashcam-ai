package com.dashcam.ai.auth;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0014J\b\u0010\u0011\u001a\u00020\u000eH\u0014J\u0010\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/dashcam/ai/auth/LoginActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "apiClient", "Lcom/dashcam/ai/network/BackendApiClient;", "authSessionManager", "Lcom/dashcam/ai/auth/AuthSessionManager;", "binding", "Lcom/dashcam/ai/databinding/ActivityLoginBinding;", "pairingManager", "Lcom/dashcam/ai/pairing/PairingManager;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "submitAuth", "isRegister", "", "app_debug"})
public final class LoginActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.dashcam.ai.databinding.ActivityLoginBinding binding;
    private com.dashcam.ai.auth.AuthSessionManager authSessionManager;
    private com.dashcam.ai.pairing.PairingManager pairingManager;
    @org.jetbrains.annotations.NotNull()
    private final com.dashcam.ai.network.BackendApiClient apiClient = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    
    public LoginActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    private final void submitAuth(boolean isRegister) {
    }
}
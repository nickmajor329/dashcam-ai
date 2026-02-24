package com.dashcam.ai.settings;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016H\u0002J\u0012\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0014J\b\u0010\u001c\u001a\u00020\u0019H\u0014J\u0010\u0010\u001d\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\b\u0010 \u001a\u00020\u0019H\u0002J\b\u0010!\u001a\u00020\u0019H\u0002J\b\u0010\"\u001a\u00020#H\u0002J\b\u0010$\u001a\u00020\u0019H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/dashcam/ai/settings/SettingsActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "apiClient", "Lcom/dashcam/ai/network/BackendApiClient;", "authSessionManager", "Lcom/dashcam/ai/auth/AuthSessionManager;", "binding", "Lcom/dashcam/ai/databinding/ActivitySettingsBinding;", "geofenceManager", "Lcom/dashcam/ai/location/GeofenceSettingsManager;", "pairingManager", "Lcom/dashcam/ai/pairing/PairingManager;", "privacySettingsManager", "Lcom/dashcam/ai/privacy/PrivacySettingsManager;", "routingManager", "Lcom/dashcam/ai/alerts/AlertRoutingManager;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "settingsManager", "Lcom/dashcam/ai/settings/AppSettingsManager;", "normalizeRole", "", "raw", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "refreshFleetUi", "state", "Lcom/dashcam/ai/pairing/PairingState;", "saveFleetRoleFromInput", "seedUi", "selectedZoneMode", "Lcom/dashcam/ai/location/AlertZoneMode;", "setActiveVehicleFromInput", "app_debug"})
public final class SettingsActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.dashcam.ai.databinding.ActivitySettingsBinding binding;
    private com.dashcam.ai.settings.AppSettingsManager settingsManager;
    private com.dashcam.ai.alerts.AlertRoutingManager routingManager;
    private com.dashcam.ai.location.GeofenceSettingsManager geofenceManager;
    private com.dashcam.ai.privacy.PrivacySettingsManager privacySettingsManager;
    private com.dashcam.ai.pairing.PairingManager pairingManager;
    private com.dashcam.ai.auth.AuthSessionManager authSessionManager;
    @org.jetbrains.annotations.NotNull()
    private final com.dashcam.ai.network.BackendApiClient apiClient = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    
    public SettingsActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    private final void seedUi() {
    }
    
    private final com.dashcam.ai.location.AlertZoneMode selectedZoneMode() {
        return null;
    }
    
    private final void setActiveVehicleFromInput() {
    }
    
    private final void saveFleetRoleFromInput() {
    }
    
    private final java.lang.String normalizeRole(java.lang.String raw) {
        return null;
    }
    
    private final void refreshFleetUi(com.dashcam.ai.pairing.PairingState state) {
    }
}
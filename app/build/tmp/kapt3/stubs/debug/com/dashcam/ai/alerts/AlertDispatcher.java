package com.dashcam.ai.alerts;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/dashcam/ai/alerts/AlertDispatcher;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "pairingManager", "Lcom/dashcam/ai/pairing/PairingManager;", "privacySettingsManager", "Lcom/dashcam/ai/privacy/PrivacySettingsManager;", "routingManager", "Lcom/dashcam/ai/alerts/AlertRoutingManager;", "enqueueAlert", "", "event", "Lcom/dashcam/ai/data/EventEntity;", "app_debug"})
public final class AlertDispatcher {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.dashcam.ai.alerts.AlertRoutingManager routingManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.dashcam.ai.privacy.PrivacySettingsManager privacySettingsManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.dashcam.ai.pairing.PairingManager pairingManager = null;
    
    public AlertDispatcher(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    public final void enqueueAlert(@org.jetbrains.annotations.NotNull()
    com.dashcam.ai.data.EventEntity event) {
    }
}
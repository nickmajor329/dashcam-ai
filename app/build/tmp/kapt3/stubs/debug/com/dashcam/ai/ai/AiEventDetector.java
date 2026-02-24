package com.dashcam.ai.ai;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/dashcam/ai/ai/AiEventDetector;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "detector", "Lcom/dashcam/ai/ai/TfliteClipEventDetector;", "suppressor", "Lcom/dashcam/ai/ai/AiFalsePositiveSuppressor;", "detectFromClip", "Lcom/dashcam/ai/ai/DetectedEvent;", "clipPath", "", "app_debug"})
public final class AiEventDetector {
    @org.jetbrains.annotations.NotNull()
    private final com.dashcam.ai.ai.TfliteClipEventDetector detector = null;
    @org.jetbrains.annotations.NotNull()
    private final com.dashcam.ai.ai.AiFalsePositiveSuppressor suppressor = null;
    
    public AiEventDetector(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.dashcam.ai.ai.DetectedEvent detectFromClip(@org.jetbrains.annotations.NotNull()
    java.lang.String clipPath) {
        return null;
    }
}
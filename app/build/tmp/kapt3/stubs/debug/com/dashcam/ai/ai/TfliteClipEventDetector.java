package com.dashcam.ai.ai;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\u0005J\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u000b\u001a\u00020\u0005H\u0002J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u0005H\u0002J\u0010\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u000eH\u0002R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/dashcam/ai/ai/TfliteClipEventDetector;", "", "context", "Landroid/content/Context;", "modelAssetName", "", "(Landroid/content/Context;Ljava/lang/String;)V", "interpreter", "Lorg/tensorflow/lite/Interpreter;", "detectFromClip", "Lcom/dashcam/ai/ai/DetectedEvent;", "clipPath", "extractFramesForInference", "", "Landroid/graphics/Bitmap;", "loadModelFile", "Ljava/nio/ByteBuffer;", "assetName", "preprocess", "bitmap", "Companion", "app_debug"})
public final class TfliteClipEventDetector {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String modelAssetName = null;
    @org.jetbrains.annotations.Nullable()
    private final org.tensorflow.lite.Interpreter interpreter = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "TfliteClipDetector";
    private static final int INPUT_SIZE = 224;
    private static final int CHANNELS = 3;
    private static final int OUTPUT_CLASSES = 3;
    private static final float MIN_CONFIDENCE = 0.58F;
    private static final int SAMPLE_FRAMES = 5;
    private static final int MOTION_INDEX = 0;
    private static final int PERSON_INDEX = 1;
    private static final int IMPACT_INDEX = 2;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<com.dashcam.ai.data.EventType> EVENT_BY_INDEX = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.dashcam.ai.ai.TfliteClipEventDetector.Companion Companion = null;
    
    public TfliteClipEventDetector(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String modelAssetName) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.dashcam.ai.ai.DetectedEvent detectFromClip(@org.jetbrains.annotations.NotNull()
    java.lang.String clipPath) {
        return null;
    }
    
    private final java.util.List<android.graphics.Bitmap> extractFramesForInference(java.lang.String clipPath) {
        return null;
    }
    
    private final java.nio.ByteBuffer preprocess(android.graphics.Bitmap bitmap) {
        return null;
    }
    
    private final java.nio.ByteBuffer loadModelFile(android.content.Context context, java.lang.String assetName) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/dashcam/ai/ai/TfliteClipEventDetector$Companion;", "", "()V", "CHANNELS", "", "EVENT_BY_INDEX", "", "Lcom/dashcam/ai/data/EventType;", "IMPACT_INDEX", "INPUT_SIZE", "MIN_CONFIDENCE", "", "MOTION_INDEX", "OUTPUT_CLASSES", "PERSON_INDEX", "SAMPLE_FRAMES", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}
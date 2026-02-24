package com.dashcam.ai.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u001c\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\rJ\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u001c\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005\u00a8\u0006\u0015"}, d2 = {"Lcom/dashcam/ai/data/EventDao;", "", "allEvents", "", "Lcom/dashcam/ai/data/EventEntity;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "eventsByType", "eventType", "Lcom/dashcam/ai/data/EventType;", "(Lcom/dashcam/ai/data/EventType;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insert", "", "event", "(Lcom/dashcam/ai/data/EventEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markUploaded", "", "eventId", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "pendingEvents", "pendingEventsByType", "pendingUploadEvents", "app_debug"})
@androidx.room.Dao()
public abstract interface EventDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.dashcam.ai.data.EventEntity event, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM events WHERE uploaded = 0 ORDER BY createdAtMs DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object pendingUploadEvents(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.dashcam.ai.data.EventEntity>> $completion);
    
    @androidx.room.Query(value = "UPDATE events SET uploaded = 1 WHERE id = :eventId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object markUploaded(long eventId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM events ORDER BY createdAtMs DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object allEvents(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.dashcam.ai.data.EventEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM events WHERE eventType = :eventType ORDER BY createdAtMs DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object eventsByType(@org.jetbrains.annotations.NotNull()
    com.dashcam.ai.data.EventType eventType, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.dashcam.ai.data.EventEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM events WHERE uploaded = 0 ORDER BY createdAtMs DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object pendingEvents(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.dashcam.ai.data.EventEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM events WHERE uploaded = 0 AND eventType = :eventType ORDER BY createdAtMs DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object pendingEventsByType(@org.jetbrains.annotations.NotNull()
    com.dashcam.ai.data.EventType eventType, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.dashcam.ai.data.EventEntity>> $completion);
}
package com.dashcam.ai.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class EventDao_Impl implements EventDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<EventEntity> __insertionAdapterOfEventEntity;

  private final EventConverters __eventConverters = new EventConverters();

  private final SharedSQLiteStatement __preparedStmtOfMarkUploaded;

  public EventDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEventEntity = new EntityInsertionAdapter<EventEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `events` (`id`,`eventType`,`confidence`,`createdAtMs`,`clipPath`,`uploaded`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final EventEntity entity) {
        statement.bindLong(1, entity.getId());
        final String _tmp = __eventConverters.fromEventType(entity.getEventType());
        if (_tmp == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, _tmp);
        }
        statement.bindDouble(3, entity.getConfidence());
        statement.bindLong(4, entity.getCreatedAtMs());
        if (entity.getClipPath() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getClipPath());
        }
        final int _tmp_1 = entity.getUploaded() ? 1 : 0;
        statement.bindLong(6, _tmp_1);
      }
    };
    this.__preparedStmtOfMarkUploaded = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE events SET uploaded = 1 WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final EventEntity event, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfEventEntity.insertAndReturnId(event);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object markUploaded(final long eventId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkUploaded.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, eventId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkUploaded.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object pendingUploadEvents(final Continuation<? super List<EventEntity>> $completion) {
    final String _sql = "SELECT * FROM events WHERE uploaded = 0 ORDER BY createdAtMs DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<EventEntity>>() {
      @Override
      @NonNull
      public List<EventEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEventType = CursorUtil.getColumnIndexOrThrow(_cursor, "eventType");
          final int _cursorIndexOfConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "confidence");
          final int _cursorIndexOfCreatedAtMs = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAtMs");
          final int _cursorIndexOfClipPath = CursorUtil.getColumnIndexOrThrow(_cursor, "clipPath");
          final int _cursorIndexOfUploaded = CursorUtil.getColumnIndexOrThrow(_cursor, "uploaded");
          final List<EventEntity> _result = new ArrayList<EventEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EventEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final EventType _tmpEventType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfEventType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfEventType);
            }
            _tmpEventType = __eventConverters.toEventType(_tmp);
            final float _tmpConfidence;
            _tmpConfidence = _cursor.getFloat(_cursorIndexOfConfidence);
            final long _tmpCreatedAtMs;
            _tmpCreatedAtMs = _cursor.getLong(_cursorIndexOfCreatedAtMs);
            final String _tmpClipPath;
            if (_cursor.isNull(_cursorIndexOfClipPath)) {
              _tmpClipPath = null;
            } else {
              _tmpClipPath = _cursor.getString(_cursorIndexOfClipPath);
            }
            final boolean _tmpUploaded;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfUploaded);
            _tmpUploaded = _tmp_1 != 0;
            _item = new EventEntity(_tmpId,_tmpEventType,_tmpConfidence,_tmpCreatedAtMs,_tmpClipPath,_tmpUploaded);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object allEvents(final Continuation<? super List<EventEntity>> $completion) {
    final String _sql = "SELECT * FROM events ORDER BY createdAtMs DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<EventEntity>>() {
      @Override
      @NonNull
      public List<EventEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEventType = CursorUtil.getColumnIndexOrThrow(_cursor, "eventType");
          final int _cursorIndexOfConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "confidence");
          final int _cursorIndexOfCreatedAtMs = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAtMs");
          final int _cursorIndexOfClipPath = CursorUtil.getColumnIndexOrThrow(_cursor, "clipPath");
          final int _cursorIndexOfUploaded = CursorUtil.getColumnIndexOrThrow(_cursor, "uploaded");
          final List<EventEntity> _result = new ArrayList<EventEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EventEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final EventType _tmpEventType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfEventType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfEventType);
            }
            _tmpEventType = __eventConverters.toEventType(_tmp);
            final float _tmpConfidence;
            _tmpConfidence = _cursor.getFloat(_cursorIndexOfConfidence);
            final long _tmpCreatedAtMs;
            _tmpCreatedAtMs = _cursor.getLong(_cursorIndexOfCreatedAtMs);
            final String _tmpClipPath;
            if (_cursor.isNull(_cursorIndexOfClipPath)) {
              _tmpClipPath = null;
            } else {
              _tmpClipPath = _cursor.getString(_cursorIndexOfClipPath);
            }
            final boolean _tmpUploaded;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfUploaded);
            _tmpUploaded = _tmp_1 != 0;
            _item = new EventEntity(_tmpId,_tmpEventType,_tmpConfidence,_tmpCreatedAtMs,_tmpClipPath,_tmpUploaded);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object eventsByType(final EventType eventType,
      final Continuation<? super List<EventEntity>> $completion) {
    final String _sql = "SELECT * FROM events WHERE eventType = ? ORDER BY createdAtMs DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __eventConverters.fromEventType(eventType);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<EventEntity>>() {
      @Override
      @NonNull
      public List<EventEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEventType = CursorUtil.getColumnIndexOrThrow(_cursor, "eventType");
          final int _cursorIndexOfConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "confidence");
          final int _cursorIndexOfCreatedAtMs = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAtMs");
          final int _cursorIndexOfClipPath = CursorUtil.getColumnIndexOrThrow(_cursor, "clipPath");
          final int _cursorIndexOfUploaded = CursorUtil.getColumnIndexOrThrow(_cursor, "uploaded");
          final List<EventEntity> _result = new ArrayList<EventEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EventEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final EventType _tmpEventType;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEventType)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfEventType);
            }
            _tmpEventType = __eventConverters.toEventType(_tmp_1);
            final float _tmpConfidence;
            _tmpConfidence = _cursor.getFloat(_cursorIndexOfConfidence);
            final long _tmpCreatedAtMs;
            _tmpCreatedAtMs = _cursor.getLong(_cursorIndexOfCreatedAtMs);
            final String _tmpClipPath;
            if (_cursor.isNull(_cursorIndexOfClipPath)) {
              _tmpClipPath = null;
            } else {
              _tmpClipPath = _cursor.getString(_cursorIndexOfClipPath);
            }
            final boolean _tmpUploaded;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfUploaded);
            _tmpUploaded = _tmp_2 != 0;
            _item = new EventEntity(_tmpId,_tmpEventType,_tmpConfidence,_tmpCreatedAtMs,_tmpClipPath,_tmpUploaded);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object pendingEvents(final Continuation<? super List<EventEntity>> $completion) {
    final String _sql = "SELECT * FROM events WHERE uploaded = 0 ORDER BY createdAtMs DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<EventEntity>>() {
      @Override
      @NonNull
      public List<EventEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEventType = CursorUtil.getColumnIndexOrThrow(_cursor, "eventType");
          final int _cursorIndexOfConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "confidence");
          final int _cursorIndexOfCreatedAtMs = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAtMs");
          final int _cursorIndexOfClipPath = CursorUtil.getColumnIndexOrThrow(_cursor, "clipPath");
          final int _cursorIndexOfUploaded = CursorUtil.getColumnIndexOrThrow(_cursor, "uploaded");
          final List<EventEntity> _result = new ArrayList<EventEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EventEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final EventType _tmpEventType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfEventType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfEventType);
            }
            _tmpEventType = __eventConverters.toEventType(_tmp);
            final float _tmpConfidence;
            _tmpConfidence = _cursor.getFloat(_cursorIndexOfConfidence);
            final long _tmpCreatedAtMs;
            _tmpCreatedAtMs = _cursor.getLong(_cursorIndexOfCreatedAtMs);
            final String _tmpClipPath;
            if (_cursor.isNull(_cursorIndexOfClipPath)) {
              _tmpClipPath = null;
            } else {
              _tmpClipPath = _cursor.getString(_cursorIndexOfClipPath);
            }
            final boolean _tmpUploaded;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfUploaded);
            _tmpUploaded = _tmp_1 != 0;
            _item = new EventEntity(_tmpId,_tmpEventType,_tmpConfidence,_tmpCreatedAtMs,_tmpClipPath,_tmpUploaded);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object pendingEventsByType(final EventType eventType,
      final Continuation<? super List<EventEntity>> $completion) {
    final String _sql = "SELECT * FROM events WHERE uploaded = 0 AND eventType = ? ORDER BY createdAtMs DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __eventConverters.fromEventType(eventType);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<EventEntity>>() {
      @Override
      @NonNull
      public List<EventEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEventType = CursorUtil.getColumnIndexOrThrow(_cursor, "eventType");
          final int _cursorIndexOfConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "confidence");
          final int _cursorIndexOfCreatedAtMs = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAtMs");
          final int _cursorIndexOfClipPath = CursorUtil.getColumnIndexOrThrow(_cursor, "clipPath");
          final int _cursorIndexOfUploaded = CursorUtil.getColumnIndexOrThrow(_cursor, "uploaded");
          final List<EventEntity> _result = new ArrayList<EventEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EventEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final EventType _tmpEventType;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEventType)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfEventType);
            }
            _tmpEventType = __eventConverters.toEventType(_tmp_1);
            final float _tmpConfidence;
            _tmpConfidence = _cursor.getFloat(_cursorIndexOfConfidence);
            final long _tmpCreatedAtMs;
            _tmpCreatedAtMs = _cursor.getLong(_cursorIndexOfCreatedAtMs);
            final String _tmpClipPath;
            if (_cursor.isNull(_cursorIndexOfClipPath)) {
              _tmpClipPath = null;
            } else {
              _tmpClipPath = _cursor.getString(_cursorIndexOfClipPath);
            }
            final boolean _tmpUploaded;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfUploaded);
            _tmpUploaded = _tmp_2 != 0;
            _item = new EventEntity(_tmpId,_tmpEventType,_tmpConfidence,_tmpCreatedAtMs,_tmpClipPath,_tmpUploaded);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}

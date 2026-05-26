package com.myplanner.app.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.myplanner.app.data.local.entities.PlannerEventEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
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
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PlannerEventDao_Impl implements PlannerEventDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PlannerEventEntity> __insertionAdapterOfPlannerEventEntity;

  private final EntityDeletionOrUpdateAdapter<PlannerEventEntity> __deletionAdapterOfPlannerEventEntity;

  private final EntityDeletionOrUpdateAdapter<PlannerEventEntity> __updateAdapterOfPlannerEventEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteEventById;

  public PlannerEventDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPlannerEventEntity = new EntityInsertionAdapter<PlannerEventEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `planner_events` (`id`,`title`,`description`,`dateEpoch`,`startTimeSecond`,`endTimeSecond`,`color`,`isAllDay`,`createdAtEpoch`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PlannerEventEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, entity.getDescription());
        statement.bindLong(4, entity.getDateEpoch());
        if (entity.getStartTimeSecond() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getStartTimeSecond());
        }
        if (entity.getEndTimeSecond() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getEndTimeSecond());
        }
        statement.bindLong(7, entity.getColor());
        final int _tmp = entity.isAllDay() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getCreatedAtEpoch());
      }
    };
    this.__deletionAdapterOfPlannerEventEntity = new EntityDeletionOrUpdateAdapter<PlannerEventEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `planner_events` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PlannerEventEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfPlannerEventEntity = new EntityDeletionOrUpdateAdapter<PlannerEventEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `planner_events` SET `id` = ?,`title` = ?,`description` = ?,`dateEpoch` = ?,`startTimeSecond` = ?,`endTimeSecond` = ?,`color` = ?,`isAllDay` = ?,`createdAtEpoch` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PlannerEventEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, entity.getDescription());
        statement.bindLong(4, entity.getDateEpoch());
        if (entity.getStartTimeSecond() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getStartTimeSecond());
        }
        if (entity.getEndTimeSecond() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getEndTimeSecond());
        }
        statement.bindLong(7, entity.getColor());
        final int _tmp = entity.isAllDay() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getCreatedAtEpoch());
        statement.bindLong(10, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteEventById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM planner_events WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertEvent(final PlannerEventEntity event,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfPlannerEventEntity.insertAndReturnId(event);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteEvent(final PlannerEventEntity event,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfPlannerEventEntity.handle(event);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateEvent(final PlannerEventEntity event,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPlannerEventEntity.handle(event);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteEventById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteEventById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
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
          __preparedStmtOfDeleteEventById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<PlannerEventEntity>> getAllEvents() {
    final String _sql = "SELECT * FROM planner_events ORDER BY dateEpoch ASC, startTimeSecond ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"planner_events"}, new Callable<List<PlannerEventEntity>>() {
      @Override
      @NonNull
      public List<PlannerEventEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDateEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "dateEpoch");
          final int _cursorIndexOfStartTimeSecond = CursorUtil.getColumnIndexOrThrow(_cursor, "startTimeSecond");
          final int _cursorIndexOfEndTimeSecond = CursorUtil.getColumnIndexOrThrow(_cursor, "endTimeSecond");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfIsAllDay = CursorUtil.getColumnIndexOrThrow(_cursor, "isAllDay");
          final int _cursorIndexOfCreatedAtEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAtEpoch");
          final List<PlannerEventEntity> _result = new ArrayList<PlannerEventEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlannerEventEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final long _tmpDateEpoch;
            _tmpDateEpoch = _cursor.getLong(_cursorIndexOfDateEpoch);
            final Integer _tmpStartTimeSecond;
            if (_cursor.isNull(_cursorIndexOfStartTimeSecond)) {
              _tmpStartTimeSecond = null;
            } else {
              _tmpStartTimeSecond = _cursor.getInt(_cursorIndexOfStartTimeSecond);
            }
            final Integer _tmpEndTimeSecond;
            if (_cursor.isNull(_cursorIndexOfEndTimeSecond)) {
              _tmpEndTimeSecond = null;
            } else {
              _tmpEndTimeSecond = _cursor.getInt(_cursorIndexOfEndTimeSecond);
            }
            final int _tmpColor;
            _tmpColor = _cursor.getInt(_cursorIndexOfColor);
            final boolean _tmpIsAllDay;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsAllDay);
            _tmpIsAllDay = _tmp != 0;
            final long _tmpCreatedAtEpoch;
            _tmpCreatedAtEpoch = _cursor.getLong(_cursorIndexOfCreatedAtEpoch);
            _item = new PlannerEventEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpDateEpoch,_tmpStartTimeSecond,_tmpEndTimeSecond,_tmpColor,_tmpIsAllDay,_tmpCreatedAtEpoch);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<PlannerEventEntity>> getEventsByDate(final long dateEpoch) {
    final String _sql = "SELECT * FROM planner_events WHERE dateEpoch = ? ORDER BY startTimeSecond ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, dateEpoch);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"planner_events"}, new Callable<List<PlannerEventEntity>>() {
      @Override
      @NonNull
      public List<PlannerEventEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDateEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "dateEpoch");
          final int _cursorIndexOfStartTimeSecond = CursorUtil.getColumnIndexOrThrow(_cursor, "startTimeSecond");
          final int _cursorIndexOfEndTimeSecond = CursorUtil.getColumnIndexOrThrow(_cursor, "endTimeSecond");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfIsAllDay = CursorUtil.getColumnIndexOrThrow(_cursor, "isAllDay");
          final int _cursorIndexOfCreatedAtEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAtEpoch");
          final List<PlannerEventEntity> _result = new ArrayList<PlannerEventEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlannerEventEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final long _tmpDateEpoch;
            _tmpDateEpoch = _cursor.getLong(_cursorIndexOfDateEpoch);
            final Integer _tmpStartTimeSecond;
            if (_cursor.isNull(_cursorIndexOfStartTimeSecond)) {
              _tmpStartTimeSecond = null;
            } else {
              _tmpStartTimeSecond = _cursor.getInt(_cursorIndexOfStartTimeSecond);
            }
            final Integer _tmpEndTimeSecond;
            if (_cursor.isNull(_cursorIndexOfEndTimeSecond)) {
              _tmpEndTimeSecond = null;
            } else {
              _tmpEndTimeSecond = _cursor.getInt(_cursorIndexOfEndTimeSecond);
            }
            final int _tmpColor;
            _tmpColor = _cursor.getInt(_cursorIndexOfColor);
            final boolean _tmpIsAllDay;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsAllDay);
            _tmpIsAllDay = _tmp != 0;
            final long _tmpCreatedAtEpoch;
            _tmpCreatedAtEpoch = _cursor.getLong(_cursorIndexOfCreatedAtEpoch);
            _item = new PlannerEventEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpDateEpoch,_tmpStartTimeSecond,_tmpEndTimeSecond,_tmpColor,_tmpIsAllDay,_tmpCreatedAtEpoch);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<PlannerEventEntity>> getEventsByDateRange(final long start, final long end) {
    final String _sql = "SELECT * FROM planner_events WHERE dateEpoch BETWEEN ? AND ? ORDER BY dateEpoch ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, start);
    _argIndex = 2;
    _statement.bindLong(_argIndex, end);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"planner_events"}, new Callable<List<PlannerEventEntity>>() {
      @Override
      @NonNull
      public List<PlannerEventEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDateEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "dateEpoch");
          final int _cursorIndexOfStartTimeSecond = CursorUtil.getColumnIndexOrThrow(_cursor, "startTimeSecond");
          final int _cursorIndexOfEndTimeSecond = CursorUtil.getColumnIndexOrThrow(_cursor, "endTimeSecond");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfIsAllDay = CursorUtil.getColumnIndexOrThrow(_cursor, "isAllDay");
          final int _cursorIndexOfCreatedAtEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAtEpoch");
          final List<PlannerEventEntity> _result = new ArrayList<PlannerEventEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlannerEventEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final long _tmpDateEpoch;
            _tmpDateEpoch = _cursor.getLong(_cursorIndexOfDateEpoch);
            final Integer _tmpStartTimeSecond;
            if (_cursor.isNull(_cursorIndexOfStartTimeSecond)) {
              _tmpStartTimeSecond = null;
            } else {
              _tmpStartTimeSecond = _cursor.getInt(_cursorIndexOfStartTimeSecond);
            }
            final Integer _tmpEndTimeSecond;
            if (_cursor.isNull(_cursorIndexOfEndTimeSecond)) {
              _tmpEndTimeSecond = null;
            } else {
              _tmpEndTimeSecond = _cursor.getInt(_cursorIndexOfEndTimeSecond);
            }
            final int _tmpColor;
            _tmpColor = _cursor.getInt(_cursorIndexOfColor);
            final boolean _tmpIsAllDay;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsAllDay);
            _tmpIsAllDay = _tmp != 0;
            final long _tmpCreatedAtEpoch;
            _tmpCreatedAtEpoch = _cursor.getLong(_cursorIndexOfCreatedAtEpoch);
            _item = new PlannerEventEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpDateEpoch,_tmpStartTimeSecond,_tmpEndTimeSecond,_tmpColor,_tmpIsAllDay,_tmpCreatedAtEpoch);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}

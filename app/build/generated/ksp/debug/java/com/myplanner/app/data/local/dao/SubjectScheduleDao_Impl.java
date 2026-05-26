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
import com.myplanner.app.data.local.entities.SubjectScheduleEntity;
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
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SubjectScheduleDao_Impl implements SubjectScheduleDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SubjectScheduleEntity> __insertionAdapterOfSubjectScheduleEntity;

  private final EntityDeletionOrUpdateAdapter<SubjectScheduleEntity> __deletionAdapterOfSubjectScheduleEntity;

  private final EntityDeletionOrUpdateAdapter<SubjectScheduleEntity> __updateAdapterOfSubjectScheduleEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteScheduleById;

  public SubjectScheduleDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSubjectScheduleEntity = new EntityInsertionAdapter<SubjectScheduleEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `subject_schedules` (`id`,`subjectName`,`teacherName`,`room`,`dayOfWeek`,`startTimeSecond`,`endTimeSecond`,`color`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SubjectScheduleEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getSubjectName());
        statement.bindString(3, entity.getTeacherName());
        statement.bindString(4, entity.getRoom());
        statement.bindString(5, entity.getDayOfWeek());
        statement.bindLong(6, entity.getStartTimeSecond());
        statement.bindLong(7, entity.getEndTimeSecond());
        statement.bindLong(8, entity.getColor());
      }
    };
    this.__deletionAdapterOfSubjectScheduleEntity = new EntityDeletionOrUpdateAdapter<SubjectScheduleEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `subject_schedules` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SubjectScheduleEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfSubjectScheduleEntity = new EntityDeletionOrUpdateAdapter<SubjectScheduleEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `subject_schedules` SET `id` = ?,`subjectName` = ?,`teacherName` = ?,`room` = ?,`dayOfWeek` = ?,`startTimeSecond` = ?,`endTimeSecond` = ?,`color` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SubjectScheduleEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getSubjectName());
        statement.bindString(3, entity.getTeacherName());
        statement.bindString(4, entity.getRoom());
        statement.bindString(5, entity.getDayOfWeek());
        statement.bindLong(6, entity.getStartTimeSecond());
        statement.bindLong(7, entity.getEndTimeSecond());
        statement.bindLong(8, entity.getColor());
        statement.bindLong(9, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteScheduleById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM subject_schedules WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertSchedule(final SubjectScheduleEntity schedule,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfSubjectScheduleEntity.insertAndReturnId(schedule);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteSchedule(final SubjectScheduleEntity schedule,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfSubjectScheduleEntity.handle(schedule);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSchedule(final SubjectScheduleEntity schedule,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfSubjectScheduleEntity.handle(schedule);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteScheduleById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteScheduleById.acquire();
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
          __preparedStmtOfDeleteScheduleById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<SubjectScheduleEntity>> getAllSchedules() {
    final String _sql = "SELECT * FROM subject_schedules ORDER BY dayOfWeek ASC, startTimeSecond ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"subject_schedules"}, new Callable<List<SubjectScheduleEntity>>() {
      @Override
      @NonNull
      public List<SubjectScheduleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfTeacherName = CursorUtil.getColumnIndexOrThrow(_cursor, "teacherName");
          final int _cursorIndexOfRoom = CursorUtil.getColumnIndexOrThrow(_cursor, "room");
          final int _cursorIndexOfDayOfWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "dayOfWeek");
          final int _cursorIndexOfStartTimeSecond = CursorUtil.getColumnIndexOrThrow(_cursor, "startTimeSecond");
          final int _cursorIndexOfEndTimeSecond = CursorUtil.getColumnIndexOrThrow(_cursor, "endTimeSecond");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final List<SubjectScheduleEntity> _result = new ArrayList<SubjectScheduleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SubjectScheduleEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpSubjectName;
            _tmpSubjectName = _cursor.getString(_cursorIndexOfSubjectName);
            final String _tmpTeacherName;
            _tmpTeacherName = _cursor.getString(_cursorIndexOfTeacherName);
            final String _tmpRoom;
            _tmpRoom = _cursor.getString(_cursorIndexOfRoom);
            final String _tmpDayOfWeek;
            _tmpDayOfWeek = _cursor.getString(_cursorIndexOfDayOfWeek);
            final int _tmpStartTimeSecond;
            _tmpStartTimeSecond = _cursor.getInt(_cursorIndexOfStartTimeSecond);
            final int _tmpEndTimeSecond;
            _tmpEndTimeSecond = _cursor.getInt(_cursorIndexOfEndTimeSecond);
            final int _tmpColor;
            _tmpColor = _cursor.getInt(_cursorIndexOfColor);
            _item = new SubjectScheduleEntity(_tmpId,_tmpSubjectName,_tmpTeacherName,_tmpRoom,_tmpDayOfWeek,_tmpStartTimeSecond,_tmpEndTimeSecond,_tmpColor);
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
  public Flow<List<SubjectScheduleEntity>> getSchedulesByDay(final String day) {
    final String _sql = "SELECT * FROM subject_schedules WHERE dayOfWeek = ? ORDER BY startTimeSecond ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, day);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"subject_schedules"}, new Callable<List<SubjectScheduleEntity>>() {
      @Override
      @NonNull
      public List<SubjectScheduleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfTeacherName = CursorUtil.getColumnIndexOrThrow(_cursor, "teacherName");
          final int _cursorIndexOfRoom = CursorUtil.getColumnIndexOrThrow(_cursor, "room");
          final int _cursorIndexOfDayOfWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "dayOfWeek");
          final int _cursorIndexOfStartTimeSecond = CursorUtil.getColumnIndexOrThrow(_cursor, "startTimeSecond");
          final int _cursorIndexOfEndTimeSecond = CursorUtil.getColumnIndexOrThrow(_cursor, "endTimeSecond");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final List<SubjectScheduleEntity> _result = new ArrayList<SubjectScheduleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SubjectScheduleEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpSubjectName;
            _tmpSubjectName = _cursor.getString(_cursorIndexOfSubjectName);
            final String _tmpTeacherName;
            _tmpTeacherName = _cursor.getString(_cursorIndexOfTeacherName);
            final String _tmpRoom;
            _tmpRoom = _cursor.getString(_cursorIndexOfRoom);
            final String _tmpDayOfWeek;
            _tmpDayOfWeek = _cursor.getString(_cursorIndexOfDayOfWeek);
            final int _tmpStartTimeSecond;
            _tmpStartTimeSecond = _cursor.getInt(_cursorIndexOfStartTimeSecond);
            final int _tmpEndTimeSecond;
            _tmpEndTimeSecond = _cursor.getInt(_cursorIndexOfEndTimeSecond);
            final int _tmpColor;
            _tmpColor = _cursor.getInt(_cursorIndexOfColor);
            _item = new SubjectScheduleEntity(_tmpId,_tmpSubjectName,_tmpTeacherName,_tmpRoom,_tmpDayOfWeek,_tmpStartTimeSecond,_tmpEndTimeSecond,_tmpColor);
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

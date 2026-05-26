package com.myplanner.app.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.myplanner.app.data.local.entities.TaskEntity;
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
public final class TaskDao_Impl implements TaskDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TaskEntity> __insertionAdapterOfTaskEntity;

  private final EntityDeletionOrUpdateAdapter<TaskEntity> __deletionAdapterOfTaskEntity;

  private final EntityDeletionOrUpdateAdapter<TaskEntity> __updateAdapterOfTaskEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteTaskById;

  public TaskDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTaskEntity = new EntityInsertionAdapter<TaskEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `tasks` (`id`,`title`,`description`,`subject`,`dueDateEpoch`,`priority`,`status`,`hasReminder`,`reminderEpoch`,`createdAtEpoch`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TaskEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, entity.getDescription());
        statement.bindString(4, entity.getSubject());
        if (entity.getDueDateEpoch() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getDueDateEpoch());
        }
        statement.bindString(6, entity.getPriority());
        statement.bindString(7, entity.getStatus());
        final int _tmp = entity.getHasReminder() ? 1 : 0;
        statement.bindLong(8, _tmp);
        if (entity.getReminderEpoch() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getReminderEpoch());
        }
        statement.bindLong(10, entity.getCreatedAtEpoch());
      }
    };
    this.__deletionAdapterOfTaskEntity = new EntityDeletionOrUpdateAdapter<TaskEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `tasks` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TaskEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfTaskEntity = new EntityDeletionOrUpdateAdapter<TaskEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `tasks` SET `id` = ?,`title` = ?,`description` = ?,`subject` = ?,`dueDateEpoch` = ?,`priority` = ?,`status` = ?,`hasReminder` = ?,`reminderEpoch` = ?,`createdAtEpoch` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TaskEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, entity.getDescription());
        statement.bindString(4, entity.getSubject());
        if (entity.getDueDateEpoch() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getDueDateEpoch());
        }
        statement.bindString(6, entity.getPriority());
        statement.bindString(7, entity.getStatus());
        final int _tmp = entity.getHasReminder() ? 1 : 0;
        statement.bindLong(8, _tmp);
        if (entity.getReminderEpoch() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getReminderEpoch());
        }
        statement.bindLong(10, entity.getCreatedAtEpoch());
        statement.bindLong(11, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteTaskById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM tasks WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertTask(final TaskEntity task, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTaskEntity.insertAndReturnId(task);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteTask(final TaskEntity task, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfTaskEntity.handle(task);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTask(final TaskEntity task, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTaskEntity.handle(task);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteTaskById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteTaskById.acquire();
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
          __preparedStmtOfDeleteTaskById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<TaskEntity>> getAllTasks() {
    final String _sql = "SELECT * FROM tasks ORDER BY dueDateEpoch ASC, priority DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tasks"}, new Callable<List<TaskEntity>>() {
      @Override
      @NonNull
      public List<TaskEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfDueDateEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDateEpoch");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfHasReminder = CursorUtil.getColumnIndexOrThrow(_cursor, "hasReminder");
          final int _cursorIndexOfReminderEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEpoch");
          final int _cursorIndexOfCreatedAtEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAtEpoch");
          final List<TaskEntity> _result = new ArrayList<TaskEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TaskEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpSubject;
            _tmpSubject = _cursor.getString(_cursorIndexOfSubject);
            final Long _tmpDueDateEpoch;
            if (_cursor.isNull(_cursorIndexOfDueDateEpoch)) {
              _tmpDueDateEpoch = null;
            } else {
              _tmpDueDateEpoch = _cursor.getLong(_cursorIndexOfDueDateEpoch);
            }
            final String _tmpPriority;
            _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final boolean _tmpHasReminder;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfHasReminder);
            _tmpHasReminder = _tmp != 0;
            final Long _tmpReminderEpoch;
            if (_cursor.isNull(_cursorIndexOfReminderEpoch)) {
              _tmpReminderEpoch = null;
            } else {
              _tmpReminderEpoch = _cursor.getLong(_cursorIndexOfReminderEpoch);
            }
            final long _tmpCreatedAtEpoch;
            _tmpCreatedAtEpoch = _cursor.getLong(_cursorIndexOfCreatedAtEpoch);
            _item = new TaskEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpSubject,_tmpDueDateEpoch,_tmpPriority,_tmpStatus,_tmpHasReminder,_tmpReminderEpoch,_tmpCreatedAtEpoch);
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
  public Flow<List<TaskEntity>> getPendingTasks() {
    final String _sql = "SELECT * FROM tasks WHERE status != 'DONE' ORDER BY dueDateEpoch ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tasks"}, new Callable<List<TaskEntity>>() {
      @Override
      @NonNull
      public List<TaskEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfDueDateEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDateEpoch");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfHasReminder = CursorUtil.getColumnIndexOrThrow(_cursor, "hasReminder");
          final int _cursorIndexOfReminderEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEpoch");
          final int _cursorIndexOfCreatedAtEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAtEpoch");
          final List<TaskEntity> _result = new ArrayList<TaskEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TaskEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpSubject;
            _tmpSubject = _cursor.getString(_cursorIndexOfSubject);
            final Long _tmpDueDateEpoch;
            if (_cursor.isNull(_cursorIndexOfDueDateEpoch)) {
              _tmpDueDateEpoch = null;
            } else {
              _tmpDueDateEpoch = _cursor.getLong(_cursorIndexOfDueDateEpoch);
            }
            final String _tmpPriority;
            _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final boolean _tmpHasReminder;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfHasReminder);
            _tmpHasReminder = _tmp != 0;
            final Long _tmpReminderEpoch;
            if (_cursor.isNull(_cursorIndexOfReminderEpoch)) {
              _tmpReminderEpoch = null;
            } else {
              _tmpReminderEpoch = _cursor.getLong(_cursorIndexOfReminderEpoch);
            }
            final long _tmpCreatedAtEpoch;
            _tmpCreatedAtEpoch = _cursor.getLong(_cursorIndexOfCreatedAtEpoch);
            _item = new TaskEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpSubject,_tmpDueDateEpoch,_tmpPriority,_tmpStatus,_tmpHasReminder,_tmpReminderEpoch,_tmpCreatedAtEpoch);
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
  public Object getTaskById(final long id, final Continuation<? super TaskEntity> $completion) {
    final String _sql = "SELECT * FROM tasks WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<TaskEntity>() {
      @Override
      @Nullable
      public TaskEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfDueDateEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDateEpoch");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfHasReminder = CursorUtil.getColumnIndexOrThrow(_cursor, "hasReminder");
          final int _cursorIndexOfReminderEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEpoch");
          final int _cursorIndexOfCreatedAtEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAtEpoch");
          final TaskEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpSubject;
            _tmpSubject = _cursor.getString(_cursorIndexOfSubject);
            final Long _tmpDueDateEpoch;
            if (_cursor.isNull(_cursorIndexOfDueDateEpoch)) {
              _tmpDueDateEpoch = null;
            } else {
              _tmpDueDateEpoch = _cursor.getLong(_cursorIndexOfDueDateEpoch);
            }
            final String _tmpPriority;
            _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final boolean _tmpHasReminder;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfHasReminder);
            _tmpHasReminder = _tmp != 0;
            final Long _tmpReminderEpoch;
            if (_cursor.isNull(_cursorIndexOfReminderEpoch)) {
              _tmpReminderEpoch = null;
            } else {
              _tmpReminderEpoch = _cursor.getLong(_cursorIndexOfReminderEpoch);
            }
            final long _tmpCreatedAtEpoch;
            _tmpCreatedAtEpoch = _cursor.getLong(_cursorIndexOfCreatedAtEpoch);
            _result = new TaskEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpSubject,_tmpDueDateEpoch,_tmpPriority,_tmpStatus,_tmpHasReminder,_tmpReminderEpoch,_tmpCreatedAtEpoch);
          } else {
            _result = null;
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
  public Object getUpcomingReminders(final long now,
      final Continuation<? super List<TaskEntity>> $completion) {
    final String _sql = "SELECT * FROM tasks WHERE hasReminder = 1 AND reminderEpoch > ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, now);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<TaskEntity>>() {
      @Override
      @NonNull
      public List<TaskEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfDueDateEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDateEpoch");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfHasReminder = CursorUtil.getColumnIndexOrThrow(_cursor, "hasReminder");
          final int _cursorIndexOfReminderEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEpoch");
          final int _cursorIndexOfCreatedAtEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAtEpoch");
          final List<TaskEntity> _result = new ArrayList<TaskEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TaskEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpSubject;
            _tmpSubject = _cursor.getString(_cursorIndexOfSubject);
            final Long _tmpDueDateEpoch;
            if (_cursor.isNull(_cursorIndexOfDueDateEpoch)) {
              _tmpDueDateEpoch = null;
            } else {
              _tmpDueDateEpoch = _cursor.getLong(_cursorIndexOfDueDateEpoch);
            }
            final String _tmpPriority;
            _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final boolean _tmpHasReminder;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfHasReminder);
            _tmpHasReminder = _tmp != 0;
            final Long _tmpReminderEpoch;
            if (_cursor.isNull(_cursorIndexOfReminderEpoch)) {
              _tmpReminderEpoch = null;
            } else {
              _tmpReminderEpoch = _cursor.getLong(_cursorIndexOfReminderEpoch);
            }
            final long _tmpCreatedAtEpoch;
            _tmpCreatedAtEpoch = _cursor.getLong(_cursorIndexOfCreatedAtEpoch);
            _item = new TaskEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpSubject,_tmpDueDateEpoch,_tmpPriority,_tmpStatus,_tmpHasReminder,_tmpReminderEpoch,_tmpCreatedAtEpoch);
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
  public Flow<List<TaskEntity>> getTasksByDateRange(final long start, final long end) {
    final String _sql = "SELECT * FROM tasks WHERE dueDateEpoch BETWEEN ? AND ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, start);
    _argIndex = 2;
    _statement.bindLong(_argIndex, end);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tasks"}, new Callable<List<TaskEntity>>() {
      @Override
      @NonNull
      public List<TaskEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfDueDateEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDateEpoch");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfHasReminder = CursorUtil.getColumnIndexOrThrow(_cursor, "hasReminder");
          final int _cursorIndexOfReminderEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEpoch");
          final int _cursorIndexOfCreatedAtEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAtEpoch");
          final List<TaskEntity> _result = new ArrayList<TaskEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TaskEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpSubject;
            _tmpSubject = _cursor.getString(_cursorIndexOfSubject);
            final Long _tmpDueDateEpoch;
            if (_cursor.isNull(_cursorIndexOfDueDateEpoch)) {
              _tmpDueDateEpoch = null;
            } else {
              _tmpDueDateEpoch = _cursor.getLong(_cursorIndexOfDueDateEpoch);
            }
            final String _tmpPriority;
            _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final boolean _tmpHasReminder;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfHasReminder);
            _tmpHasReminder = _tmp != 0;
            final Long _tmpReminderEpoch;
            if (_cursor.isNull(_cursorIndexOfReminderEpoch)) {
              _tmpReminderEpoch = null;
            } else {
              _tmpReminderEpoch = _cursor.getLong(_cursorIndexOfReminderEpoch);
            }
            final long _tmpCreatedAtEpoch;
            _tmpCreatedAtEpoch = _cursor.getLong(_cursorIndexOfCreatedAtEpoch);
            _item = new TaskEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpSubject,_tmpDueDateEpoch,_tmpPriority,_tmpStatus,_tmpHasReminder,_tmpReminderEpoch,_tmpCreatedAtEpoch);
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

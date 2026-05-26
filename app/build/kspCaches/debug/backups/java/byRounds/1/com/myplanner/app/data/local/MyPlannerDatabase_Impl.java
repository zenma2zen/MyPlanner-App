package com.myplanner.app.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.myplanner.app.data.local.dao.PlannerEventDao;
import com.myplanner.app.data.local.dao.PlannerEventDao_Impl;
import com.myplanner.app.data.local.dao.SubjectScheduleDao;
import com.myplanner.app.data.local.dao.SubjectScheduleDao_Impl;
import com.myplanner.app.data.local.dao.TaskDao;
import com.myplanner.app.data.local.dao.TaskDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MyPlannerDatabase_Impl extends MyPlannerDatabase {
  private volatile TaskDao _taskDao;

  private volatile PlannerEventDao _plannerEventDao;

  private volatile SubjectScheduleDao _subjectScheduleDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `tasks` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `subject` TEXT NOT NULL, `dueDateEpoch` INTEGER, `priority` TEXT NOT NULL, `status` TEXT NOT NULL, `hasReminder` INTEGER NOT NULL, `reminderEpoch` INTEGER, `createdAtEpoch` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `planner_events` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `dateEpoch` INTEGER NOT NULL, `startTimeSecond` INTEGER, `endTimeSecond` INTEGER, `color` INTEGER NOT NULL, `isAllDay` INTEGER NOT NULL, `createdAtEpoch` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `subject_schedules` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `subjectName` TEXT NOT NULL, `teacherName` TEXT NOT NULL, `room` TEXT NOT NULL, `dayOfWeek` TEXT NOT NULL, `startTimeSecond` INTEGER NOT NULL, `endTimeSecond` INTEGER NOT NULL, `color` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '3a2110e27cfdd518bba866d054fd190d')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `tasks`");
        db.execSQL("DROP TABLE IF EXISTS `planner_events`");
        db.execSQL("DROP TABLE IF EXISTS `subject_schedules`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsTasks = new HashMap<String, TableInfo.Column>(10);
        _columnsTasks.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("subject", new TableInfo.Column("subject", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("dueDateEpoch", new TableInfo.Column("dueDateEpoch", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("priority", new TableInfo.Column("priority", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("hasReminder", new TableInfo.Column("hasReminder", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("reminderEpoch", new TableInfo.Column("reminderEpoch", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("createdAtEpoch", new TableInfo.Column("createdAtEpoch", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTasks = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTasks = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTasks = new TableInfo("tasks", _columnsTasks, _foreignKeysTasks, _indicesTasks);
        final TableInfo _existingTasks = TableInfo.read(db, "tasks");
        if (!_infoTasks.equals(_existingTasks)) {
          return new RoomOpenHelper.ValidationResult(false, "tasks(com.myplanner.app.data.local.entities.TaskEntity).\n"
                  + " Expected:\n" + _infoTasks + "\n"
                  + " Found:\n" + _existingTasks);
        }
        final HashMap<String, TableInfo.Column> _columnsPlannerEvents = new HashMap<String, TableInfo.Column>(9);
        _columnsPlannerEvents.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlannerEvents.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlannerEvents.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlannerEvents.put("dateEpoch", new TableInfo.Column("dateEpoch", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlannerEvents.put("startTimeSecond", new TableInfo.Column("startTimeSecond", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlannerEvents.put("endTimeSecond", new TableInfo.Column("endTimeSecond", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlannerEvents.put("color", new TableInfo.Column("color", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlannerEvents.put("isAllDay", new TableInfo.Column("isAllDay", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlannerEvents.put("createdAtEpoch", new TableInfo.Column("createdAtEpoch", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPlannerEvents = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPlannerEvents = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPlannerEvents = new TableInfo("planner_events", _columnsPlannerEvents, _foreignKeysPlannerEvents, _indicesPlannerEvents);
        final TableInfo _existingPlannerEvents = TableInfo.read(db, "planner_events");
        if (!_infoPlannerEvents.equals(_existingPlannerEvents)) {
          return new RoomOpenHelper.ValidationResult(false, "planner_events(com.myplanner.app.data.local.entities.PlannerEventEntity).\n"
                  + " Expected:\n" + _infoPlannerEvents + "\n"
                  + " Found:\n" + _existingPlannerEvents);
        }
        final HashMap<String, TableInfo.Column> _columnsSubjectSchedules = new HashMap<String, TableInfo.Column>(8);
        _columnsSubjectSchedules.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjectSchedules.put("subjectName", new TableInfo.Column("subjectName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjectSchedules.put("teacherName", new TableInfo.Column("teacherName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjectSchedules.put("room", new TableInfo.Column("room", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjectSchedules.put("dayOfWeek", new TableInfo.Column("dayOfWeek", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjectSchedules.put("startTimeSecond", new TableInfo.Column("startTimeSecond", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjectSchedules.put("endTimeSecond", new TableInfo.Column("endTimeSecond", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjectSchedules.put("color", new TableInfo.Column("color", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSubjectSchedules = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSubjectSchedules = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSubjectSchedules = new TableInfo("subject_schedules", _columnsSubjectSchedules, _foreignKeysSubjectSchedules, _indicesSubjectSchedules);
        final TableInfo _existingSubjectSchedules = TableInfo.read(db, "subject_schedules");
        if (!_infoSubjectSchedules.equals(_existingSubjectSchedules)) {
          return new RoomOpenHelper.ValidationResult(false, "subject_schedules(com.myplanner.app.data.local.entities.SubjectScheduleEntity).\n"
                  + " Expected:\n" + _infoSubjectSchedules + "\n"
                  + " Found:\n" + _existingSubjectSchedules);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "3a2110e27cfdd518bba866d054fd190d", "2f8b75bcb2b62cccbe7ced67aacbfc77");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "tasks","planner_events","subject_schedules");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `tasks`");
      _db.execSQL("DELETE FROM `planner_events`");
      _db.execSQL("DELETE FROM `subject_schedules`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(TaskDao.class, TaskDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PlannerEventDao.class, PlannerEventDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SubjectScheduleDao.class, SubjectScheduleDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public TaskDao taskDao() {
    if (_taskDao != null) {
      return _taskDao;
    } else {
      synchronized(this) {
        if(_taskDao == null) {
          _taskDao = new TaskDao_Impl(this);
        }
        return _taskDao;
      }
    }
  }

  @Override
  public PlannerEventDao plannerEventDao() {
    if (_plannerEventDao != null) {
      return _plannerEventDao;
    } else {
      synchronized(this) {
        if(_plannerEventDao == null) {
          _plannerEventDao = new PlannerEventDao_Impl(this);
        }
        return _plannerEventDao;
      }
    }
  }

  @Override
  public SubjectScheduleDao subjectScheduleDao() {
    if (_subjectScheduleDao != null) {
      return _subjectScheduleDao;
    } else {
      synchronized(this) {
        if(_subjectScheduleDao == null) {
          _subjectScheduleDao = new SubjectScheduleDao_Impl(this);
        }
        return _subjectScheduleDao;
      }
    }
  }
}

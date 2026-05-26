package com.myplanner.app.data.local.dao

import androidx.room.*
import com.myplanner.app.data.local.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY dueDateEpoch ASC, priority DESC")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE status != 'DONE' ORDER BY dueDateEpoch ASC")
    fun getPendingTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: Long): TaskEntity?

    @Query("SELECT * FROM tasks WHERE hasReminder = 1 AND reminderEpoch > :now")
    suspend fun getUpcomingReminders(now: Long): List<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity): Long

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun deleteTaskById(id: Long)

    @Query("SELECT * FROM tasks WHERE dueDateEpoch BETWEEN :start AND :end")
    fun getTasksByDateRange(start: Long, end: Long): Flow<List<TaskEntity>>
}

@Dao
interface PlannerEventDao {
    @Query("SELECT * FROM planner_events ORDER BY dateEpoch ASC, startTimeSecond ASC")
    fun getAllEvents(): Flow<List<PlannerEventEntity>>

    @Query("SELECT * FROM planner_events WHERE dateEpoch = :dateEpoch ORDER BY startTimeSecond ASC")
    fun getEventsByDate(dateEpoch: Long): Flow<List<PlannerEventEntity>>

    @Query("SELECT * FROM planner_events WHERE dateEpoch BETWEEN :start AND :end ORDER BY dateEpoch ASC")
    fun getEventsByDateRange(start: Long, end: Long): Flow<List<PlannerEventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: PlannerEventEntity): Long

    @Update
    suspend fun updateEvent(event: PlannerEventEntity)

    @Delete
    suspend fun deleteEvent(event: PlannerEventEntity)

    @Query("DELETE FROM planner_events WHERE id = :id")
    suspend fun deleteEventById(id: Long)
}

@Dao
interface SubjectScheduleDao {
    @Query("SELECT * FROM subject_schedules ORDER BY dayOfWeek ASC, startTimeSecond ASC")
    fun getAllSchedules(): Flow<List<SubjectScheduleEntity>>

    @Query("SELECT * FROM subject_schedules WHERE dayOfWeek = :day ORDER BY startTimeSecond ASC")
    fun getSchedulesByDay(day: String): Flow<List<SubjectScheduleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(schedule: SubjectScheduleEntity): Long

    @Update
    suspend fun updateSchedule(schedule: SubjectScheduleEntity)

    @Delete
    suspend fun deleteSchedule(schedule: SubjectScheduleEntity)

    @Query("DELETE FROM subject_schedules WHERE id = :id")
    suspend fun deleteScheduleById(id: Long)
}

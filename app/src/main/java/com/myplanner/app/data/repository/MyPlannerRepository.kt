package com.myplanner.app.data.repository

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.myplanner.app.data.local.dao.*
import com.myplanner.app.data.local.entities.*
import com.myplanner.app.domain.model.*
import com.myplanner.app.widget.ScheduleWidget
import com.myplanner.app.widget.TaskWidget
import dagger.hilt.android.qualifiers.ApplicationContext
import androidx.glance.appwidget.updateAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject
import javax.inject.Singleton

data class ExportData(
    val version: Int = 1,
    val exportedAt: String = LocalDateTime.now().toString(),
    val tasks: List<TaskJson> = emptyList(),
    val plannerEvents: List<PlannerEventJson> = emptyList(),
    val subjectSchedules: List<SubjectScheduleJson> = emptyList()
)

data class TaskJson(
    val id: Long, val title: String, val description: String,
    val subject: String, val dueDate: String?, val priority: String,
    val status: String, val hasReminder: Boolean, val reminderTime: String?,
    val createdAt: String
)

data class PlannerEventJson(
    val id: Long, val title: String, val description: String,
    val date: String, val startTime: String?, val endTime: String?,
    val color: Int, val isAllDay: Boolean
)

data class SubjectScheduleJson(
    val id: Long, val subjectName: String, val teacherName: String,
    val room: String, val dayOfWeek: String, val startTime: String,
    val endTime: String, val color: Int
)

@Singleton
class MyPlannerRepository @Inject constructor(
    private val taskDao: TaskDao,
    private val plannerEventDao: PlannerEventDao,
    private val subjectScheduleDao: SubjectScheduleDao,
    @ApplicationContext private val context: android.content.Context
) {
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    private suspend fun updateWidgets() {
        TaskWidget().updateAll(context)
        ScheduleWidget().updateAll(context)
    }

    // ─── Tasks ───────────────────────────────────────────────────────────────
    fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks().map { it.map { e -> e.toDomain() } }
    fun getPendingTasks(): Flow<List<Task>> = taskDao.getPendingTasks().map { it.map { e -> e.toDomain() } }

    suspend fun insertTask(task: Task): Long {
        val id = taskDao.insertTask(task.toEntity())
        updateWidgets()
        return id
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task.toEntity())
        updateWidgets()
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task.toEntity())
        updateWidgets()
    }

    suspend fun getTaskById(id: Long): Task? = taskDao.getTaskById(id)?.toDomain()

    // ─── Planner Events ──────────────────────────────────────────────────────
    fun getAllEvents(): Flow<List<PlannerEvent>> = plannerEventDao.getAllEvents().map { it.map { e -> e.toDomain() } }
    fun getEventsByDate(date: LocalDate): Flow<List<PlannerEvent>> =
        plannerEventDao.getEventsByDate(date.toEpochDay()).map { it.map { e -> e.toDomain() } }

    suspend fun insertEvent(event: PlannerEvent): Long {
        val id = plannerEventDao.insertEvent(event.toEntity())
        updateWidgets()
        return id
    }

    suspend fun updateEvent(event: PlannerEvent) {
        plannerEventDao.updateEvent(event.toEntity())
        updateWidgets()
    }

    suspend fun deleteEvent(event: PlannerEvent) {
        plannerEventDao.deleteEvent(event.toEntity())
        updateWidgets()
    }

    // ─── Subject Schedules ───────────────────────────────────────────────────
    fun getAllSchedules(): Flow<List<SubjectSchedule>> = subjectScheduleDao.getAllSchedules().map { it.map { e -> e.toDomain() } }
    fun getSchedulesByDay(day: DayOfWeek): Flow<List<SubjectSchedule>> =
        subjectScheduleDao.getSchedulesByDay(day.name).map { it.map { e -> e.toDomain() } }

    suspend fun insertSchedule(schedule: SubjectSchedule): Long {
        val id = subjectScheduleDao.insertSchedule(schedule.toEntity())
        updateWidgets()
        return id
    }

    suspend fun updateSchedule(schedule: SubjectSchedule) {
        subjectScheduleDao.updateSchedule(schedule.toEntity())
        updateWidgets()
    }

    suspend fun deleteSchedule(schedule: SubjectSchedule) {
        subjectScheduleDao.deleteSchedule(schedule.toEntity())
        updateWidgets()
    }

    // ─── Export JSON ─────────────────────────────────────────────────────────
    suspend fun exportToJson(
        tasks: List<Task>,
        events: List<PlannerEvent>,
        schedules: List<SubjectSchedule>
    ): String {
        val data = ExportData(
            tasks = tasks.map { t ->
                TaskJson(t.id, t.title, t.description, t.subject,
                    t.dueDate?.toString(), t.priority.name, t.status.name,
                    t.hasReminder, t.reminderTime?.toString(), t.createdAt.toString())
            },
            plannerEvents = events.map { e ->
                PlannerEventJson(e.id, e.title, e.description, e.date.toString(),
                    e.startTime?.toString(), e.endTime?.toString(), e.color, e.isAllDay)
            },
            subjectSchedules = schedules.map { s ->
                SubjectScheduleJson(s.id, s.subjectName, s.teacherName, s.room,
                    s.dayOfWeek.name, s.startTime.toString(), s.endTime.toString(), s.color)
            }
        )
        return gson.toJson(data)
    }

    // ─── Import JSON ─────────────────────────────────────────────────────────
    suspend fun importFromJson(json: String): Result<ImportSummary> {
        return try {
            val data = gson.fromJson(json, ExportData::class.java)
            var taskCount = 0; var eventCount = 0; var scheduleCount = 0

            data.tasks.forEach { t ->
                taskDao.insertTask(TaskEntity(
                    title = t.title, description = t.description, subject = t.subject,
                    dueDateEpoch = t.dueDate?.let { LocalDateTime.parse(it).toEpochSecond(java.time.ZoneOffset.UTC) },
                    priority = t.priority, status = t.status, hasReminder = t.hasReminder,
                    reminderEpoch = t.reminderTime?.let { LocalDateTime.parse(it).toEpochSecond(java.time.ZoneOffset.UTC) }
                )); taskCount++
            }
            data.plannerEvents.forEach { e ->
                plannerEventDao.insertEvent(PlannerEventEntity(
                    title = e.title, description = e.description,
                    dateEpoch = LocalDate.parse(e.date).toEpochDay(),
                    startTimeSecond = e.startTime?.let { LocalTime.parse(it).toSecondOfDay() },
                    endTimeSecond = e.endTime?.let { LocalTime.parse(it).toSecondOfDay() },
                    color = e.color, isAllDay = e.isAllDay
                )); eventCount++
            }
            data.subjectSchedules.forEach { s ->
                subjectScheduleDao.insertSchedule(SubjectScheduleEntity(
                    subjectName = s.subjectName, teacherName = s.teacherName, room = s.room,
                    dayOfWeek = s.dayOfWeek,
                    startTimeSecond = LocalTime.parse(s.startTime).toSecondOfDay(),
                    endTimeSecond = LocalTime.parse(s.endTime).toSecondOfDay(),
                    color = s.color
                )); scheduleCount++
            }
            Result.success(ImportSummary(taskCount, eventCount, scheduleCount))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

data class ImportSummary(val tasks: Int, val events: Int, val schedules: Int)

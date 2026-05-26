package com.myplanner.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myplanner.app.domain.model.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String = "",
    val subject: String = "",
    val dueDateEpoch: Long? = null,
    val priority: String = "MEDIUM",
    val status: String = "PENDING",
    val hasReminder: Boolean = false,
    val reminderEpoch: Long? = null,
    val createdAtEpoch: Long = System.currentTimeMillis()
) {
    fun toDomain() = Task(
        id = id,
        title = title,
        description = description,
        subject = subject,
        dueDate = dueDateEpoch?.let { LocalDateTime.ofEpochSecond(it, 0, java.time.ZoneOffset.UTC) },
        priority = Priority.valueOf(priority),
        status = TaskStatus.valueOf(status),
        hasReminder = hasReminder,
        reminderTime = reminderEpoch?.let { LocalDateTime.ofEpochSecond(it, 0, java.time.ZoneOffset.UTC) },
        createdAt = LocalDateTime.ofEpochSecond(createdAtEpoch / 1000, 0, java.time.ZoneOffset.UTC)
    )
}

fun Task.toEntity() = TaskEntity(
    id = id,
    title = title,
    description = description,
    subject = subject,
    dueDateEpoch = dueDate?.toEpochSecond(java.time.ZoneOffset.UTC),
    priority = priority.name,
    status = status.name,
    hasReminder = hasReminder,
    reminderEpoch = reminderTime?.toEpochSecond(java.time.ZoneOffset.UTC),
    createdAtEpoch = createdAt.toEpochSecond(java.time.ZoneOffset.UTC) * 1000
)

@Entity(tableName = "planner_events")
data class PlannerEventEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String = "",
    val dateEpoch: Long,
    val startTimeSecond: Int? = null,
    val endTimeSecond: Int? = null,
    val color: Int = 0xFF6200EE.toInt(),
    val isAllDay: Boolean = false,
    val createdAtEpoch: Long = System.currentTimeMillis()
) {
    fun toDomain() = PlannerEvent(
        id = id,
        title = title,
        description = description,
        date = LocalDate.ofEpochDay(dateEpoch),
        startTime = startTimeSecond?.let { LocalTime.ofSecondOfDay(it.toLong()) },
        endTime = endTimeSecond?.let { LocalTime.ofSecondOfDay(it.toLong()) },
        color = color,
        isAllDay = isAllDay
    )
}

fun PlannerEvent.toEntity() = PlannerEventEntity(
    id = id,
    title = title,
    description = description,
    dateEpoch = date.toEpochDay(),
    startTimeSecond = startTime?.toSecondOfDay(),
    endTimeSecond = endTime?.toSecondOfDay(),
    color = color,
    isAllDay = isAllDay
)

@Entity(tableName = "subject_schedules")
data class SubjectScheduleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val subjectName: String,
    val teacherName: String = "",
    val room: String = "",
    val dayOfWeek: String,
    val startTimeSecond: Int,
    val endTimeSecond: Int,
    val color: Int = 0xFF6200EE.toInt()
) {
    fun toDomain() = SubjectSchedule(
        id = id,
        subjectName = subjectName,
        teacherName = teacherName,
        room = room,
        dayOfWeek = DayOfWeek.valueOf(dayOfWeek),
        startTime = LocalTime.ofSecondOfDay(startTimeSecond.toLong()),
        endTime = LocalTime.ofSecondOfDay(endTimeSecond.toLong()),
        color = color
    )
}

fun SubjectSchedule.toEntity() = SubjectScheduleEntity(
    id = id,
    subjectName = subjectName,
    teacherName = teacherName,
    room = room,
    dayOfWeek = dayOfWeek.name,
    startTimeSecond = startTime.toSecondOfDay(),
    endTimeSecond = endTime.toSecondOfDay(),
    color = color
)

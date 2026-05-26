package com.myplanner.app.domain.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

enum class Priority { LOW, MEDIUM, HIGH }
enum class TaskStatus { PENDING, IN_PROGRESS, DONE }
enum class DayOfWeek { SENIN, SELASA, RABU, KAMIS, JUMAT, SABTU, MINGGU }

data class Task(
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val subject: String = "",
    val dueDate: LocalDateTime? = null,
    val priority: Priority = Priority.MEDIUM,
    val status: TaskStatus = TaskStatus.PENDING,
    val hasReminder: Boolean = false,
    val reminderTime: LocalDateTime? = null,
    val createdAt: LocalDateTime = LocalDateTime.now()
)

data class PlannerEvent(
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val date: LocalDate,
    val startTime: LocalTime? = null,
    val endTime: LocalTime? = null,
    val color: Int = 0xFF6200EE.toInt(),
    val isAllDay: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now()
)

data class SubjectSchedule(
    val id: Long = 0,
    val subjectName: String,
    val teacherName: String = "",
    val room: String = "",
    val dayOfWeek: DayOfWeek,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val color: Int = 0xFF6200EE.toInt()
)

package com.myplanner.app.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myplanner.app.data.repository.MyPlannerRepository
import com.myplanner.app.domain.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

// ─── Task ViewModel ──────────────────────────────────────────────────────────
@HiltViewModel
class TaskViewModel @Inject constructor(private val repo: MyPlannerRepository) : ViewModel() {
    val tasks: StateFlow<List<Task>> = repo.getAllTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val pendingTasks: StateFlow<List<Task>> = repo.getPendingTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addTask(task: Task) = viewModelScope.launch { repo.insertTask(task) }
    fun updateTask(task: Task) = viewModelScope.launch { repo.updateTask(task) }
    fun deleteTask(task: Task) = viewModelScope.launch { repo.deleteTask(task) }
    fun toggleDone(task: Task) = viewModelScope.launch {
        val newStatus = if (task.status == TaskStatus.DONE) TaskStatus.PENDING else TaskStatus.DONE
        repo.updateTask(task.copy(status = newStatus))
    }
}

// ─── Planner ViewModel ───────────────────────────────────────────────────────
@HiltViewModel
class PlannerViewModel @Inject constructor(private val repo: MyPlannerRepository) : ViewModel() {
    val events: StateFlow<List<PlannerEvent>> = repo.getAllEvents()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    val eventsForSelectedDate: StateFlow<List<PlannerEvent>> = combine(_selectedDate, events) { date, all ->
        all.filter { it.date == date }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun selectDate(date: LocalDate) { _selectedDate.value = date }
    fun addEvent(event: PlannerEvent) = viewModelScope.launch { repo.insertEvent(event) }
    fun updateEvent(event: PlannerEvent) = viewModelScope.launch { repo.updateEvent(event) }
    fun deleteEvent(event: PlannerEvent) = viewModelScope.launch { repo.deleteEvent(event) }
}

// ─── Schedule ViewModel ──────────────────────────────────────────────────────
@HiltViewModel
class ScheduleViewModel @Inject constructor(private val repo: MyPlannerRepository) : ViewModel() {
    val schedules: StateFlow<List<SubjectSchedule>> = repo.getAllSchedules()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedDay = MutableStateFlow(getTodayDayOfWeek())
    val selectedDay: StateFlow<DayOfWeek> = _selectedDay.asStateFlow()

    val schedulesForDay: StateFlow<List<SubjectSchedule>> = combine(_selectedDay, schedules) { day, all ->
        all.filter { it.dayOfWeek == day }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun selectDay(day: DayOfWeek) { _selectedDay.value = day }
    fun addSchedule(s: SubjectSchedule) = viewModelScope.launch { repo.insertSchedule(s) }
    fun updateSchedule(s: SubjectSchedule) = viewModelScope.launch { repo.updateSchedule(s) }
    fun deleteSchedule(s: SubjectSchedule) = viewModelScope.launch { repo.deleteSchedule(s) }

    private fun getTodayDayOfWeek(): DayOfWeek {
        val javaDay = java.time.LocalDate.now().dayOfWeek
        return when (javaDay) {
            java.time.DayOfWeek.MONDAY    -> DayOfWeek.SENIN
            java.time.DayOfWeek.TUESDAY   -> DayOfWeek.SELASA
            java.time.DayOfWeek.WEDNESDAY -> DayOfWeek.RABU
            java.time.DayOfWeek.THURSDAY  -> DayOfWeek.KAMIS
            java.time.DayOfWeek.FRIDAY    -> DayOfWeek.JUMAT
            java.time.DayOfWeek.SATURDAY  -> DayOfWeek.SABTU
            java.time.DayOfWeek.SUNDAY    -> DayOfWeek.MINGGU
        }
    }
}

// ─── Export/Import ViewModel ─────────────────────────────────────────────────
@HiltViewModel
class ExportImportViewModel @Inject constructor(private val repo: MyPlannerRepository) : ViewModel() {
    private val _tasks = repo.getAllTasks().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    private val _events = repo.getAllEvents().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    private val _schedules = repo.getAllSchedules().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val _exportResult = MutableStateFlow<String?>(null)
    val exportResult: StateFlow<String?> = _exportResult.asStateFlow()

    private val _importResult = MutableStateFlow<String?>(null)
    val importResult: StateFlow<String?> = _importResult.asStateFlow()

    fun exportData() = viewModelScope.launch {
        val json = repo.exportToJson(_tasks.value, _events.value, _schedules.value)
        _exportResult.value = json
    }

    fun importData(json: String) = viewModelScope.launch {
        val result = repo.importFromJson(json)
        _importResult.value = if (result.isSuccess) {
            val s = result.getOrNull()!!
            "Berhasil import: ${s.tasks} tugas, ${s.events} kegiatan, ${s.schedules} jadwal"
        } else {
            "Gagal import: ${result.exceptionOrNull()?.message}"
        }
    }

    fun clearResults() { _exportResult.value = null; _importResult.value = null }
}

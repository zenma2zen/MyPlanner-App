package com.myplanner.app.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.myplanner.app.domain.model.*
import com.myplanner.app.ui.*
import com.myplanner.app.ui.components.*
import com.myplanner.app.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun HomeScreen(
    taskViewModel: TaskViewModel = hiltViewModel(),
    plannerViewModel: PlannerViewModel = hiltViewModel(),
    scheduleViewModel: ScheduleViewModel = hiltViewModel(),
    onNavigateToTasks: () -> Unit,
    onNavigateToPlanner: () -> Unit,
    onNavigateToSchedule: () -> Unit
) {
    val tasks by taskViewModel.pendingTasks.collectAsState()
    val todayEvents by plannerViewModel.eventsForSelectedDate.collectAsState()
    val todaySchedules by scheduleViewModel.schedulesForDay.collectAsState()

    val today = LocalDate.now()
    val dateFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale("id"))

    LaunchedEffect(Unit) {
        plannerViewModel.selectDate(today)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ── Header ──
        item {
            Column {
                Text("MyPlanner ✨", style = MaterialTheme.typography.headlineMedium,
                    color = NeonBlue, fontWeight = FontWeight.Bold)
                Text(today.format(dateFormatter), style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF94A3B8))
            }
        }

        // ── Stats Row ──
        item {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard(Modifier.weight(1f), tasks.size.toString(), "Tugas Aktif", Icons.Rounded.Assignment, NeonBlue)
                StatCard(Modifier.weight(1f), todayEvents.size.toString(), "Kegiatan", Icons.Rounded.Event, NeonPurple)
                StatCard(Modifier.weight(1f), todaySchedules.size.toString(), "Mapel", Icons.Rounded.School, Color(0xFFFF6B9D))
            }
        }

        // ── Jadwal Hari Ini ──
        item {
            SectionHeader("Jadwal Hari Ini", Icons.Rounded.School) {
                TextButton(onClick = onNavigateToSchedule) {
                    Text("Lihat semua", color = NeonBlue, fontSize = 12.sp)
                }
            }
        }

        if (todaySchedules.isEmpty()) {
            item {
                EmptyStateSmall("Tidak ada jadwal hari ini")
            }
        } else {
            items(todaySchedules.take(3)) { s ->
                SubjectScheduleCard(s, onDelete = { scheduleViewModel.deleteSchedule(s) })
            }
        }

        // ── Kegiatan Hari Ini ──
        item {
            SectionHeader("Kegiatan Hari Ini", Icons.Rounded.CalendarToday) {
                TextButton(onClick = onNavigateToPlanner) {
                    Text("Lihat semua", color = NeonBlue, fontSize = 12.sp)
                }
            }
        }

        if (todayEvents.isEmpty()) {
            item { EmptyStateSmall("Tidak ada kegiatan hari ini") }
        } else {
            items(todayEvents.take(3)) { event ->
                EventChip(event)
            }
        }

        // ── Tugas Pending ──
        item {
            SectionHeader("Tugas Belum Selesai", Icons.Rounded.Assignment) {
                TextButton(onClick = onNavigateToTasks) {
                    Text("Lihat semua", color = NeonBlue, fontSize = 12.sp)
                }
            }
        }

        if (tasks.isEmpty()) {
            item { EmptyStateSmall("Semua tugas sudah selesai! 🎉") }
        } else {
            items(tasks.take(5)) { task ->
                TaskCard(
                    task = task,
                    onToggle = { taskViewModel.toggleDone(task) },
                    onClick = {},
                    onDelete = { taskViewModel.deleteTask(task) }
                )
            }
        }

        item { Spacer(Modifier.height(80.dp)) }
    }
}

@Composable
fun StatCard(modifier: Modifier, value: String, label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color) {
    GlassCard(modifier = modifier) {
        Icon(icon, null, tint = color, modifier = Modifier.size(24.dp))
        Spacer(Modifier.height(8.dp))
        Text(value, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = color)
        Text(label, fontSize = 11.sp, color = Color(0xFF94A3B8))
    }
}

@Composable
fun EventChip(event: PlannerEvent) {
    val color = Color(event.color)
    Row(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp))
            .background(color.copy(0.1f))
            .border(1.dp, color.copy(0.3f), RoundedCornerShape(14.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.width(4.dp).height(40.dp).clip(RoundedCornerShape(2.dp)).background(color))
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(event.title, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
            if (event.startTime != null) {
                Text("${event.startTime} - ${event.endTime ?: "?"}", fontSize = 11.sp, color = color)
            }
        }
    }
}

@Composable
fun EmptyStateSmall(message: String) {
    Box(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp))
            .background(Color(0x0DFFFFFF)).padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(message, color = Color(0xFF64748B), fontSize = 13.sp)
    }
}

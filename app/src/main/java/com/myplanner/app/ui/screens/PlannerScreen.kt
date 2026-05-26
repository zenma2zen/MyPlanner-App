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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.myplanner.app.domain.model.PlannerEvent
import com.myplanner.app.ui.PlannerViewModel
import com.myplanner.app.ui.components.*
import com.myplanner.app.ui.theme.*
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun PlannerScreen(viewModel: PlannerViewModel = hiltViewModel()) {
    val events by viewModel.events.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val selectedEvents by viewModel.eventsForSelectedDate.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = NeonPurple, contentColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            ) { Icon(Icons.Rounded.Add, "Tambah Kegiatan") }
        }
    ) { padding ->
        LazyColumn(
            Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text("📅 Planner", style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)
            }

            item {
                CalendarView(
                    currentMonth = currentMonth,
                    selectedDate = selectedDate,
                    eventsDateSet = events.map { it.date }.toSet(),
                    onDateSelect = { viewModel.selectDate(it) },
                    onMonthChange = { currentMonth = it }
                )
            }

            item {
                val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale("id"))
                SectionHeader("${selectedDate.format(formatter)}", Icons.Rounded.Event)
            }

            if (selectedEvents.isEmpty()) {
                item { EmptyStateSmall("Tidak ada kegiatan di tanggal ini") }
            } else {
                items(selectedEvents) { event ->
                    EventCard(event, onDelete = { viewModel.deleteEvent(event) })
                }
            }
            item { Spacer(Modifier.height(80.dp)) }
        }
    }

    if (showAddDialog) {
        AddEventDialog(
            defaultDate = selectedDate,
            onDismiss = { showAddDialog = false },
            onAdd = { viewModel.addEvent(it); showAddDialog = false }
        )
    }
}

@Composable
fun CalendarView(
    currentMonth: YearMonth,
    selectedDate: LocalDate,
    eventsDateSet: Set<LocalDate>,
    onDateSelect: (LocalDate) -> Unit,
    onMonthChange: (YearMonth) -> Unit
) {
    val monthFormatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale("id"))
    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayOffset = (currentMonth.atDay(1).dayOfWeek.value % 7)

    GlassCard {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { onMonthChange(currentMonth.minusMonths(1)) }) {
                Icon(Icons.Rounded.ChevronLeft, null, tint = NeonBlue)
            }
            Text(currentMonth.format(monthFormatter), Modifier.weight(1f),
                textAlign = TextAlign.Center, style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface)
            IconButton(onClick = { onMonthChange(currentMonth.plusMonths(1)) }) {
                Icon(Icons.Rounded.ChevronRight, null, tint = NeonBlue)
            }
        }

        Spacer(Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth()) {
            listOf("M", "S", "S", "R", "K", "J", "S").forEach { day ->
                Text(day, Modifier.weight(1f), textAlign = TextAlign.Center,
                    fontSize = 12.sp, color = Color(0xFF64748B), fontWeight = FontWeight.SemiBold)
            }
        }
        Spacer(Modifier.height(4.dp))

        val totalCells = firstDayOffset + daysInMonth
        val rows = (totalCells + 6) / 7
        for (row in 0 until rows) {
            Row(Modifier.fillMaxWidth()) {
                for (col in 0 until 7) {
                    val dayIndex = row * 7 + col - firstDayOffset + 1
                    if (dayIndex < 1 || dayIndex > daysInMonth) {
                        Box(Modifier.weight(1f).aspectRatio(1f))
                    } else {
                        val date = currentMonth.atDay(dayIndex)
                        val isSelected = date == selectedDate
                        val isToday = date == LocalDate.now()
                        val hasEvent = eventsDateSet.contains(date)
                        Box(
                            Modifier.weight(1f).aspectRatio(1f).padding(2.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    when {
                                        isSelected -> NeonBlue.copy(0.3f)
                                        isToday    -> NeonPurple.copy(0.2f)
                                        else       -> Color.Transparent
                                    }
                                )
                                .border(
                                    if (isSelected) 1.5.dp else if (isToday) 1.dp else 0.dp,
                                    if (isSelected) NeonBlue else NeonPurple,
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable { onDateSelect(date) },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("$dayIndex",
                                    color = if (isSelected) NeonBlue else if (isToday) NeonPurple else MaterialTheme.colorScheme.onSurface,
                                    fontSize = 13.sp, fontWeight = if (isSelected || isToday) FontWeight.Bold else FontWeight.Normal)
                                if (hasEvent) {
                                    Box(Modifier.size(4.dp).clip(RoundedCornerShape(2.dp)).background(NeonBlue))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EventCard(event: PlannerEvent, onDelete: () -> Unit) {
    val color = Color(event.color)
    GlassCard(Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.width(4.dp).height(50.dp).clip(RoundedCornerShape(2.dp)).background(color))
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(event.title, style = MaterialTheme.typography.titleSmall)
                if (event.description.isNotEmpty())
                    Text(event.description, fontSize = 12.sp, color = Color(0xFF94A3B8))
                if (!event.isAllDay && event.startTime != null)
                    Text("🕐 ${event.startTime} - ${event.endTime ?: "?"}", fontSize = 11.sp, color = color)
                else if (event.isAllDay)
                    Text("📅 Sepanjang hari", fontSize = 11.sp, color = color)
            }
            IconButton(onClick = onDelete, Modifier.size(36.dp)) {
                Icon(Icons.Rounded.DeleteOutline, null, tint = Color(0xFF64748B), modifier = Modifier.size(18.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventDialog(defaultDate: LocalDate, onDismiss: () -> Unit, onAdd: (PlannerEvent) -> Unit) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var dateText by remember { mutableStateOf(defaultDate.toString()) }
    var startTimeText by remember { mutableStateOf("") }
    var endTimeText by remember { mutableStateOf("") }
    var isAllDay by remember { mutableStateOf(false) }
    var selectedColorIdx by remember { mutableStateOf(0) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(shape = RoundedCornerShape(24.dp), color = MaterialTheme.colorScheme.surfaceVariant, tonalElevation = 8.dp) {
            Column(Modifier.padding(24.dp).verticalScroll(rememberScrollState())) {
                Text("Tambah Kegiatan", style = MaterialTheme.typography.titleLarge,
                    color = NeonPurple, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(16.dp))

                OutlinedTextField(value = title, onValueChange = { title = it },
                    label = { Text("Judul Kegiatan *") }, modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = NeonPurple))
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(value = description, onValueChange = { description = it },
                    label = { Text("Deskripsi") }, modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = NeonPurple))
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(value = dateText, onValueChange = { dateText = it },
                    label = { Text("Tanggal (yyyy-MM-dd)") }, modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = NeonPurple))
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(checked = isAllDay, onCheckedChange = { isAllDay = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = NeonPurple, checkedTrackColor = NeonPurple.copy(0.3f)))
                    Spacer(Modifier.width(8.dp))
                    Text("Sepanjang Hari")
                }
                if (!isAllDay) {
                    Spacer(Modifier.height(8.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(value = startTimeText, onValueChange = { startTimeText = it },
                            label = { Text("Mulai (HH:mm)") }, modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = NeonPurple))
                        OutlinedTextField(value = endTimeText, onValueChange = { endTimeText = it },
                            label = { Text("Selesai (HH:mm)") }, modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = NeonPurple))
                    }
                }
                Spacer(Modifier.height(12.dp))
                Text("Warna", style = MaterialTheme.typography.labelLarge, color = Color(0xFF94A3B8))
                Spacer(Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SubjectColors.forEachIndexed { i, color ->
                        Box(
                            Modifier.size(28.dp).clip(RoundedCornerShape(8.dp)).background(color)
                                .border(if (selectedColorIdx == i) 2.dp else 0.dp, Color.White, RoundedCornerShape(8.dp))
                                .clickable { selectedColorIdx = i }
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) { Text("Batal") }
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (title.isNotBlank()) {
                                val date = try { LocalDate.parse(dateText) } catch (e: Exception) { defaultDate }
                                val start = if (!isAllDay && startTimeText.isNotBlank()) try { LocalTime.parse(startTimeText) } catch (e: Exception) { null } else null
                                val end   = if (!isAllDay && endTimeText.isNotBlank())   try { LocalTime.parse(endTimeText)   } catch (e: Exception) { null } else null
                                onAdd(PlannerEvent(title = title, description = description, date = date,
                                    startTime = start, endTime = end, isAllDay = isAllDay,
                                    color = SubjectColors[selectedColorIdx].value.toInt()))
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = NeonPurple),
                        shape = RoundedCornerShape(12.dp)
                    ) { Text("Simpan", fontWeight = FontWeight.Bold) }
                }
            }
        }
    }
}

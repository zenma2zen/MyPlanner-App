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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.myplanner.app.domain.model.*
import com.myplanner.app.ui.ScheduleViewModel
import com.myplanner.app.ui.components.*
import com.myplanner.app.ui.theme.*
import java.time.LocalTime

@Composable
fun ScheduleScreen(viewModel: ScheduleViewModel = hiltViewModel()) {
    val schedules by viewModel.schedules.collectAsState()
    val selectedDay by viewModel.selectedDay.collectAsState()
    val daySchedules by viewModel.schedulesForDay.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = Color(0xFFFF6B9D), contentColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            ) { Icon(Icons.Rounded.Add, "Tambah Jadwal") }
        }
    ) { padding ->
        LazyColumn(
            Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Text("🏫 Jadwal Mapel", style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(12.dp))

                // Day selector
                val days = listOf(
                    DayOfWeek.SENIN to "Sen", DayOfWeek.SELASA to "Sel", DayOfWeek.RABU to "Rab",
                    DayOfWeek.KAMIS to "Kam", DayOfWeek.JUMAT to "Jum", DayOfWeek.SABTU to "Sab",
                    DayOfWeek.MINGGU to "Min"
                )
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    days.forEach { (day, label) ->
                        val isSelected = selectedDay == day
                        Box(
                            Modifier.weight(1f).clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) Color(0xFFFF6B9D).copy(0.3f) else Color(0x0DFFFFFF))
                                .border(if (isSelected) 1.dp else 0.dp, Color(0xFFFF6B9D), RoundedCornerShape(10.dp))
                                .clickable { viewModel.selectDay(day) }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(label, fontSize = 11.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Color(0xFFFF6B9D) else Color(0xFF94A3B8))
                        }
                    }
                }
            }

            item { NeonDivider() }

            if (daySchedules.isEmpty()) {
                item {
                    Box(Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("📚", fontSize = 48.sp)
                            Spacer(Modifier.height(8.dp))
                            Text("Tidak ada jadwal hari ini", color = Color(0xFF64748B))
                        }
                    }
                }
            } else {
                items(daySchedules, key = { it.id }) { schedule ->
                    SubjectScheduleCard(schedule, onDelete = { viewModel.deleteSchedule(schedule) })
                }
            }

            // Weekly overview
            item {
                Spacer(Modifier.height(8.dp))
                SectionHeader("Semua Mapel", Icons.Rounded.GridView)
            }
            val uniqueSubjects = schedules.map { it.subjectName }.distinct()
            item {
                if (uniqueSubjects.isEmpty()) {
                    EmptyStateSmall("Belum ada mata pelajaran")
                } else {
                    Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        uniqueSubjects.forEachIndexed { i, name ->
                            val color = SubjectColors[i % SubjectColors.size]
                            Box(
                                Modifier.clip(RoundedCornerShape(12.dp))
                                    .background(color.copy(0.15f))
                                    .border(1.dp, color.copy(0.4f), RoundedCornerShape(12.dp))
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            ) { Text(name, color = color, fontSize = 12.sp, fontWeight = FontWeight.SemiBold) }
                        }
                    }
                }
            }
            item { Spacer(Modifier.height(80.dp)) }
        }
    }

    if (showAddDialog) {
        AddScheduleDialog(
            defaultDay = selectedDay,
            onDismiss = { showAddDialog = false },
            onAdd = { viewModel.addSchedule(it); showAddDialog = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScheduleDialog(defaultDay: DayOfWeek, onDismiss: () -> Unit, onAdd: (SubjectSchedule) -> Unit) {
    var subjectName by remember { mutableStateOf("") }
    var teacherName by remember { mutableStateOf("") }
    var room by remember { mutableStateOf("") }
    var selectedDay by remember { mutableStateOf(defaultDay) }
    var startTimeText by remember { mutableStateOf("07:00") }
    var endTimeText by remember { mutableStateOf("08:00") }
    var selectedColorIdx by remember { mutableStateOf(0) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(shape = RoundedCornerShape(24.dp), color = MaterialTheme.colorScheme.surfaceVariant, tonalElevation = 8.dp) {
            Column(Modifier.padding(24.dp).verticalScroll(rememberScrollState())) {
                Text("Tambah Jadwal", style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFFFF6B9D), fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(16.dp))

                OutlinedTextField(value = subjectName, onValueChange = { subjectName = it },
                    label = { Text("Mata Pelajaran *") }, modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFFFF6B9D)))
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(value = teacherName, onValueChange = { teacherName = it },
                    label = { Text("Nama Guru") }, modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFFFF6B9D)))
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(value = room, onValueChange = { room = it },
                    label = { Text("Ruang Kelas") }, modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFFFF6B9D)))

                Spacer(Modifier.height(12.dp))
                Text("Hari", style = MaterialTheme.typography.labelLarge, color = Color(0xFF94A3B8))
                Spacer(Modifier.height(6.dp))
                Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    DayOfWeek.values().forEach { day ->
                        val label = day.name.take(3).lowercase().replaceFirstChar { it.uppercase() }
                        FilterChip(selected = selectedDay == day, onClick = { selectedDay = day },
                            label = { Text(label, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFFFF6B9D).copy(0.2f),
                                selectedLabelColor = Color(0xFFFF6B9D)
                            ))
                    }
                }

                Spacer(Modifier.height(10.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = startTimeText, onValueChange = { startTimeText = it },
                        label = { Text("Mulai (HH:mm)") }, modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFFFF6B9D)))
                    OutlinedTextField(value = endTimeText, onValueChange = { endTimeText = it },
                        label = { Text("Selesai (HH:mm)") }, modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFFFF6B9D)))
                }

                Spacer(Modifier.height(12.dp))
                Text("Warna", style = MaterialTheme.typography.labelLarge, color = Color(0xFF94A3B8))
                Spacer(Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SubjectColors.forEachIndexed { i, color ->
                        Box(Modifier.size(28.dp).clip(RoundedCornerShape(8.dp)).background(color)
                            .border(if (selectedColorIdx == i) 2.dp else 0.dp, Color.White, RoundedCornerShape(8.dp))
                            .clickable { selectedColorIdx = i })
                    }
                }

                Spacer(Modifier.height(20.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) { Text("Batal") }
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (subjectName.isNotBlank()) {
                                val start = try { LocalTime.parse(startTimeText) } catch (e: Exception) { LocalTime.of(7,0) }
                                val end   = try { LocalTime.parse(endTimeText)   } catch (e: Exception) { LocalTime.of(8,0) }
                                onAdd(SubjectSchedule(subjectName = subjectName, teacherName = teacherName,
                                    room = room, dayOfWeek = selectedDay, startTime = start, endTime = end,
                                    color = SubjectColors[selectedColorIdx].value.toInt()))
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6B9D)),
                        shape = RoundedCornerShape(12.dp)
                    ) { Text("Simpan", fontWeight = FontWeight.Bold) }
                }
            }
        }
    }
}

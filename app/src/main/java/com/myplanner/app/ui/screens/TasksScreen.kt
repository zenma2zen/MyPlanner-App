package com.myplanner.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.myplanner.app.domain.model.*
import com.myplanner.app.ui.TaskViewModel
import com.myplanner.app.ui.components.*
import com.myplanner.app.ui.theme.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(viewModel: TaskViewModel = hiltViewModel()) {
    val tasks by viewModel.tasks.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var filterStatus by remember { mutableStateOf<TaskStatus?>(null) }

    val filtered = if (filterStatus == null) tasks else tasks.filter { it.status == filterStatus }

    Scaffold(
        containerColor = DeepSpace,
        floatingActionButton = {
            val scale by animateFloatAsState(targetValue = 1f)
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = NeonBlue,
                contentColor = DeepSpace,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.graphicsLayer { scaleX = scale; scaleY = scale }
            ) { Icon(Icons.Rounded.Add, "Tambah Tugas", modifier = Modifier.size(30.dp)) }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    "📋 Tugas", 
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White, 
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                // Kinetic Filter Row
                Row(
                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    listOf(null to "Semua", TaskStatus.PENDING to "Belum",
                        TaskStatus.IN_PROGRESS to "Dikerjakan", TaskStatus.DONE to "Selesai")
                        .forEach { (status, label) ->
                            val isSelected = filterStatus == status
                            val chipScale by animateFloatAsState(if (isSelected) 1.05f else 1f)
                            
                            Box(
                                modifier = Modifier
                                    .graphicsLayer { scaleX = chipScale; scaleY = chipScale }
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (isSelected) NeonBlue else SurfaceDark)
                                    .clickable { filterStatus = status }
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    label, 
                                    fontSize = 13.sp, 
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) DeepSpace else Color.White
                                )
                            }
                        }
                }
                Spacer(Modifier.height(16.dp))
            }

            if (filtered.isEmpty()) {
                item {
                    Box(Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Rounded.TaskAlt, null, tint = Color(0xFF1E293B), modifier = Modifier.size(80.dp))
                            Spacer(Modifier.height(16.dp))
                            Text("Belum ada tugas", color = Color(0xFF64748B), fontWeight = FontWeight.Medium)
                        }
                    }
                }
            } else {
                items(filtered, key = { it.id }) { task ->
                    AnimatedVisibility(
                        visible = true, 
                        enter = fadeIn() + expandVertically() + slideInVertically { it / 2 }
                    ) {
                        TaskCard(
                            task = task,
                            onToggle = { viewModel.toggleDone(task) },
                            onClick = {},
                            onDelete = { viewModel.deleteTask(task) }
                        )
                    }
                }
            }
            item { Spacer(Modifier.height(100.dp)) }
        }
    }

    if (showAddDialog) {
        AddTaskDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { task -> viewModel.addTask(task); showAddDialog = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskDialog(onDismiss: () -> Unit, onAdd: (Task) -> Unit) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf(Priority.MEDIUM) }
    
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    
    var hasReminder by remember { mutableStateOf(true) }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        selectedDate = java.time.Instant.ofEpochMilli(it).atZone(java.time.ZoneId.systemDefault()).toLocalDate()
                    }
                    showDatePicker = false
                }) { Text("OK") }
            }
        ) { DatePicker(state = datePickerState) }
    }

    if (showTimePicker) {
        val timePickerState = rememberTimePickerState(selectedTime.hour, selectedTime.minute)
        Dialog(onDismissRequest = { showTimePicker = false }) {
            Surface(shape = RoundedCornerShape(24.dp), color = SurfaceDark) {
                Column(Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    TimePicker(state = timePickerState)
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = { showTimePicker = false }) { Text("Batal") }
                        TextButton(onClick = {
                            selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
                            showTimePicker = false
                        }) { Text("OK") }
                    }
                }
            }
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        NeuBox(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
            backgroundColor = SurfaceDark,
            elevation = 12.dp
        ) {
            Column(Modifier.padding(24.dp).verticalScroll(rememberScrollState())) {
                Text("Tambah Tugas", style = MaterialTheme.typography.headlineSmall,
                    color = NeonBlue, fontWeight = FontWeight.ExtraBold)
                Spacer(Modifier.height(20.dp))

                TextField(
                    value = title, onValueChange = { title = it },
                    label = { Text("Judul Tugas", color = Color.Gray) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = DeepSpace,
                        unfocusedContainerColor = DeepSpace,
                        focusedIndicatorColor = NeonBlue,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(Modifier.height(12.dp))
                TextField(
                    value = subject, onValueChange = { subject = it },
                    label = { Text("Mata Pelajaran", color = Color.Gray) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = DeepSpace,
                        unfocusedContainerColor = DeepSpace,
                        focusedIndicatorColor = NeonBlue,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(Modifier.height(16.dp))
                Text("Prioritas", style = MaterialTheme.typography.labelLarge, color = Color.White)
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Priority.values().forEach { p ->
                        val (color, label) = when (p) {
                            Priority.LOW    -> PriorityLow  to "Low"
                            Priority.MEDIUM -> PriorityMed  to "Med"
                            Priority.HIGH   -> PriorityHigh to "High"
                        }
                        val isSelected = priority == p
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) color else DeepSpace)
                                .clickable { priority = p }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(label, color = if (isSelected) DeepSpace else color, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))
                Text("Tenggat Waktu", style = MaterialTheme.typography.labelLarge, color = Color.White)
                Spacer(Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { showDatePicker = true },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = DeepSpace),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Rounded.CalendarToday, null, tint = NeonBlue, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(selectedDate.format(DateTimeFormatter.ofPattern("dd/MM")), color = Color.White)
                    }
                    Button(
                        onClick = { showTimePicker = true },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = DeepSpace),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Rounded.Schedule, null, tint = NeonBlue, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(selectedTime.format(DateTimeFormatter.ofPattern("HH:mm")), color = Color.White)
                    }
                }

                Spacer(Modifier.height(24.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) { Text("Batal", color = Color.Gray) }
                    Spacer(Modifier.width(12.dp))
                    Button(
                        onClick = {
                            if (title.isNotBlank()) {
                                val due = LocalDateTime.of(selectedDate, selectedTime)
                                onAdd(Task(title = title, description = description, subject = subject,
                                    priority = priority, dueDate = due, hasReminder = hasReminder,
                                    reminderTime = if (hasReminder) due.minusMinutes(30) else null))
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = NeonBlue),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.height(48.dp)
                    ) { Text("SIMPAN", color = DeepSpace, fontWeight = FontWeight.ExtraBold) }
                }
            }
        }
    }
}

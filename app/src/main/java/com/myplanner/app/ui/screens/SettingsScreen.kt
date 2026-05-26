package com.myplanner.app.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.myplanner.app.ui.ExportImportViewModel
import com.myplanner.app.ui.components.GlassCard
import com.myplanner.app.ui.components.NeonDivider
import com.myplanner.app.ui.theme.*

@Composable
fun SettingsScreen(viewModel: ExportImportViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val exportResult by viewModel.exportResult.collectAsState()
    val importResult by viewModel.importResult.collectAsState()
    var showExportSheet by remember { mutableStateOf(false) }

    val importLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            val json = context.contentResolver.openInputStream(it)?.bufferedReader()?.readText() ?: return@let
            viewModel.importData(json)
        }
    }

    val saveLauncher = rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("application/json")) { uri: Uri? ->
        uri?.let { destUri ->
            exportResult?.let { json ->
                context.contentResolver.openOutputStream(destUri)?.use { it.write(json.toByteArray()) }
                viewModel.clearResults()
            }
        }
    }

    LaunchedEffect(exportResult) {
        if (exportResult != null) {
            saveLauncher.launch("myplanner_backup_${System.currentTimeMillis()}.json")
        }
    }

    Column(
        Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("⚙️ Pengaturan", style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)

        // Export/Import section
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Text("Data & Backup", style = MaterialTheme.typography.titleMedium, color = NeonBlue, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(4.dp))
            Text("Ekspor atau impor data kamu dalam format JSON", fontSize = 12.sp, color = Color(0xFF94A3B8))
            Spacer(Modifier.height(16.dp))

            SettingsActionItem(
                icon = Icons.Rounded.Upload,
                title = "Ekspor Data",
                subtitle = "Simpan semua data ke file JSON",
                color = NeonBlue,
                onClick = { viewModel.exportData() }
            )
            NeonDivider()
            SettingsActionItem(
                icon = Icons.Rounded.Download,
                title = "Impor Data",
                subtitle = "Muat data dari file JSON",
                color = NeonPurple,
                onClick = { importLauncher.launch("application/json") }
            )
        }

        // Import result snackbar-style
        if (importResult != null) {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Rounded.CheckCircle, null, tint = PriorityLow, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(importResult!!, fontSize = 13.sp, color = PriorityLow, modifier = Modifier.weight(1f))
                    IconButton(onClick = { viewModel.clearResults() }, Modifier.size(28.dp)) {
                        Icon(Icons.Rounded.Close, null, tint = Color(0xFF64748B), modifier = Modifier.size(16.dp))
                    }
                }
            }
        }

        // App Info
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Text("Tentang Aplikasi", style = MaterialTheme.typography.titleMedium,
                color = Color(0xFFFF6B9D), fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(12.dp))
            InfoRow("Nama Aplikasi", "MyPlanner")
            InfoRow("Versi", "1.0.0")
            InfoRow("Target", "Pelajar SMP / SMA / Mahasiswa")
            InfoRow("Platform", "Android 11+")
        }

        Spacer(Modifier.height(80.dp))
    }
}

@Composable
fun SettingsActionItem(icon: ImageVector, title: String, subtitle: String, color: Color, onClick: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().clickable(onClick = onClick).padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier.size(40.dp).clip(RoundedCornerShape(12.dp)).background(color.copy(0.15f)),
            contentAlignment = Alignment.Center
        ) { Icon(icon, null, tint = color, modifier = Modifier.size(20.dp)) }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
            Text(subtitle, fontSize = 11.sp, color = Color(0xFF94A3B8))
        }
        Icon(Icons.Rounded.ChevronRight, null, tint = Color(0xFF64748B), modifier = Modifier.size(20.dp))
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(label, fontSize = 13.sp, color = Color(0xFF94A3B8), modifier = Modifier.weight(1f))
        Text(value, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)
    }
}

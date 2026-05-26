package com.myplanner.app.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.*
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.*
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.*
import androidx.glance.text.*
import androidx.glance.unit.ColorProvider
import com.myplanner.app.MainActivity
import com.myplanner.app.data.local.MyPlannerDatabase
import kotlinx.coroutines.flow.first
import androidx.room.Room
import com.myplanner.app.domain.model.TaskStatus

private fun createColorProvider(color: Color): ColorProvider = object : ColorProvider {
    override fun getColor(context: Context): Color = color
}

class TaskWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = TaskWidget()
}

class TaskWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val db = Room.databaseBuilder(context, MyPlannerDatabase::class.java, "myplanner.db")
            .fallbackToDestructiveMigration().build()
        val tasks = db.taskDao().getPendingTasks().first().take(5)
        db.close()

        provideContent {
            TaskWidgetContent(tasks.map { it.toDomain() })
        }
    }
}

@Composable
fun TaskWidgetContent(tasks: List<com.myplanner.app.domain.model.Task>) {
    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(createColorProvider(Color(0xFF0D1117)))
            .padding(16.dp)
            .appWidgetBackground()
            .cornerRadius(28.dp)
            .clickable(actionStartActivity<MainActivity>()),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            "📋 Tugas Aktif",
            style = TextStyle(color = createColorProvider(Color(0xFF00C8FF)), fontSize = 15.sp, fontWeight = FontWeight.Bold)
        )
        Spacer(GlanceModifier.height(12.dp))

        if (tasks.isEmpty()) {
            Text("Semua tugas selesai! 🎉",
                style = TextStyle(color = createColorProvider(Color(0xFF64748B)), fontSize = 13.sp))
        } else {
            tasks.forEach { task ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = GlanceModifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .background(createColorProvider(Color(0xFF161E2E)))
                        .cornerRadius(12.dp)
                        .padding(8.dp)
                ) {
                    Box(
                        GlanceModifier.size(8.dp).background(
                            createColorProvider(
                                when (task.priority) {
                                    com.myplanner.app.domain.model.Priority.HIGH -> Color(0xFFFF4757)
                                    com.myplanner.app.domain.model.Priority.MEDIUM -> Color(0xFFFFBE00)
                                    com.myplanner.app.domain.model.Priority.LOW -> Color(0xFF2ED573)
                                }
                            )
                        ).cornerRadius(4.dp)
                    ) {}
                    Spacer(GlanceModifier.width(10.dp))
                    Text(
                        task.title,
                        style = TextStyle(color = createColorProvider(Color.White), fontSize = 12.sp),
                        maxLines = 1
                    )
                }
            }
        }
    }
}

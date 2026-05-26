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
import androidx.glance.layout.*
import androidx.glance.text.*
import androidx.glance.unit.ColorProvider
import com.myplanner.app.MainActivity
import com.myplanner.app.data.local.MyPlannerDatabase
import com.myplanner.app.domain.model.DayOfWeek
import kotlinx.coroutines.flow.first
import androidx.room.Room
import java.time.LocalDate

private fun createColorProvider(color: Color): ColorProvider = object : ColorProvider {
    override fun getColor(context: Context): Color = color
}

class ScheduleWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = ScheduleWidget()
}

class ScheduleWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val db = Room.databaseBuilder(context, MyPlannerDatabase::class.java, "myplanner.db")
            .fallbackToDestructiveMigration().build()

        val todayJava = LocalDate.now().dayOfWeek
        val today = when (todayJava) {
            java.time.DayOfWeek.MONDAY    -> DayOfWeek.SENIN
            java.time.DayOfWeek.TUESDAY   -> DayOfWeek.SELASA
            java.time.DayOfWeek.WEDNESDAY -> DayOfWeek.RABU
            java.time.DayOfWeek.THURSDAY  -> DayOfWeek.KAMIS
            java.time.DayOfWeek.FRIDAY    -> DayOfWeek.JUMAT
            java.time.DayOfWeek.SATURDAY  -> DayOfWeek.SABTU
            java.time.DayOfWeek.SUNDAY    -> DayOfWeek.MINGGU
        }

        val schedules = db.subjectScheduleDao().getSchedulesByDay(today.name).first().take(5)
        db.close()

        provideContent {
            ScheduleWidgetContent(schedules.map { it.toDomain() })
        }
    }
}

@Composable
fun ScheduleWidgetContent(schedules: List<com.myplanner.app.domain.model.SubjectSchedule>) {
    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(createColorProvider(Color(0xFF0D1117)))
            .padding(16.dp)
            .appWidgetBackground()
            .cornerRadius(28.dp)
            .clickable(actionStartActivity<MainActivity>()),
    ) {
        Text(
            "🏫 Jadwal Hari Ini",
            style = TextStyle(color = createColorProvider(Color(0xFFFF6B9D)), fontSize = 15.sp, fontWeight = FontWeight.Bold)
        )
        Spacer(GlanceModifier.height(12.dp))

        if (schedules.isEmpty()) {
            Text("Tidak ada jadwal hari ini",
                style = TextStyle(color = createColorProvider(Color(0xFF64748B)), fontSize = 13.sp))
        } else {
            schedules.forEach { s ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = GlanceModifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .background(createColorProvider(Color(0xFF161E2E)))
                        .cornerRadius(12.dp)
                        .padding(8.dp)
                ) {
                    Box(GlanceModifier.size(8.dp).background(
                        createColorProvider(Color(s.color))
                    ).cornerRadius(4.dp)) {}
                    Spacer(GlanceModifier.width(10.dp))
                    Column {
                        Text(s.subjectName,
                            style = TextStyle(color = createColorProvider(Color.White), fontSize = 12.sp),
                            maxLines = 1)
                        Text("${s.startTime} - ${s.endTime}",
                            style = TextStyle(color = createColorProvider(Color(0xFF94A3B8)), fontSize = 10.sp))
                    }
                }
            }
        }
    }
}

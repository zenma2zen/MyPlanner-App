package com.myplanner.app.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.myplanner.app.MainActivity
import com.myplanner.app.MyPlannerApp
import com.myplanner.app.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val taskId    = intent.getLongExtra("task_id", -1)
        val taskTitle = intent.getStringExtra("task_title") ?: "Tugas"

        val pendingIntent = PendingIntent.getActivity(
            context, taskId.toInt(),
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, MyPlannerApp.TASK_REMINDER_CHANNEL)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("⏰ Pengingat Tugas")
            .setContentText("Jangan lupa: $taskTitle")
            .setStyle(NotificationCompat.BigTextStyle().bigText("Tugas \"$taskTitle\" akan segera jatuh tempo!"))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        val manager = context.getSystemService(NotificationManager::class.java)
        manager.notify(taskId.toInt(), notification)
    }
}

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // WorkManager will reschedule via periodic work
        }
    }
}

package com.myplanner.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyPlannerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        val manager = getSystemService(NotificationManager::class.java)

        val taskChannel = NotificationChannel(
            TASK_REMINDER_CHANNEL,
            "Pengingat Tugas",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Notifikasi pengingat tugas yang akan jatuh tempo"
            enableVibration(true)
        }

        val scheduleChannel = NotificationChannel(
            SCHEDULE_CHANNEL,
            "Jadwal Pelajaran",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Notifikasi jadwal pelajaran hari ini"
        }

        manager.createNotificationChannels(listOf(taskChannel, scheduleChannel))
    }

    companion object {
        const val TASK_REMINDER_CHANNEL = "task_reminder"
        const val SCHEDULE_CHANNEL = "schedule_channel"
    }
}

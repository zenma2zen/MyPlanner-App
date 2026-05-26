package com.myplanner.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myplanner.app.data.local.dao.*
import com.myplanner.app.data.local.entities.*

@Database(
    entities = [TaskEntity::class, PlannerEventEntity::class, SubjectScheduleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MyPlannerDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun plannerEventDao(): PlannerEventDao
    abstract fun subjectScheduleDao(): SubjectScheduleDao
}

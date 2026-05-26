package com.myplanner.app

import android.content.Context
import androidx.room.Room
import com.myplanner.app.data.local.MyPlannerDatabase
import com.myplanner.app.data.local.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MyPlannerDatabase =
        Room.databaseBuilder(context, MyPlannerDatabase::class.java, "myplanner.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides fun provideTaskDao(db: MyPlannerDatabase): TaskDao = db.taskDao()
    @Provides fun providePlannerEventDao(db: MyPlannerDatabase): PlannerEventDao = db.plannerEventDao()
    @Provides fun provideSubjectScheduleDao(db: MyPlannerDatabase): SubjectScheduleDao = db.subjectScheduleDao()
}

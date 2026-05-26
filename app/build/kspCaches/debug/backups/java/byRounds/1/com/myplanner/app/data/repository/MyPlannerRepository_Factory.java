package com.myplanner.app.data.repository;

import android.content.Context;
import com.myplanner.app.data.local.dao.PlannerEventDao;
import com.myplanner.app.data.local.dao.SubjectScheduleDao;
import com.myplanner.app.data.local.dao.TaskDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class MyPlannerRepository_Factory implements Factory<MyPlannerRepository> {
  private final Provider<TaskDao> taskDaoProvider;

  private final Provider<PlannerEventDao> plannerEventDaoProvider;

  private final Provider<SubjectScheduleDao> subjectScheduleDaoProvider;

  private final Provider<Context> contextProvider;

  public MyPlannerRepository_Factory(Provider<TaskDao> taskDaoProvider,
      Provider<PlannerEventDao> plannerEventDaoProvider,
      Provider<SubjectScheduleDao> subjectScheduleDaoProvider, Provider<Context> contextProvider) {
    this.taskDaoProvider = taskDaoProvider;
    this.plannerEventDaoProvider = plannerEventDaoProvider;
    this.subjectScheduleDaoProvider = subjectScheduleDaoProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public MyPlannerRepository get() {
    return newInstance(taskDaoProvider.get(), plannerEventDaoProvider.get(), subjectScheduleDaoProvider.get(), contextProvider.get());
  }

  public static MyPlannerRepository_Factory create(Provider<TaskDao> taskDaoProvider,
      Provider<PlannerEventDao> plannerEventDaoProvider,
      Provider<SubjectScheduleDao> subjectScheduleDaoProvider, Provider<Context> contextProvider) {
    return new MyPlannerRepository_Factory(taskDaoProvider, plannerEventDaoProvider, subjectScheduleDaoProvider, contextProvider);
  }

  public static MyPlannerRepository newInstance(TaskDao taskDao, PlannerEventDao plannerEventDao,
      SubjectScheduleDao subjectScheduleDao, Context context) {
    return new MyPlannerRepository(taskDao, plannerEventDao, subjectScheduleDao, context);
  }
}

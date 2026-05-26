package com.myplanner.app;

import com.myplanner.app.data.local.MyPlannerDatabase;
import com.myplanner.app.data.local.dao.SubjectScheduleDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
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
public final class AppModule_ProvideSubjectScheduleDaoFactory implements Factory<SubjectScheduleDao> {
  private final Provider<MyPlannerDatabase> dbProvider;

  public AppModule_ProvideSubjectScheduleDaoFactory(Provider<MyPlannerDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public SubjectScheduleDao get() {
    return provideSubjectScheduleDao(dbProvider.get());
  }

  public static AppModule_ProvideSubjectScheduleDaoFactory create(
      Provider<MyPlannerDatabase> dbProvider) {
    return new AppModule_ProvideSubjectScheduleDaoFactory(dbProvider);
  }

  public static SubjectScheduleDao provideSubjectScheduleDao(MyPlannerDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideSubjectScheduleDao(db));
  }
}

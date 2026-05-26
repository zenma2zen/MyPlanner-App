package com.myplanner.app;

import com.myplanner.app.data.local.MyPlannerDatabase;
import com.myplanner.app.data.local.dao.TaskDao;
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
public final class AppModule_ProvideTaskDaoFactory implements Factory<TaskDao> {
  private final Provider<MyPlannerDatabase> dbProvider;

  public AppModule_ProvideTaskDaoFactory(Provider<MyPlannerDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public TaskDao get() {
    return provideTaskDao(dbProvider.get());
  }

  public static AppModule_ProvideTaskDaoFactory create(Provider<MyPlannerDatabase> dbProvider) {
    return new AppModule_ProvideTaskDaoFactory(dbProvider);
  }

  public static TaskDao provideTaskDao(MyPlannerDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideTaskDao(db));
  }
}

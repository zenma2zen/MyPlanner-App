package com.myplanner.app.ui;

import com.myplanner.app.data.repository.MyPlannerRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class TaskViewModel_Factory implements Factory<TaskViewModel> {
  private final Provider<MyPlannerRepository> repoProvider;

  public TaskViewModel_Factory(Provider<MyPlannerRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public TaskViewModel get() {
    return newInstance(repoProvider.get());
  }

  public static TaskViewModel_Factory create(Provider<MyPlannerRepository> repoProvider) {
    return new TaskViewModel_Factory(repoProvider);
  }

  public static TaskViewModel newInstance(MyPlannerRepository repo) {
    return new TaskViewModel(repo);
  }
}

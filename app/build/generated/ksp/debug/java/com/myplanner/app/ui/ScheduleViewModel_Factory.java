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
public final class ScheduleViewModel_Factory implements Factory<ScheduleViewModel> {
  private final Provider<MyPlannerRepository> repoProvider;

  public ScheduleViewModel_Factory(Provider<MyPlannerRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public ScheduleViewModel get() {
    return newInstance(repoProvider.get());
  }

  public static ScheduleViewModel_Factory create(Provider<MyPlannerRepository> repoProvider) {
    return new ScheduleViewModel_Factory(repoProvider);
  }

  public static ScheduleViewModel newInstance(MyPlannerRepository repo) {
    return new ScheduleViewModel(repo);
  }
}

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
public final class PlannerViewModel_Factory implements Factory<PlannerViewModel> {
  private final Provider<MyPlannerRepository> repoProvider;

  public PlannerViewModel_Factory(Provider<MyPlannerRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public PlannerViewModel get() {
    return newInstance(repoProvider.get());
  }

  public static PlannerViewModel_Factory create(Provider<MyPlannerRepository> repoProvider) {
    return new PlannerViewModel_Factory(repoProvider);
  }

  public static PlannerViewModel newInstance(MyPlannerRepository repo) {
    return new PlannerViewModel(repo);
  }
}

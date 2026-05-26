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
public final class ExportImportViewModel_Factory implements Factory<ExportImportViewModel> {
  private final Provider<MyPlannerRepository> repoProvider;

  public ExportImportViewModel_Factory(Provider<MyPlannerRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public ExportImportViewModel get() {
    return newInstance(repoProvider.get());
  }

  public static ExportImportViewModel_Factory create(Provider<MyPlannerRepository> repoProvider) {
    return new ExportImportViewModel_Factory(repoProvider);
  }

  public static ExportImportViewModel newInstance(MyPlannerRepository repo) {
    return new ExportImportViewModel(repo);
  }
}

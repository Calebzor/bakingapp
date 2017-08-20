package hu.tvarga.bakingapp.di.androidinjectors;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import hu.tvarga.bakingapp.di.scopes.ActivityScope;
import hu.tvarga.bakingapp.ui.detail.AdditionalDetailActivity;
import hu.tvarga.bakingapp.ui.detail.DetailActivity;
import hu.tvarga.bakingapp.ui.main.MainActivity;

@Module
public interface ActivityInjectorModule {

	@ActivityScope
	@ContributesAndroidInjector
	abstract MainActivity contributesMainActivityInjector();

	@ActivityScope
	@ContributesAndroidInjector
	abstract DetailActivity contributesDetailActivityInjector();

	@ActivityScope
	@ContributesAndroidInjector
	abstract AdditionalDetailActivity contributesAdditionalDetailActivityInjector();
}
package hu.tvarga.bakingapp.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import hu.tvarga.bakingapp.di.scopes.ActivityScope;
import hu.tvarga.bakingapp.ui.detail.DetailActivity;
import hu.tvarga.bakingapp.ui.main.MainActivity;

@Module
public abstract class ActivityInjectorModule {

	@ActivityScope
	@ContributesAndroidInjector
	abstract MainActivity contributesMainActivityInjector();

	@ActivityScope
	@ContributesAndroidInjector
	abstract DetailActivity contributesDetailActivityInjector();
}
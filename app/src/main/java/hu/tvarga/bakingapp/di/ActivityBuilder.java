package hu.tvarga.bakingapp.di;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import hu.tvarga.bakingapp.ui.MainActivity;

@Module
public abstract class ActivityBuilder {

	@Binds
	@IntoMap
	@ActivityKey(MainActivity.class)
	abstract AndroidInjector.Factory<? extends Activity> bindMainActivity(
			MainActivityComponent.Builder builder);

	//	@Binds
	//	@IntoMap
	//	@ActivityKey(DetailActivity.class)
	//	abstract AndroidInjector.Factory<? extends Activity> bindDetailActivity(DetailActivityComponent.Builder builder);

}
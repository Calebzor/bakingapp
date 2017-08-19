package hu.tvarga.bakingapp.di;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import hu.tvarga.bakingapp.ui.MainActivityComponent;

@Module(subcomponents = {MainActivityComponent.class})
public class AppModule {

	@Provides
	Context provideContext(Application application) {
		return application;
	}

}

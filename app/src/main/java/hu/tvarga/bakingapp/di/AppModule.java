package hu.tvarga.bakingapp.di;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents = {MainActivityComponent.class})
public class AppModule {

	@Provides
	Context provideContext(Application application) {
		return application;
	}

	@Provides
	Handler provideMainHandler(Application application) {
		return new Handler(application.getMainLooper());
	}

}

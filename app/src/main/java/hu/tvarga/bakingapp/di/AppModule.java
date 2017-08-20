package hu.tvarga.bakingapp.di;

import android.content.Context;
import android.os.Handler;

import dagger.Module;
import dagger.Provides;
import hu.tvarga.bakingapp.BakingApp;
import hu.tvarga.bakingapp.di.androidinjectors.ActivityInjectorModule;
import hu.tvarga.bakingapp.di.androidinjectors.FragmentInjectorModule;
import hu.tvarga.bakingapp.di.scopes.ApplicationScope;

@Module(includes = {ActivityInjectorModule.class, FragmentInjectorModule.class})
@ApplicationScope
public class AppModule {

	private final BakingApp application;

	public AppModule(BakingApp app) {
		application = app;
	}

	@Provides
	Context provideContext() {
		return application.getApplicationContext();
	}

	@Provides
	Handler provideMainHandler() {
		return new Handler(application.getMainLooper());
	}

}

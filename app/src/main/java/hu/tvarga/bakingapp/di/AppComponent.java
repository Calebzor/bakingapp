package hu.tvarga.bakingapp.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import hu.tvarga.bakingapp.BakingApp;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ActivityBuilder.class})
public interface AppComponent {

	@Component.Builder
	interface Builder {

		@BindsInstance
		Builder application(Application application);
		AppComponent build();
	}

	void inject(BakingApp app);
}
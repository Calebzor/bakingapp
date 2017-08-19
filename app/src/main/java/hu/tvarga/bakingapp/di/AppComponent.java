package hu.tvarga.bakingapp.di;

import android.content.Context;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import hu.tvarga.bakingapp.BakingApp;
import hu.tvarga.bakingapp.di.scopes.ApplicationScope;

@ApplicationScope
@Component(modules = {AppModule.class, AndroidInjectionModule.class})
public interface AppComponent {

	void inject(BakingApp bakingApp);

	Context getContext();

}
package hu.tvarga.bakingapp.di;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import hu.tvarga.bakingapp.di.scopes.ActivityScope;
import hu.tvarga.bakingapp.ui.main.MainActivity;

@ActivityScope
@Subcomponent(modules = MainActivityModule.class)
public interface MainActivityComponent extends AndroidInjector<MainActivity> {

	@Subcomponent.Builder
	abstract class Builder extends AndroidInjector.Builder<MainActivity> {}
}
package hu.tvarga.bakingapp.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import hu.tvarga.bakingapp.di.scopes.FragmentScope;
import hu.tvarga.bakingapp.ui.main.fragments.BaseFragment;
import hu.tvarga.bakingapp.ui.main.fragments.MainCardFragment;

@Module
public interface FragmentInjectorModule {

	@FragmentScope
	@ContributesAndroidInjector
	BaseFragment contributesBaseFragmentInjector();

	@FragmentScope
	@ContributesAndroidInjector
	MainCardFragment contributesMainCardFragmentInjector();
}

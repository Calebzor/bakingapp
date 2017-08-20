package hu.tvarga.bakingapp.di.androidinjectors;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import hu.tvarga.bakingapp.di.scopes.FragmentScope;
import hu.tvarga.bakingapp.ui.BaseFragment;
import hu.tvarga.bakingapp.ui.detail.fragments.DetailFragment;
import hu.tvarga.bakingapp.ui.detail.fragments.IngredientsFragment;
import hu.tvarga.bakingapp.ui.detail.fragments.StepFragment;
import hu.tvarga.bakingapp.ui.main.fragments.MainCardFragment;

@Module
public interface FragmentInjectorModule {

	@FragmentScope
	@ContributesAndroidInjector
	BaseFragment contributesBaseFragmentInjector();

	@FragmentScope
	@ContributesAndroidInjector
	MainCardFragment contributesMainCardFragmentInjector();

	@FragmentScope
	@ContributesAndroidInjector
	DetailFragment contributesDetailFragmentInjector();

	@FragmentScope
	@ContributesAndroidInjector
	IngredientsFragment contributesIngredientsFragmentInjector();

	@FragmentScope
	@ContributesAndroidInjector
	StepFragment contributesStepFragmentInjector();
}

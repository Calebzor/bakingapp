package hu.tvarga.bakingapp.ui.detail;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import hu.tvarga.bakingapp.R;
import hu.tvarga.bakingapp.dataaccess.objects.RecepyWithIngredientsAndSteps;
import hu.tvarga.bakingapp.dataaccess.preferences.Preferences;
import hu.tvarga.bakingapp.ui.adapters.DetailAdapter;
import hu.tvarga.bakingapp.ui.detail.fragments.DetailBaseFragment;
import hu.tvarga.bakingapp.ui.detail.fragments.DetailFragment;
import hu.tvarga.bakingapp.ui.detail.fragments.IngredientsFragment;
import hu.tvarga.bakingapp.ui.detail.fragments.SecondDetailFragment;
import hu.tvarga.bakingapp.ui.detail.fragments.StepFragment;
import hu.tvarga.bakingapp.utilties.GsonHelper;

import static hu.tvarga.bakingapp.ui.adapters.MainCardAdapter.RECEPY_EXTRA_KEY;
import static hu.tvarga.bakingapp.ui.detail.fragments.StepFragment.STEP_EXTRA_KEY;

public class DetailActivity extends DetailBaseActivity
		implements DetailAdapter.DetailItemClickAction {

	private static final String SECOND_FRAGMENT_INSTANCE_KEY = "SECOND_FRAGMENT_INSTANCE_KEY";

	SecondDetailFragment secondDetailFragment;
	private boolean multiPane;
	private SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		sharedPreferences = Preferences.getSharedPreferences(this);
		String recepyJson = sharedPreferences.getString(RECEPY_EXTRA_KEY, null);
		recepy = GsonHelper.getGson().fromJson(recepyJson, RecepyWithIngredientsAndSteps.class);
		if (recepy != null) {
			setTitle(recepy.name);
		}

		if (baseFragment == null) {
			baseFragment = new DetailFragment();
			((DetailBaseFragment) baseFragment).recepy = recepy;
		}

		loadFragment(baseFragment);

		multiPane = findViewById(R.id.detailActivitySecondContainer) != null;
		if (multiPane) {
			if (savedInstanceState != null) {
				secondDetailFragment =
						(SecondDetailFragment) getSupportFragmentManager().getFragment(
								savedInstanceState, SECOND_FRAGMENT_INSTANCE_KEY);
			}

			if (secondDetailFragment == null) {
				secondDetailFragment = new IngredientsFragment();
				secondDetailFragment.recepy = recepy;
			}

			loadFragment(secondDetailFragment);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (multiPane) {
			getSupportFragmentManager().putFragment(outState, SECOND_FRAGMENT_INSTANCE_KEY,
					secondDetailFragment);
		}
	}

	public void loadFragment(SecondDetailFragment fragment) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.detailActivitySecondContainer, fragment)
				.commit();
	}

	@Override
	public void onItemClick(Integer step) {
		if (multiPane) {
			if (step == null) {
				secondDetailFragment = new IngredientsFragment();
				secondDetailFragment.recepy = recepy;
				loadFragment(secondDetailFragment);
				return;
			}
			secondDetailFragment = new StepFragment();
			secondDetailFragment.recepy = recepy;
			((StepFragment) secondDetailFragment).step = step;
			loadFragment(secondDetailFragment);
			return;
		}
		Intent intent = new Intent(this, AdditionalDetailActivity.class);
		intent.putExtra(RECEPY_EXTRA_KEY, recepy);
		if (step != null) {
			intent.putExtra(STEP_EXTRA_KEY, step);
		}
		startActivity(intent);

	}

}

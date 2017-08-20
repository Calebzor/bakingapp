package hu.tvarga.bakingapp.ui.detail;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import hu.tvarga.bakingapp.R;
import hu.tvarga.bakingapp.dataaccess.objects.RecepyWithIngredientsAndSteps;
import hu.tvarga.bakingapp.ui.BaseActivity;
import hu.tvarga.bakingapp.ui.adapters.DetailAdapter;
import hu.tvarga.bakingapp.ui.detail.fragments.DetailFragment;
import hu.tvarga.bakingapp.ui.detail.fragments.IngredientsFragment;
import hu.tvarga.bakingapp.ui.detail.fragments.SecondDetailFragment;
import hu.tvarga.bakingapp.ui.detail.fragments.StepFragment;

import static hu.tvarga.bakingapp.ui.adapters.MainCardAdapter.RECEPY_EXTRA_KEY;

public class DetailActivity extends BaseActivity implements DetailAdapter.DetailItemClickAction {

	private static final String SECOND_FRAGMENT_INSTANCE_KEY = "SECOND_FRAGMENT_INSTANCE_KEY";

	SecondDetailFragment secondDetailFragment;
	private boolean multiPane;
	private RecepyWithIngredientsAndSteps recepy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		Bundle extras = getIntent().getExtras();
		recepy = (RecepyWithIngredientsAndSteps) extras.getSerializable(RECEPY_EXTRA_KEY);
		if (recepy != null) {
			setTitle(recepy.name);
		}

		if (baseFragment == null) {
			baseFragment = new DetailFragment();
			((DetailFragment) baseFragment).recepy = recepy;
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
		outState.putSerializable(RECEPY_EXTRA_KEY, recepy);
		if (multiPane) {
			getSupportFragmentManager().putFragment(outState, SECOND_FRAGMENT_INSTANCE_KEY,
					secondDetailFragment);
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if (savedInstanceState != null && savedInstanceState.containsKey(RECEPY_EXTRA_KEY)) {
			recepy = (RecepyWithIngredientsAndSteps) savedInstanceState.getSerializable(
					RECEPY_EXTRA_KEY);
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
		}
		// start activity with recepy and step
	}
}

package hu.tvarga.bakingapp.ui.detail;

import android.os.Bundle;

import hu.tvarga.bakingapp.R;
import hu.tvarga.bakingapp.dataaccess.objects.RecepyWithIngredientsAndSteps;
import hu.tvarga.bakingapp.ui.detail.fragments.DetailBaseFragment;
import hu.tvarga.bakingapp.ui.detail.fragments.IngredientsFragment;
import hu.tvarga.bakingapp.ui.detail.fragments.StepFragment;

import static hu.tvarga.bakingapp.ui.adapters.MainCardAdapter.RECEPY_EXTRA_KEY;
import static hu.tvarga.bakingapp.ui.detail.fragments.StepFragment.STEP_EXTRA_KEY;

public class AdditionalDetailActivity extends DetailBaseActivity {

	private Integer step;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_additional_detail);

		Bundle extras = getIntent().getExtras();
		recepy = (RecepyWithIngredientsAndSteps) extras.getSerializable(RECEPY_EXTRA_KEY);
		if (extras.containsKey(STEP_EXTRA_KEY)) {
			step = extras.getInt(STEP_EXTRA_KEY);
		}

		if (baseFragment == null) {
			if (step == null) {
				baseFragment = new IngredientsFragment();
			}
			else {
				baseFragment = new StepFragment();
				((StepFragment) baseFragment).step = step;
			}
			((DetailBaseFragment) baseFragment).recepy = recepy;
		}

		loadFragment(baseFragment);
	}

	public void goPreviousStep(int step) {
		if (step == 0) {
			finish();
			return;
		}
		this.step = step - 1;
		startNewStepFragment();
	}

	public void goNextStep(int step) {
		if (step == recepy.steps.size() - 1) {
			finish();
			return;
		}
		this.step = step + 1;
		startNewStepFragment();
	}

	private void startNewStepFragment() {
		baseFragment = new StepFragment();
		((StepFragment) baseFragment).step = this.step;
		((DetailBaseFragment) baseFragment).recepy = recepy;
		loadFragment(baseFragment);
	}
}

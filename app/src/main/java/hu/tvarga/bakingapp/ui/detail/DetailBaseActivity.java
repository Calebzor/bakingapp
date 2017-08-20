package hu.tvarga.bakingapp.ui.detail;

import android.os.Bundle;

import hu.tvarga.bakingapp.dataaccess.objects.RecepyWithIngredientsAndSteps;
import hu.tvarga.bakingapp.ui.BaseActivity;

import static hu.tvarga.bakingapp.ui.adapters.MainCardAdapter.RECEPY_EXTRA_KEY;

public class DetailBaseActivity extends BaseActivity {

	protected RecepyWithIngredientsAndSteps recepy;

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(RECEPY_EXTRA_KEY, recepy);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if (savedInstanceState != null && savedInstanceState.containsKey(RECEPY_EXTRA_KEY)) {
			recepy = (RecepyWithIngredientsAndSteps) savedInstanceState.getSerializable(
					RECEPY_EXTRA_KEY);
		}
	}
}

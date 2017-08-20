package hu.tvarga.bakingapp.ui.detail;

import android.os.Bundle;
import android.widget.Toast;

import hu.tvarga.bakingapp.R;
import hu.tvarga.bakingapp.dataaccess.objects.RecepyWithIngredientsAndSteps;
import hu.tvarga.bakingapp.dataaccess.objects.Step;
import hu.tvarga.bakingapp.ui.BaseActivity;
import hu.tvarga.bakingapp.ui.adapters.DetailAdapter;
import hu.tvarga.bakingapp.ui.detail.fragments.DetailFragment;

import static hu.tvarga.bakingapp.ui.adapters.MainCardAdapter.RECEPY_EXTRA_KEY;

public class DetailActivity extends BaseActivity implements DetailAdapter.DetailItemClickAction {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		Bundle extras = getIntent().getExtras();
		RecepyWithIngredientsAndSteps recepy =
				(RecepyWithIngredientsAndSteps) extras.getSerializable(RECEPY_EXTRA_KEY);
		if (recepy != null) {
			setTitle(recepy.name);
		}

		if (baseFragment == null) {
			baseFragment = new DetailFragment();
			((DetailFragment) baseFragment).recepy = recepy;
		}

		loadFragment(baseFragment);
	}

	@Override
	public void onItemClick(Step step) {
		String text = "Ingredients clicked";
		if (step != null) {
			text = step.id + " step clicked";
		}
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
}

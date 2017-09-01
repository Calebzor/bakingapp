package hu.tvarga.bakingapp.ui.detail;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;

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
import static hu.tvarga.bakingapp.widget.WidgetProvider.ACTION_UPDATE;

public class DetailActivity extends DetailBaseActivity
		implements DetailAdapter.DetailItemClickAction {

	public static final String FAVORITE_RECEPY_INDEX = "FAVORITE_RECEPY_INDEX";
	private static final String SECOND_FRAGMENT_INSTANCE_KEY = "SECOND_FRAGMENT_INSTANCE_KEY";

	SecondDetailFragment secondDetailFragment;
	private boolean multiPane;
	private SharedPreferences sharedPreferences;
	private Menu menu;

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
	public boolean onCreateOptionsMenu(Menu menu) {
		this.menu = menu;
		getMenuInflater().inflate(R.menu.menu, menu);
		setFavoriteIcon();
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.favorite) {
			toggleFavorite();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void toggleFavorite() {
		int favoritesIndex = sharedPreferences.getInt(FAVORITE_RECEPY_INDEX, -1);
		int newIndex = -1;
		if (favoritesIndex != recepy.id) {
			newIndex = recepy.id;
		}
		sharedPreferences.edit().putInt(FAVORITE_RECEPY_INDEX, newIndex).apply();
		setFavoriteIcon();
		Intent dataUpdatedIntent = new Intent(ACTION_UPDATE);
		sendBroadcast(dataUpdatedIntent);
	}

	private void setFavoriteIcon() {
		int favoritesIndex = sharedPreferences.getInt(FAVORITE_RECEPY_INDEX, -1);
		if (menu != null && menu.getItem(0) != null) {
			if (favoritesIndex == -1 || recepy == null || favoritesIndex != recepy.id) {
				menu.getItem(0).setIcon(
						ContextCompat.getDrawable(this, android.R.drawable.btn_star_big_off));
			}
			else {
				menu.getItem(0).setIcon(
						ContextCompat.getDrawable(this, android.R.drawable.btn_star_big_on));
			}
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

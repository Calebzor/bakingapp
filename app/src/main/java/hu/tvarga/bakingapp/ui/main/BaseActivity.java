package hu.tvarga.bakingapp.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import hu.tvarga.bakingapp.R;
import hu.tvarga.bakingapp.ui.main.fragments.BaseFragment;

public abstract class BaseActivity extends DaggerAppCompatActivity {

	public static final String BASE_FRAGMENT_INSTANCE_KEY = "BASE_FRAGMENT_INSTANCE_KEY";

	protected BaseFragment baseFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ButterKnife.bind(this);

		if (savedInstanceState != null) {
			baseFragment = (BaseFragment) getSupportFragmentManager().getFragment(
					savedInstanceState, BASE_FRAGMENT_INSTANCE_KEY);
		}

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, BASE_FRAGMENT_INSTANCE_KEY, baseFragment);
	}

	public void loadFragment(Fragment fragment) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.mainContainer, fragment).commit();
	}
}

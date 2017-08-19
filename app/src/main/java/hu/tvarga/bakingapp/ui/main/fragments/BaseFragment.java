package hu.tvarga.bakingapp.ui.main.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;
import hu.tvarga.bakingapp.ui.main.MainActivity;

public class BaseFragment extends DaggerFragment {

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ButterKnife.bind(this, view);
	}

	protected void loadFragment(BaseFragment baseFragment) {
		MainActivity mainActivity = (MainActivity) getActivity();
		mainActivity.loadFragment(baseFragment);
	}

}

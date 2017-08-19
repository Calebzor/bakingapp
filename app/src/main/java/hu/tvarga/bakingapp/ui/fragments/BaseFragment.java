package hu.tvarga.bakingapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.ButterKnife;
import hu.tvarga.bakingapp.ui.MainActivity;

public class BaseFragment extends Fragment {

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

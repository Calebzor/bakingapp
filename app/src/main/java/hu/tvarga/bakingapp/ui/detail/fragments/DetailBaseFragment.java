package hu.tvarga.bakingapp.ui.detail.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.Unbinder;
import hu.tvarga.bakingapp.dataaccess.objects.RecepyWithIngredientsAndSteps;
import hu.tvarga.bakingapp.ui.BaseFragment;

import static hu.tvarga.bakingapp.ui.adapters.MainCardAdapter.RECEPY_EXTRA_KEY;

public abstract class DetailBaseFragment extends BaseFragment {

	public RecepyWithIngredientsAndSteps recepy;
	protected Unbinder unbinder;

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (savedInstanceState != null && savedInstanceState.containsKey(RECEPY_EXTRA_KEY)) {
			recepy = (RecepyWithIngredientsAndSteps) savedInstanceState.getSerializable(
					RECEPY_EXTRA_KEY);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putSerializable(RECEPY_EXTRA_KEY, recepy);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}
}

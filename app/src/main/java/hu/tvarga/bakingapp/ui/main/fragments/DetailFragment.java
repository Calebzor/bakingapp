package hu.tvarga.bakingapp.ui.main.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hu.tvarga.bakingapp.R;
import hu.tvarga.bakingapp.dataaccess.objects.RecepyWithIngredientsAndSteps;
import hu.tvarga.bakingapp.ui.adapters.DetailAdapter;

import static hu.tvarga.bakingapp.ui.adapters.MainCardAdapter.RECEPY_EXTRA_KEY;

public class DetailFragment extends BaseFragment {

	@BindView(R.id.detailFragment)
	RecyclerView recyclerView;

	public RecepyWithIngredientsAndSteps recepy;

	private View root;
	private Unbinder unbinder;
	private DetailAdapter detailAdapter;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.fragment_detail, container, false);
		unbinder = ButterKnife.bind(this, root);

		detailAdapter = new DetailAdapter(getActivity());
		detailAdapter.setRecepy(recepy);

		recyclerView.setAdapter(detailAdapter);

		return root;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (savedInstanceState != null && savedInstanceState.containsKey(RECEPY_EXTRA_KEY)) {
			recepy = (RecepyWithIngredientsAndSteps) savedInstanceState.getSerializable(
					RECEPY_EXTRA_KEY);
			detailAdapter.setRecepy(recepy);
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

package hu.tvarga.bakingapp.ui.detail.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.tvarga.bakingapp.R;
import hu.tvarga.bakingapp.ui.adapters.DetailAdapter;

import static hu.tvarga.bakingapp.ui.adapters.DetailAdapter.DETAIL_ADAPTER_SELECTED_POSITION;

public class DetailFragment extends DetailBaseFragment {

	@BindView(R.id.detailFragment)
	RecyclerView recyclerView;

	public DetailAdapter detailAdapter;


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
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putInt(DETAIL_ADAPTER_SELECTED_POSITION, detailAdapter.selectedPosition);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		detailAdapter.setRecepy(recepy);
		if (savedInstanceState != null && savedInstanceState.containsKey(
				DETAIL_ADAPTER_SELECTED_POSITION)) {
			detailAdapter.selectedPosition = savedInstanceState.getInt(
					DETAIL_ADAPTER_SELECTED_POSITION);
		}
	}

}

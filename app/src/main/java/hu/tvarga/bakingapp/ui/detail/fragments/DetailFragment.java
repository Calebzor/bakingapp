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

public class DetailFragment extends DetailBaseFragment {

	@BindView(R.id.detailFragment)
	RecyclerView recyclerView;

	private View root;
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
		detailAdapter.setRecepy(recepy);
	}

}

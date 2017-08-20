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
import hu.tvarga.bakingapp.ui.adapters.IngredientsAdapter;

public class IngredientsFragment extends SecondDetailFragment {

	@BindView(R.id.ingredientsList)
	RecyclerView recyclerView;

	private IngredientsAdapter ingredientsAdapter;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.fragment_ingredients, container, false);
		unbinder = ButterKnife.bind(this, root);

		ingredientsAdapter = new IngredientsAdapter(getActivity());
		ingredientsAdapter.setRecepy(recepy);

		recyclerView.setAdapter(ingredientsAdapter);

		return root;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ingredientsAdapter.setRecepy(recepy);
	}
}

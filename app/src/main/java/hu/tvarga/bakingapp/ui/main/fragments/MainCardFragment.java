package hu.tvarga.bakingapp.ui.main.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hu.tvarga.bakingapp.R;
import hu.tvarga.bakingapp.dataaccess.db.DbFactory;
import hu.tvarga.bakingapp.dataaccess.objects.RecepyWithIngredientsAndSteps;
import hu.tvarga.bakingapp.ui.BaseFragment;
import hu.tvarga.bakingapp.ui.adapters.MainCardAdapter;

import static hu.tvarga.bakingapp.utilties.DispatchQueueHelper.runInBackgroundThread;
import static hu.tvarga.bakingapp.utilties.DispatchQueueHelper.runInMainThread;

public class MainCardFragment extends BaseFragment {

	@Inject
	DbFactory dbFactory;

	@BindView(R.id.mainCardFragment)
	RecyclerView recyclerView;

	private List<RecepyWithIngredientsAndSteps> recepies = new ArrayList<>();
	private View root;
	private Unbinder unbinder;
	private MainCardAdapter mainCardAdapter;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.fragment_main_card, container, false);
		unbinder = ButterKnife.bind(this, root);

		mainCardAdapter = new MainCardAdapter(getActivity(), recepies);
		recyclerView.setAdapter(mainCardAdapter);
		RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
		if (layoutManager instanceof GridLayoutManager) {
			GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
			gridLayoutManager.setSpanCount(3);
		}

		return root;
	}

	@Override
	public void onResume() {
		super.onResume();
		loadDataForFragment();
	}

	private void loadDataForFragment() {
		runInBackgroundThread(new Runnable() {
			@Override
			public void run() {
				List<RecepyWithIngredientsAndSteps> recepyWithIngredientsAndStepses =
						dbFactory.getDb().recepyWithIngredientsAndStepsDao()
								.loadAllRecepyWithIngredientsAndSteps();
				if (recepyWithIngredientsAndStepses != null) {
					recepies.clear();
					recepies.addAll(recepyWithIngredientsAndStepses);
				}
				runInMainThread(new Runnable() {
					@Override
					public void run() {
						mainCardAdapter.notifyDataSetChanged();
					}
				});
			}
		});
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}

}

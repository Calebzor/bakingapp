package hu.tvarga.bakingapp.ui.main.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
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
import hu.tvarga.bakingapp.ui.adapters.MainCardAdapter;

import static hu.tvarga.bakingapp.utilties.DispatchQueueHelper.runInBackgroundThread;

public class MainCardFragment extends BaseFragment {

	@Inject
	DbFactory dbFactory;

	@BindView(R.id.mainCardFragment)
	RecyclerView recyclerView;

	private List<RecepyWithIngredientsAndSteps> recepies = new ArrayList<>();
	private View root;
	private Unbinder unbinder;
	private MainCardAdapter mainCardAdapter;
	private LinearLayoutManager recyclerViewLayoutManager;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.fragment_main_card, container, false);
		unbinder = ButterKnife.bind(this, root);

		mainCardAdapter = new MainCardAdapter(getActivity(), recepies);
		recyclerView.setAdapter(mainCardAdapter);
		recyclerViewLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

		return root;
	}

	@Override
	public void onResume() {
		super.onResume();
		runInBackgroundThread(new Runnable() {
			@Override
			public void run() {
				//				dbFactory.getDb();
				//				recepies = recepyWithIngredientsAndStepses;

				//				RecepyFromNetworkDbHelper.addRecepiesToDb(recepies, dbFactory.getDb());
			}
		});
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}

}
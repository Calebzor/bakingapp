package hu.tvarga.bakingapp.ui.detail.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.tvarga.bakingapp.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class StepFragment extends SecondDetailFragment {

	private static final String STEP_EXTRA_KEY = "STEP_EXTRA_KEY";
	@BindView(R.id.video)
	TextView video;

	@BindView(R.id.description)
	TextView description;

	public int step;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.fragment_step, container, false);
		unbinder = ButterKnife.bind(this, root);

		return root;
	}

	@Override
	public void onResume() {
		super.onResume();
		String videoURL = recepy.steps.get(step).videoURL;
		if (videoURL != null) {
			video.setText(videoURL);
		}
		video.setVisibility((videoURL == null || videoURL.isEmpty()) ? GONE : VISIBLE);
		description.setText(recepy.steps.get(step).description);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (savedInstanceState != null && savedInstanceState.containsKey(STEP_EXTRA_KEY)) {
			step = savedInstanceState.getInt(STEP_EXTRA_KEY);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putSerializable(STEP_EXTRA_KEY, step);
	}
}

package hu.tvarga.bakingapp.ui.detail.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.tvarga.bakingapp.R;
import hu.tvarga.bakingapp.ui.detail.AdditionalDetailActivity;
import timber.log.Timber;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class StepFragment extends SecondDetailFragment {

	public static final String STEP_EXTRA_KEY = "STEP_EXTRA_KEY";
	public static final String VIDEO_PLAYBACK_POSITION = "VIDEO_PLAYBACK_POSITION";

	@Nullable
	@BindView(R.id.description)
	TextView description;

	@Nullable
	@BindView(R.id.buttonContainer)
	View buttonContainer;

	@BindView(R.id.playerView)
	SimpleExoPlayerView playerView;

	SimpleExoPlayer player;

	public int step;
	private long playbackPosition;

	private Target picassoDownloadTarget = new Target() {
		@Override
		public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
			playerView.setDefaultArtwork(bitmap);
			initializePlayer(Uri.parse(videoURL));
		}

		@Override
		public void onBitmapFailed(Drawable errorDrawable) {
			Timber.d("image download failed");
		}

		@Override
		public void onPrepareLoad(Drawable placeHolderDrawable) {
			// not using placeholder for now
		}
	};
	private String videoURL;
	;

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
		videoURL = recepy.steps.get(step).videoURL;
		if (hasVideo()) {
			String thumbnailURL = recepy.steps.get(step).thumbnailURL;
			if (thumbnailURL == null || thumbnailURL.isEmpty() ||
					thumbnailURL.toLowerCase().endsWith("mp4")) {
				thumbnailURL = "http://i.imgur.com/Cey1Ud1.jpg";
			}
			Timber.d("Getting thumbnail image from:" + thumbnailURL);
			Picasso.with(getActivity()).load(thumbnailURL).into(picassoDownloadTarget);
		}
		playerView.setVisibility((videoURL == null || videoURL.isEmpty()) ? GONE : VISIBLE);
		if (description != null) {
			description.setText(recepy.steps.get(step).description);
		}

		if (buttonContainer != null) {
			Button previousStep = (Button) root.findViewById(R.id.previousStep);
			Button nextStep = (Button) root.findViewById(R.id.nextStep);
			previousStep.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					((AdditionalDetailActivity) getActivity()).goPreviousStep(step);
				}
			});
			nextStep.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					((AdditionalDetailActivity) getActivity()).goNextStep(step);
				}
			});
		}
	}

	private boolean hasVideo() {return videoURL != null && !videoURL.isEmpty();}

	private void initializePlayer(Uri mediaUri) {
		if (player == null) {
			TrackSelector trackSelector = new DefaultTrackSelector();
			LoadControl loadControl = new DefaultLoadControl();
			player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
			playerView.setPlayer(player);
			String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
			DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getActivity(),
					userAgent);
			MediaSource mediaSource = new ExtractorMediaSource(mediaUri, dataSourceFactory,
					new DefaultExtractorsFactory(), null, null);
			player.prepare(mediaSource);
			player.setPlayWhenReady(true);
			player.seekTo(playbackPosition);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		releasePlayer();
	}

	private void releasePlayer() {
		if (player != null) {
			player.stop();
			player.release();
			player = null;
		}
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey(STEP_EXTRA_KEY)) {
				step = savedInstanceState.getInt(STEP_EXTRA_KEY);
			}
			if (savedInstanceState.containsKey(VIDEO_PLAYBACK_POSITION)) {
				playbackPosition = savedInstanceState.getLong(VIDEO_PLAYBACK_POSITION);
			}
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putSerializable(STEP_EXTRA_KEY, step);
		outState.putLong(VIDEO_PLAYBACK_POSITION, player.getCurrentPosition());
	}
}

package hu.tvarga.bakingapp.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import hu.tvarga.bakingapp.R;
import hu.tvarga.bakingapp.data.BakingData;
import hu.tvarga.bakingapp.dataaccess.db.DbFactory;
import hu.tvarga.bakingapp.dataaccess.db.RecepyFromNetworkDbHelper;
import hu.tvarga.bakingapp.dataaccess.network.NetworkCallback;
import hu.tvarga.bakingapp.dataaccess.network.Networking;
import hu.tvarga.bakingapp.dataaccess.objects.RecepyWithIngredientsAndSteps;
import hu.tvarga.bakingapp.ui.main.fragments.BaseFragment;
import hu.tvarga.bakingapp.ui.main.fragments.MainCardFragment;
import okhttp3.Call;
import timber.log.Timber;

import static hu.tvarga.bakingapp.utilties.DispatchQueueHelper.runInBackgroundThread;

public class MainActivity extends DaggerAppCompatActivity {

	public static final String BASE_FRAGMENT_INSTANCE_KEY = "BASE_FRAGMENT_INSTANCE_KEY";

	@Inject
	Networking networking;

	@Inject
	DbFactory dbFactory;

	private BaseFragment baseFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		if (savedInstanceState != null) {
			baseFragment = (BaseFragment) getSupportFragmentManager().getFragment(
					savedInstanceState, BASE_FRAGMENT_INSTANCE_KEY);
		}

		if (baseFragment == null) {
			baseFragment = new MainCardFragment();
		}

		loadFragment(baseFragment);

		getRecepiesFromNetwork();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, BASE_FRAGMENT_INSTANCE_KEY, baseFragment);
	}

	private void getRecepiesFromNetwork() {
		networking.get(BakingData.URL, new NetworkCallback() {
			@Override
			public void onFailure(Call call, IOException e) {
				failedToLoadRecepiesToast();
			}

			@Override
			public void onResponse(Call call, String response) {
				Timber.d(response);
				if (response == null || response.isEmpty()) {
					failedToLoadRecepiesToast();
					return;
				}
				List<RecepyWithIngredientsAndSteps> recepies = BakingData.getRecepies(response);
				if (recepies == null || recepies.isEmpty()) {
					failedToLoadRecepiesToast();
					return;
				}
				saveRecepiesToDB(recepies);
			}
		});
	}

	private void saveRecepiesToDB(final List<RecepyWithIngredientsAndSteps> recepies) {
		runInBackgroundThread(new Runnable() {
			@Override
			public void run() {
				RecepyFromNetworkDbHelper.addRecepiesToDb(recepies, dbFactory.getDb());
			}
		});
	}

	private void failedToLoadRecepiesToast() {
		Toast.makeText(MainActivity.this, "Failed to load recepies form the network!",
				Toast.LENGTH_SHORT).show();
	}

	public void loadFragment(Fragment fragment) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.mainActivityContainer, fragment).commit();
	}
}

package hu.tvarga.bakingapp.ui.main;

import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import hu.tvarga.bakingapp.R;
import hu.tvarga.bakingapp.data.BakingData;
import hu.tvarga.bakingapp.dataaccess.db.DbFactory;
import hu.tvarga.bakingapp.dataaccess.db.RecepyFromNetworkDbHelper;
import hu.tvarga.bakingapp.dataaccess.network.NetworkCallback;
import hu.tvarga.bakingapp.dataaccess.network.Networking;
import hu.tvarga.bakingapp.dataaccess.objects.RecepyWithIngredientsAndSteps;
import hu.tvarga.bakingapp.ui.BaseActivity;
import hu.tvarga.bakingapp.ui.main.fragments.MainCardFragment;
import okhttp3.Call;
import timber.log.Timber;

import static hu.tvarga.bakingapp.utilties.DispatchQueueHelper.runInBackgroundThread;

public class MainActivity extends BaseActivity {

	@Inject
	Networking networking;

	@Inject
	DbFactory dbFactory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (baseFragment == null) {
			baseFragment = new MainCardFragment();
		}

		loadFragment(baseFragment);

		getRecepiesFromNetwork();
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

}

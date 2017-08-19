package hu.tvarga.bakingapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import hu.tvarga.bakingapp.R;
import hu.tvarga.bakingapp.data.BakingData;
import hu.tvarga.bakingapp.dataaccess.network.NetworkCallback;
import hu.tvarga.bakingapp.dataaccess.network.Networking;
import hu.tvarga.bakingapp.dataaccess.objects.RecepyWithIngredientsAndSteps;
import hu.tvarga.bakingapp.ui.fragments.MainCardFragment;
import okhttp3.Call;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

	@Inject
	Networking networking;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		AndroidInjection.inject(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		loadFragment(new MainCardFragment());

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
				//				RecepyFromNetworkDbHelper.addRecepiesToDb(recepies, db);
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

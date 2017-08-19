package hu.tvarga.bakingapp.dataaccess.network;

import android.os.Handler;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import timber.log.Timber;

@Singleton
public class Networking {

	private ConnectivityHelper connectivityHelper;
	private Handler handler;
	private OkHttpClient client;

	@Inject
	public Networking(Handler handler, ConnectivityHelper connectivityHelper,
			DispatcherProvider dispatcherProvider) {
		this.handler = handler;
		this.connectivityHelper = connectivityHelper;
		client = new OkHttpClient.Builder().dispatcher(dispatcherProvider.getNewDispatcher())
				.build();
	}

	public void get(String url, final NetworkCallback callback) {
		Timber.d("Calling: ", url);
		if (!connectivityHelper.isNetworkAvailable()) {
			NoNetworkException noNetworkException = new NoNetworkException();
			Timber.w(noNetworkException);
			callback.onFailure(null, noNetworkException);
			return;
		}

		Request request = new Request.Builder().url(url).build();
		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(final Call call, final IOException e) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						Timber.e(e);
						callback.onFailure(call, e);
					}
				});
			}

			@Override
			public void onResponse(final Call call, final Response response) throws IOException {
				ResponseBody body = response.body();
				if (body != null) {
					final String responseString = body.string();
					Timber.d(responseString);

					handler.post(new Runnable() {
						@Override
						public void run() {
							callback.onResponse(call, responseString);
						}
					});
				}

			}
		});
	}
}

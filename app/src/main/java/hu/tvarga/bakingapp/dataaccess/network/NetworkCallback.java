package hu.tvarga.bakingapp.dataaccess.network;

import java.io.IOException;

import okhttp3.Call;

public interface NetworkCallback {

	void onFailure(Call call, IOException e);

	void onResponse(Call call, String response);
}

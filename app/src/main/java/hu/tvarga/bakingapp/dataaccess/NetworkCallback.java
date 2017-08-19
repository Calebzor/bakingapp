package hu.tvarga.bakingapp.dataaccess;

import java.io.IOException;

import okhttp3.Call;

public interface NetworkCallback {

	void onFailure(Call call, IOException e);

	void onResponse(Call call, String response);
}

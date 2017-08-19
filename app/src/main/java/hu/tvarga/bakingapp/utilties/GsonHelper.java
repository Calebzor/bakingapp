package hu.tvarga.bakingapp.utilties;

import com.google.gson.Gson;

public final class GsonHelper {

	private static Gson gson;

	private GsonHelper() {
		// hide public constructor
	}

	public static Gson getGson() {
		if (gson == null) {
			gson = new Gson();
		}

		return gson;
	}
}

package hu.tvarga.bakingapp.dataaccess.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import hu.tvarga.bakingapp.BuildConfig;

public class Preferences {

	public static SharedPreferences getSharedPreferences(Context context) {
		return context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
	}
}

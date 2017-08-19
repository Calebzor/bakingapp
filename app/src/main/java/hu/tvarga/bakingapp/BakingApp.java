package hu.tvarga.bakingapp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import hu.tvarga.bakingapp.dataaccess.Networking;
import hu.tvarga.bakingapp.di.DaggerAppComponent;
import timber.log.Timber;

public class BakingApp extends Application implements HasActivityInjector {

	public static final String LOG_TAG = "BakingApp";

	Networking networking;

	@Inject
	DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

	@Override
	public void onCreate() {
		super.onCreate();
		initializeApplication();
	}

	private void initializeApplication() {
		if (BuildConfig.DEBUG) {
			Timber.plant(new Timber.DebugTree());
		}
		DaggerAppComponent.builder().application(this).build().inject(this);
	}

	public static BakingApp get(Context context) {
		return (BakingApp) context.getApplicationContext();
	}

	@Override
	public DispatchingAndroidInjector<Activity> activityInjector() {
		return dispatchingAndroidInjector;
	}

}

package hu.tvarga.bakingapp;

import android.app.Activity;
import android.app.Application;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import hu.tvarga.bakingapp.dataaccess.db.DbFactory;
import hu.tvarga.bakingapp.dataaccess.network.Networking;
import hu.tvarga.bakingapp.di.DaggerAppComponent;
import timber.log.Timber;

public class BakingApp extends Application implements HasActivityInjector {

	@Inject
	Networking networking;

	@Inject
	DbFactory dbFactory;

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


	@Override
	public DispatchingAndroidInjector<Activity> activityInjector() {
		return dispatchingAndroidInjector;
	}

}


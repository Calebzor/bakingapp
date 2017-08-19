package hu.tvarga.bakingapp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import hu.tvarga.bakingapp.dataaccess.db.DbFactory;
import hu.tvarga.bakingapp.dataaccess.network.Networking;
import hu.tvarga.bakingapp.di.AppModule;
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
		DaggerAppComponent.builder().appModule(new AppModule(this)).build().inject(this);
	}

	public static BakingApp get(Context context) {
		return (BakingApp) context.getApplicationContext();
	}

	@Override
	public DispatchingAndroidInjector<Activity> activityInjector() {
		return dispatchingAndroidInjector;
	}

}


package hu.tvarga.bakingapp.utilties;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import hu.tvarga.bakingapp.BuildConfig;

public enum DispatchQueueHelper {
	MAIN_THREAD(Looper.getMainLooper()),
	BACKGROUND_THREAD(createBackgroundLooper(BuildConfig.APPLICATION_ID + "background"));

	private final Handler handler;

	private final Looper looper;

	DispatchQueueHelper(Looper looper) {
		this.looper = looper;
		this.handler = getNewHandlerInstance();
	}

	public Handler getDefaultHandler() {
		return handler;
	}

	public Handler getNewHandlerInstance() {
		return new Handler(looper);
	}

	public static void runInMainThread(Runnable runnable) {
		MAIN_THREAD.getDefaultHandler().post(runnable);
	}

	public static void runInBackgroundThread(Runnable runnable) {
		BACKGROUND_THREAD.getDefaultHandler().post(runnable);
	}

	private static synchronized Looper createBackgroundLooper(String name) {
		HandlerThread handlerThread = new HandlerThread(name);
		handlerThread.start();
		return handlerThread.getLooper();
	}
}

package hu.tvarga.bakingapp.dataaccess.network;

import javax.inject.Inject;

import okhttp3.Dispatcher;

public class DispatcherProvider {

	@Inject
	public DispatcherProvider() {
	}

	public Dispatcher getNewDispatcher() {
		return new Dispatcher();
	}
}

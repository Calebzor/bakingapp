package hu.tvarga.bakingapp.dataaccess.network;

import java.io.IOException;

public class NoNetworkException extends IOException {

	public NoNetworkException() {
		super("No network available");
	}
}

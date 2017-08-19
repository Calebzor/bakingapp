package hu.tvarga.bakingapp;

import android.os.Handler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import hu.tvarga.bakingapp.data.BakingData;
import hu.tvarga.bakingapp.dataaccess.ConnectivityHelper;
import hu.tvarga.bakingapp.dataaccess.DispatcherProvider;
import hu.tvarga.bakingapp.dataaccess.NetworkCallback;
import hu.tvarga.bakingapp.dataaccess.Networking;
import okhttp3.Call;
import okhttp3.Dispatcher;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NetworkingTest extends Assert {

	@Mock
	ConnectivityHelper connectivityHelper;

	@Mock
	Handler handler;

	@Mock
	DispatcherProvider dispatcherProvider;

	@Mock
	ExecutorService executorService;

	@Captor
	ArgumentCaptor<Runnable> runnableArgumentCaptor;

	private Networking networking;

	@Before
	public void setUp() {
		when(connectivityHelper.isNetworkAvailable()).thenReturn(true);
		when(dispatcherProvider.getNewDispatcher()).thenReturn(new Dispatcher(executorService));

		networking = new Networking(handler, connectivityHelper, dispatcherProvider);
	}

	/**
	 * Not a unit test!
	 */
	@Test
	public void simpleGet() {
		networking.get(BakingData.URL, new NetworkCallback() {
			@Override
			public void onFailure(Call call, IOException e) {
				fail("Failure is not expected");
			}

			@Override
			public void onResponse(Call call, String response) {
				assertNotNull(response);
				System.out.println(response);
			}
		});

		executeRequest();
	}

	private void executeRequest() {
		verify(executorService).execute(runnableArgumentCaptor.capture());
		runnableArgumentCaptor.getValue().run();

		verify(handler).post(runnableArgumentCaptor.capture());
		runnableArgumentCaptor.getValue().run();
	}
}

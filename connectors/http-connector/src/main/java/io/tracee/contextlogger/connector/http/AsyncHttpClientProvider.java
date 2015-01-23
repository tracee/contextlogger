package io.tracee.contextlogger.connector.http;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;

public interface AsyncHttpClientProvider {

	AsyncHttpClient provideHttpClient(AsyncHttpClientConfig config);

}

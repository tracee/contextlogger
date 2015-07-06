package io.tracee.contextlogger.contextprovider.servlet.contextprovider;

import io.tracee.contextlogger.contextprovider.api.Order;
import io.tracee.contextlogger.contextprovider.api.ProfileSettings;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderMethod;
import io.tracee.contextlogger.contextprovider.api.WrappedContextData;
import io.tracee.contextlogger.contextprovider.core.utility.NameValuePair;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Context provider for HttpServletResponse.
 * Created by Tobias Gindler, holisticon AG on 20.03.14.
 */
@TraceeContextProvider(displayName = "servletResponse", order = Order.SERVLET)
@ProfileSettings(basic = true, enhanced = true, full = true)
public final class ServletResponseContextProvider implements WrappedContextData<HttpServletResponse> {

	private HttpServletResponse response;

	public ServletResponseContextProvider() {
	}

	public ServletResponseContextProvider(final HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void setContextData(Object instance) throws ClassCastException {
		this.response = (HttpServletResponse) instance;
	}

	@Override
	public HttpServletResponse getContextData() {
		return this.response;
	}

	@Override
	public Class<HttpServletResponse> getWrappedType() {
		return HttpServletResponse.class;
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "http-status-code", order = 10)
	@ProfileSettings(basic = true, enhanced = true, full = true)
	public Integer getHttpStatusCode() {
		if (this.response != null) {
			return this.response.getStatus();
		}
		return null;
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "http-header", order = 20)
	@ProfileSettings(basic = false, enhanced = true, full = true)
	public List<NameValuePair<String>> getHttpResponseHeaders() {
		final List<NameValuePair<String>> list = new ArrayList<NameValuePair<String>>();

		if (this.response != null && this.response.getHeaderNames() != null) {
			final Collection<String> httpHeaderNames = this.response.getHeaderNames();
			for (final String httpHeaderName : httpHeaderNames) {

				final String value = this.response.getHeader(httpHeaderName);
				list.add(new NameValuePair<String>(httpHeaderName, value));

			}
		}

		return list.size() > 0 ? list : null;
	}

}

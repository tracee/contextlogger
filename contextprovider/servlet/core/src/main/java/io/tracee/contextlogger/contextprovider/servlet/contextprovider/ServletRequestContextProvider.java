package io.tracee.contextlogger.contextprovider.servlet.contextprovider;

import io.tracee.contextlogger.contextprovider.api.Order;
import io.tracee.contextlogger.contextprovider.api.ProfileConfig;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderMethod;
import io.tracee.contextlogger.contextprovider.api.WrappedContextData;
import io.tracee.contextlogger.contextprovider.core.utility.NameValuePair;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Context provider for HttpServletRequest.
 * Created by Tobias Gindler, holisticon AG on 17.03.14.
 */

@TraceeContextProvider(displayName = "servletRequest", order = Order.SERVLET)
@ProfileConfig(basic = true, enhanced = true, full = true)
public final class ServletRequestContextProvider implements WrappedContextData<HttpServletRequest> {

	private HttpServletRequest request;

	public ServletRequestContextProvider() {

	}

	public ServletRequestContextProvider(final HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setContextData(Object instance) throws ClassCastException {
		this.request = (HttpServletRequest) instance;
	}

	@Override
	public HttpServletRequest getContextData() {
		return this.request;
	}

	@Override
	public Class<HttpServletRequest> getWrappedType() {
		return HttpServletRequest.class;
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "url", order = 10)
	@ProfileConfig(basic = true, enhanced = true, full = true)
	public String getUrl() {
		if (this.request != null && request.getRequestURL() != null) {
			return request.getRequestURL().toString();
		}
		return null;
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "http-method", order = 20)
	@ProfileConfig(basic = true, enhanced = true, full = true)
	public String getHttpMethod() {
		if (this.request != null) {
			return this.request.getMethod();
		}
		return null;
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "http-parameters", order = 30)
	@ProfileConfig(basic = true, enhanced = true, full = true)
	public List<NameValuePair<String>> getHttpParameters() {

		final List<NameValuePair<String>> list = new ArrayList<NameValuePair<String>>();

		if (this.request != null) {
			final Enumeration<String> httpHeaderNamesEnum = this.request.getParameterNames();
			while (httpHeaderNamesEnum.hasMoreElements()) {

				final String httpHeaderName = httpHeaderNamesEnum.nextElement();

				final String[] values = this.request.getParameterValues(httpHeaderName);
				if (values != null) {
					for (final String value : values) {
						list.add(new NameValuePair<String>(httpHeaderName, value));
					}
				}

			}
		}

		return list.size() > 0 ? list : null;

	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "http-request-headers", order = 40)
	@ProfileConfig(basic = false, enhanced = true, full = true)
	public List<NameValuePair<String>> getHttpRequestHeaders() {

		final List<NameValuePair<String>> list = new ArrayList<NameValuePair<String>>();

		if (this.request != null) {
			final Enumeration<String> httpHeaderNamesEnum = this.request.getHeaderNames();
			while (httpHeaderNamesEnum.hasMoreElements()) {

				final String httpHeaderName = httpHeaderNamesEnum.nextElement();
				final String value = this.request.getHeader(httpHeaderName);
				list.add(new NameValuePair<String>(httpHeaderName, value));

			}
		}

		return list.size() > 0 ? list : null;
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "http-request-attributes", order = 45)
	@ProfileConfig(basic = false, enhanced = true, full = true)
	public List<NameValuePair<Object>> getHttpRequestAttributes() {

		final List<NameValuePair<Object>> list = new ArrayList<NameValuePair<Object>>();

		if (this.request != null) {
			final Enumeration<String> attributeNames = this.request.getAttributeNames();
			while (attributeNames.hasMoreElements()) {

				final String attributeName = attributeNames.nextElement();
				final Object value = this.request.getAttribute(attributeName);
				list.add(new NameValuePair<Object>(attributeName, value));

			}
		}

		return list.size() > 0 ? list : null;
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "cookies", order = 50)
	@ProfileConfig(basic = false, enhanced = true, full = true)
	public List<ServletCookieContextProvider> getCookies() {

		List<ServletCookieContextProvider> wrappedCookies = new ArrayList<ServletCookieContextProvider>();

		if (this.request != null) {
			for (Cookie cookie : request.getCookies()) {
				wrappedCookies.add(new ServletCookieContextProvider(cookie));
			}
		}

		return wrappedCookies.size() > 0 ? wrappedCookies : null;
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "http-remote-address", order = 150)
	@ProfileConfig(basic = false, enhanced = true, full = true)
	public String getHttpRemoteAddress() {
		if (this.request != null) {
			return request.getRemoteAddr();
		}
		return null;
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "http-remote-host", order = 160)
	@ProfileConfig(basic = false, enhanced = true, full = true)
	public String getHttpRemoteHost() {
		if (this.request != null) {
			return this.request.getRemoteHost();
		}
		return null;
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "http-remote-port", order = 170)
	@ProfileConfig(basic = false, enhanced = true, full = true)
	public Integer getHttpRemotePort() {
		if (this.request != null) {
			return this.request.getRemotePort();
		}
		return null;
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "scheme", order = 200)
	@ProfileConfig(basic = false, enhanced = true, full = true)
	public String getScheme() {
		if (this.request != null) {
			return this.request.getScheme();
		}

		return null;
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "is-secure", order = 210)
	@ProfileConfig(basic = false, enhanced = true, full = true)
	public Boolean getSecure() {
		if (this.request != null) {
			return this.request.isSecure();
		}

		return null;
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "content-type", order = 220)
	@ProfileConfig(basic = false, enhanced = true, full = true)
	public String getContentType() {
		if (this.request != null) {
			return this.request.getContentType();
		}

		return null;
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "content-length", order = 230)
	@ProfileConfig(basic = false, enhanced = true, full = true)
	public Integer getContentLength() {
		if (this.request != null) {
			return this.request.getContentLength();
		}
		return null;
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "locale", order = 240)
	@ProfileConfig(basic = false, enhanced = true, full = true)
	public String getLocale() {
		if (this.request != null && this.request.getLocale() != null) {
			return this.request.getLocale().toString();
		}
		return null;
	}
}

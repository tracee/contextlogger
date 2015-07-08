package io.tracee.contextlogger.contextprovider.servlet.contextprovider;

import io.tracee.contextlogger.contextprovider.api.Order;
import io.tracee.contextlogger.contextprovider.api.ProfileConfig;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderMethod;
import io.tracee.contextlogger.contextprovider.api.WrappedContextData;

import javax.servlet.http.Cookie;

/**
 * Context provider for ServletCookieContextProvider.
 * Created by Tobias Gindler, holisticon AG on 24.01.14.
 */
@TraceeContextProvider(displayName = "servletCookies", order = Order.SERVLET)
@ProfileConfig(basic = true, enhanced = true, full = true)
public final class ServletCookieContextProvider implements WrappedContextData<Cookie> {

	private Cookie cookie;

	public ServletCookieContextProvider() {
	}

	public ServletCookieContextProvider(Cookie cookie) {
		this.cookie = cookie;
	}

	public void setContextData(Object instance) throws ClassCastException {
		this.cookie = (Cookie) instance;
	}

	public Cookie getContextData() {
		return this.cookie;
	}

	public Class<Cookie> getWrappedType() {
		return Cookie.class;
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "name", order = 10)
	@ProfileConfig(basic = true, enhanced = true, full = true)
	public String getName() {
		if (cookie != null) {
			return cookie.getName();
		}
		return null;
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "value", order = 20)
	@ProfileConfig(basic = true, enhanced = true, full = true)
	public String getValue() {
		if (cookie != null) {
			return cookie.getValue();
		}
		return null;
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "domain", order = 30)
	@ProfileConfig(basic = true, enhanced = true, full = true)
	public String getDomain() {
		if (cookie != null) {
			return cookie.getDomain();
		}
		return null;
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "path", order = 40)
	@ProfileConfig(basic = true, enhanced = true, full = true)
	public String getPath() {
		if (cookie != null) {
			return cookie.getPath();
		}
		return null;
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "secure", order = 50)
	@ProfileConfig(basic = true, enhanced = true, full = true)
	public Boolean getSecure() {
		if (cookie != null) {
			return cookie.getSecure();
		}
		return null;
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "max-age", order = 60)
	@ProfileConfig(basic = true, enhanced = true, full = true)
	public Integer getMaxAge() {
		if (cookie != null) {
			return cookie.getMaxAge();
		}
		return null;
	}

}

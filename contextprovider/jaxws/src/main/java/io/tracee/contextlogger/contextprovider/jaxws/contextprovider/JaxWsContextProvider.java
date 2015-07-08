package io.tracee.contextlogger.contextprovider.jaxws.contextprovider;

import io.tracee.contextlogger.contextprovider.api.Order;
import io.tracee.contextlogger.contextprovider.api.ProfileConfig;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderMethod;
import io.tracee.contextlogger.contextprovider.api.WrappedContextData;

/**
 * JaxWsContextProvider context provider.
 */
@TraceeContextProvider(displayName = "jaxWs", order = Order.JAXWS)
@ProfileConfig(basic = true, enhanced = true, full = true)
public class JaxWsContextProvider implements WrappedContextData<JaxWsWrapper> {

	private JaxWsWrapper jaxWsWrapper;

	@Override
	public final void setContextData(Object instance) throws ClassCastException {
		this.jaxWsWrapper = (JaxWsWrapper) instance;
	}

	@Override
	public final Class<JaxWsWrapper> getWrappedType() {
		return JaxWsWrapper.class;
	}

	@Override
	public JaxWsWrapper getContextData() {
		return this.jaxWsWrapper;
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "soapRequest", order = 40)
	@ProfileConfig(basic = true, enhanced = true, full = true)
	public final String getSoapRequest() {
		if (jaxWsWrapper != null) {
			return jaxWsWrapper.getSoapRequest();
		}
		return null;
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "soapResponse", order = 50)
	@ProfileConfig(basic = true, enhanced = true, full = true)
	public final String getSoapResponse() {
		if (jaxWsWrapper != null) {
			return jaxWsWrapper.getSoapResponse();
		}
		return null;
	}
}

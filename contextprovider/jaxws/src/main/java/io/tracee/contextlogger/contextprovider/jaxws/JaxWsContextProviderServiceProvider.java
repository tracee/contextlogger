package io.tracee.contextlogger.contextprovider.jaxws;

import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderServiceProvider;
import io.tracee.contextlogger.contextprovider.jaxws.contextprovider.JaxWsContextProvider;

/**
 * Provides all javaee (CDI, ...) related service providers
 */
public class JaxWsContextProviderServiceProvider implements TraceeContextProviderServiceProvider {

	public static final Class[] IMPLICIT_CONTEXT_PROVIDER = {};
	public static final Class[] CONTEXT_PROVIDER = {JaxWsContextProvider.class};

	@Override
	public Class[] getImplicitContextProvider() {
		return IMPLICIT_CONTEXT_PROVIDER;
	}

	@Override
	public Class[] getContextProvider() {
		return CONTEXT_PROVIDER;
	}


}

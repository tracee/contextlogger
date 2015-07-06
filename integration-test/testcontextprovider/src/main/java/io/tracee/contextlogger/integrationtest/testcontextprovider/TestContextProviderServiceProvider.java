package io.tracee.contextlogger.integrationtest.testcontextprovider;

import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderServiceProvider;

/**
 * Service Provider for integrationtests.
 */
public class TestContextProviderServiceProvider implements TraceeContextProviderServiceProvider {

	public static final Class[] IMPLICIT_CONTEXT_PROVIDER = {BrokenImplicitContextProviderThatThrowsNullPointerException.class, TestImplicitContextProvider.class};
	public static final Class[] CONTEXT_PROVIDER = {BrokenContextProviderThatThrowsNullPointerException.class, TestContextDataWrapper.class};


	@Override
	public Class[] getImplicitContextProvider() {
		return IMPLICIT_CONTEXT_PROVIDER;
	}

	@Override
	public Class[] getContextProvider() {
		return CONTEXT_PROVIDER;
	}

}

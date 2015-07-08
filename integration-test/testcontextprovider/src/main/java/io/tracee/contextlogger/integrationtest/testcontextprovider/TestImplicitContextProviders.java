package io.tracee.contextlogger.integrationtest.testcontextprovider;

import io.tracee.contextlogger.contextprovider.api.ImplicitContext;

/**
 * Enum for adding implicit test contextproviders.
 */
public enum TestImplicitContextProviders implements ImplicitContext {

	BROKEN(BrokenImplicitContextProviderThatThrowsNullPointerException.class),
	TEST(TestImplicitContextProvider.class);

	private final Class implicitContextProviderClass;

	private TestImplicitContextProviders(final Class implicitContextProviderClass) {
		this.implicitContextProviderClass = implicitContextProviderClass;
	}

}

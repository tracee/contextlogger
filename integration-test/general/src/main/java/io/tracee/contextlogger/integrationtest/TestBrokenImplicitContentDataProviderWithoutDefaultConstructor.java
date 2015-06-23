package io.tracee.contextlogger.integrationtest;

import io.tracee.contextlogger.api.ImplicitContext;
import io.tracee.contextlogger.api.ImplicitContextData;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderMethod;

/**
 * Test provider that provides implicit context information but which has no args constructor.
 */
@TraceeContextProvider(displayName = "testBrokenImplicitContentDataProviderWithoutDefaultConstructor", order = 200)
public class TestBrokenImplicitContentDataProviderWithoutDefaultConstructor implements ImplicitContextData {

    public static final String PROPERTY_NAME = "io.tracee.contextlogger.integrationtest.TestBrokenImplicitContentDataProviderWithoutDefaultConstructor.output";

    TestBrokenImplicitContentDataProviderWithoutDefaultConstructor(String something) {

    }

    @SuppressWarnings("unused")
    @TraceeContextProviderMethod(displayName = "output", order = 10)
    public final String getOutput() {
        throw new NullPointerException("Whoops!!!");
    }

	@Override
	public ImplicitContext getImplicitContext() {
		return null;
	}
}

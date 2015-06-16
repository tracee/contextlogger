package io.tracee.contextlogger.integrationtest;

import io.tracee.contextlogger.api.ImplicitContext;
import io.tracee.contextlogger.api.ImplicitContextData;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderMethod;

/**
 * Test provider that provides implicit context information that triggers an exception during deserialization.
 */
@TraceeContextProvider(displayName = "testBrokenImplicitContextData", order = 200)
public class TestBrokenImplicitContextDataProvider implements ImplicitContextData {

    public static final String PROPERTY_NAME = "io.tracee.contextlogger.integrationtest.TestBrokenImplicitContextDataProvider.output";

    @SuppressWarnings("unused")
    @TraceeContextProviderMethod(displayName = "output", order = 10)
    public final String getOutput() {
        throw new NullPointerException("Whoops!!!");
    }

    @SuppressWarnings("unused")
    @TraceeContextProviderMethod(displayName = "workingOutput", order = 20)
    public final String getWorkingOutput() {
        return "IT_WORKS";
    }

	@Override
	public ImplicitContext getImplicitContext() {
        return null;
	}
}

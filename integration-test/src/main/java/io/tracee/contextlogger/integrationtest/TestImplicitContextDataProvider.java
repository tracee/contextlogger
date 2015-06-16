package io.tracee.contextlogger.integrationtest;

import io.tracee.contextlogger.api.ImplicitContext;
import io.tracee.contextlogger.api.ImplicitContextData;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderMethod;

/**
 * Test provider that provides implicit context information.
 */
@TraceeContextProvider(displayName = "testImplicitContextData", order = 200)
public class TestImplicitContextDataProvider implements ImplicitContextData{

    public static final String PROPERTY_NAME = "io.tracee.contextlogger.integrationtest.TestImplicitContextDataProvider.output";
    public static final String OUTPUT = "IT WORKS TOO!!!";

    @SuppressWarnings("unused")
    @TraceeContextProviderMethod(displayName = "output", order = 10)
    public final String getOutput() {
        return OUTPUT;
    }

	@Override
	public ImplicitContext getImplicitContext() {
		return null;
	}
}

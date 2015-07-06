package io.tracee.contextlogger.integrationtest.testcontextprovider;


import io.tracee.contextlogger.contextprovider.api.ImplicitContext;
import io.tracee.contextlogger.contextprovider.api.ImplicitContextData;
import io.tracee.contextlogger.contextprovider.api.ProfileSettings;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderMethod;

/**
 * Test provider that provides implicit context information.
 */


@TraceeContextProvider(displayName = "testImplicitContextData", order = 200)
@ProfileSettings(basic = false, enhanced = true)
public class TestImplicitContextProvider implements ImplicitContextData {


	public static final String PROPERTY_NAME = "io.tracee.contextlogger.integrationtest.testcontextprovider.TestImplicitContextProvider.output";
	public static final String OUTPUT = "IT WORKS TOO!!!";

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "output", order = 10)
	@ProfileSettings(basic = false, enhanced = true)
	public final String getOutput() {
		return OUTPUT;
	}

	@Override
	public ImplicitContext getImplicitContext() {
		return null;
	}
}


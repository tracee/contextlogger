package io.tracee.contextlogger.integrationtest.testcontextprovider;

import io.tracee.contextlogger.contextprovider.api.ProfileSettings;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderMethod;
import io.tracee.contextlogger.contextprovider.api.WrappedContextData;

/**
 * Broken context data wrapper that throws a NullPointerException at deserialization.
 */
@TraceeContextProvider(displayName = "brokenCustomContextDataWrapper", order = 50)
@ProfileSettings(basic = true, enhanced = true)
public class BrokenContextProviderThatThrowsNullPointerException implements WrappedContextData<WrappedBrokenTestContextData> {

	public static final String PROPERTY_NAME = "io.tracee.contextlogger.integrationtest.BrokenCustomContextDataWrapper.output";

	private WrappedBrokenTestContextData contextData;

	public BrokenContextProviderThatThrowsNullPointerException() {

	}

	public BrokenContextProviderThatThrowsNullPointerException(final WrappedBrokenTestContextData contextData) {
		this.contextData = contextData;
	}

	@Override
	public void setContextData(Object instance) throws ClassCastException {
		this.contextData = (WrappedBrokenTestContextData) instance;
	}

	@Override
	public WrappedBrokenTestContextData getContextData() {
		return this.contextData;
	}

	@Override
	public Class<WrappedBrokenTestContextData> getWrappedType() {
		return WrappedBrokenTestContextData.class;
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "testoutput", order = 10)
	@ProfileSettings(basic = false, enhanced = true)
	public String getOutput() {
		throw new NullPointerException("Whoops!!!");
	}
}

package io.tracee.contextlogger.output.internal.testclasses;

import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderMethod;
import io.tracee.contextlogger.contextprovider.api.WrappedContextData;

/**
 * Test class for tracee context provider.
 */

@SuppressWarnings("unused")
@TraceeContextProvider(displayName = "dummy", order = 60)
public class TraceeContextProviderTestClass implements WrappedContextData<String> {

    private String dummyString;

    @SuppressWarnings("unused")
    public TraceeContextProviderTestClass() {
    }

    @SuppressWarnings("unused")
    public TraceeContextProviderTestClass(final String dummyString) {
        this.dummyString = dummyString;
    }

    @Override
    public void setContextData(Object instance) throws ClassCastException {
        this.dummyString = (String)instance;
    }

    public Class<String> getWrappedType() {
        return String.class;
    }

    @SuppressWarnings("unused")
    @TraceeContextProviderMethod(displayName = "dummyString", order = 10)
    public String getMessage() {
        return dummyString != null ? dummyString : null;
    }

}

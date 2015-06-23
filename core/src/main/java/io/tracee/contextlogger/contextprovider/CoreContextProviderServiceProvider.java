package io.tracee.contextlogger.contextprovider;

import io.tracee.contextlogger.contextprovider.api.AbstractTraceeContextProviderServiceProvider;
import io.tracee.contextlogger.contextprovider.java.JavaThrowableContextProvider;
import io.tracee.contextlogger.contextprovider.tracee.CommonDataContextProvider;
import io.tracee.contextlogger.contextprovider.tracee.TraceeMdcContextProvider;
import io.tracee.contextlogger.contextprovider.tracee.TraceeMessageContextProvider;
import io.tracee.contextlogger.contextprovider.utility.NameValuePair;

/**
 * Service provider for all core context providers.
 */
public class CoreContextProviderServiceProvider extends AbstractTraceeContextProviderServiceProvider {

    public static final Class[] IMPLICIT_CONTEXT_PROVIDER = { CommonDataContextProvider.class, TraceeMdcContextProvider.class };
    public static final Class[] CONTEXT_PROVIDER = { JavaThrowableContextProvider.class, TraceeMdcContextProvider.class, NameValuePair.class,
        TraceeMessageContextProvider.class };

    @Override
    public Class[] getImplicitContextProvider() {
        return IMPLICIT_CONTEXT_PROVIDER;
    }

    @Override
    public Class[] getContextProvider() {
        return CONTEXT_PROVIDER;
    }

    @Override
    protected String getPropertyFilePrefix() {
        return "Core";
    }
}

package io.tracee.contextlogger.contextprovider.javaee;

import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderServiceProvider;
import io.tracee.contextlogger.contextprovider.javaee.contextprovider.InvocationContextContextProvider;

/**
 * Provides all javaee (CDI, ...) related service providers
 */
public class JavaeeContextProviderServiceProvider implements TraceeContextProviderServiceProvider {

    public static final Class[] IMPLICIT_CONTEXT_PROVIDER = {};
    public static final Class[] CONTEXT_PROVIDER = { InvocationContextContextProvider.class };

    @Override
    public Class[] getImplicitContextProvider() {
        return IMPLICIT_CONTEXT_PROVIDER;
    }

    @Override
    public Class[] getContextProvider() {
        return CONTEXT_PROVIDER;
    }
}

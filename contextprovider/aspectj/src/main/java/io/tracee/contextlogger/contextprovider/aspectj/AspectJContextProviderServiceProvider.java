package io.tracee.contextlogger.contextprovider.aspectj;

import io.tracee.contextlogger.contextprovider.api.AbstractTraceeContextProviderServiceProvider;
import io.tracee.contextlogger.contextprovider.aspectj.contextprovider.AspectjProceedingJoinPointContextProvider;
import io.tracee.contextlogger.contextprovider.aspectj.contextprovider.WatchdogContextProvider;

/**
 * Provides all aspectj related service providers
 */
public class AspectJContextProviderServiceProvider extends AbstractTraceeContextProviderServiceProvider {

    public static final Class[] IMPLICIT_CONTEXT_PROVIDER = {};
    public static final Class[] CONTEXT_PROVIDER = { AspectjProceedingJoinPointContextProvider.class, WatchdogContextProvider.class };

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
        return "AspectJ";
    }
}

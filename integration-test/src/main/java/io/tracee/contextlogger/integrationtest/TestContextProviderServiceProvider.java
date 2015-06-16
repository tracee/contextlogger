package io.tracee.contextlogger.integrationtest;

import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderServiceProvider;

/**
 * Service Provider for integrationtests.
 */
public class TestContextProviderServiceProvider implements TraceeContextProviderServiceProvider {

    public static final Class[] IMPLICIT_CONTEXT_PROVIDER = { TestImplicitContextDataProvider.class, TestBrokenImplicitContextDataProvider.class,
        TestBrokenImplicitContentDataProviderWithoutDefaultConstructor.class };
    public static final Class[] CONTEXT_PROVIDER = { TestContextDataWrapper.class, BrokenCustomContextDataWrapper.class };

    @Override
    public Class[] getImplicitContextProvider() {
        return IMPLICIT_CONTEXT_PROVIDER;
    }

    @Override
    public Class[] getContextProvider() {
        return CONTEXT_PROVIDER;
    }
}

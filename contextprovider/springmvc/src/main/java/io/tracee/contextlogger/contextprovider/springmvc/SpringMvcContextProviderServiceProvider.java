package io.tracee.contextlogger.contextprovider.springmvc;

import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderServiceProvider;
import io.tracee.contextlogger.contextprovider.springmvc.contextprovider.HandlerMethodContextProvider;
import io.tracee.contextlogger.contextprovider.springmvc.contextprovider.MethodParameterContextProvider;

/**
 * Provides all javaee (CDI, ...) related service providers
 */
public class SpringMvcContextProviderServiceProvider implements TraceeContextProviderServiceProvider {

    public static final Class[] IMPLICIT_CONTEXT_PROVIDER = {};
    public static final Class[] CONTEXT_PROVIDER = { HandlerMethodContextProvider.class, MethodParameterContextProvider.class };

    @Override
    public Class[] getImplicitContextProvider() {
        return IMPLICIT_CONTEXT_PROVIDER;
    }

    @Override
    public Class[] getContextProvider() {
        return CONTEXT_PROVIDER;
    }
}

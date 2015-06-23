package io.tracee.contextlogger.contextprovider.servlet.contextprovider;

import io.tracee.contextlogger.contextprovider.api.AbstractTraceeContextProviderServiceProvider;

/**
 * Provides all javaee (CDI, ...) related service providers
 */
public class ServletContextProviderServiceProvider extends AbstractTraceeContextProviderServiceProvider {

    public static final Class[] IMPLICIT_CONTEXT_PROVIDER = {};
    public static final Class[] CONTEXT_PROVIDER = { ServletCookieContextProvider.class, ServletRequestContextProvider.class,
        ServletResponseContextProvider.class, ServletSessionContextProvider.class };

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
        return "Servlet";
    }
}

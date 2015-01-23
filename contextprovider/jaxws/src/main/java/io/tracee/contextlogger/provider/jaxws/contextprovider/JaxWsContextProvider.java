package io.tracee.contextlogger.provider.jaxws.contextprovider;

import io.tracee.contextlogger.contextprovider.Order;
import io.tracee.contextlogger.provider.api.TraceeContextProvider;
import io.tracee.contextlogger.provider.api.TraceeContextProviderMethod;
import io.tracee.contextlogger.provider.api.WrappedContextData;

/**
 * JaxWsContextProvider context provider.
 */
@TraceeContextProvider(displayName = "jaxWs", order = Order.JAXWS)
public class JaxWsContextProvider implements WrappedContextData<JaxWsWrapper> {

    private JaxWsWrapper jaxWsWrapper;

    @Override
    public final void setContextData(Object instance) throws ClassCastException {
        this.jaxWsWrapper = (JaxWsWrapper)instance;
    }

    @Override
    public final Class<JaxWsWrapper> getWrappedType() {
        return JaxWsWrapper.class;
    }

    @SuppressWarnings("unused")
    @TraceeContextProviderMethod(displayName = "soapRequest", order = 40)
    public final String getSoapRequest() {
        if (jaxWsWrapper != null) {
            return jaxWsWrapper.getSoapRequest();
        }
        return null;
    }

    @SuppressWarnings("unused")
    @TraceeContextProviderMethod(displayName = "soapResponse", order = 50)
    public final String getSoapResponse() {
        if (jaxWsWrapper != null) {
            return jaxWsWrapper.getSoapResponse();
        }
        return null;
    }
}

package io.tracee.contextlogger.contextprovider.jaxws;

import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 * JaxWs client side handler that detects uncaught exceptions and outputs contextual information.
 */
public class TraceeClientErrorLoggingHandler extends AbstractTraceeErrorLoggingHandler {

    @Override
    protected final void handleIncoming(SOAPMessageContext context) {
        // Do nothing
    }

    @Override
    protected final void handleOutgoing(SOAPMessageContext context) {
        storeMessageInThreadLocal(context);
    }

}

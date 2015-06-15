package io.tracee.contextlogger.contextprovider.jaxws;

import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 * JaxWs container side handler that detects uncaught exceptions and outputs contextual information.
 */
public class TraceeServerErrorLoggingHandler extends AbstractTraceeErrorLoggingHandler {

    @Override
    protected final void handleIncoming(SOAPMessageContext context) {
        storeMessageInThreadLocal(context);
    }

    @Override
    protected final void handleOutgoing(SOAPMessageContext context) {
        // Do nothing
    }
}

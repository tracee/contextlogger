package io.tracee.contextlogger.contextprovider.jaxws;

import javax.xml.ws.handler.soap.SOAPMessageContext;

import io.tracee.Tracee;
import io.tracee.TraceeBackend;

/**
 * JaxWs client side handler that detects uncaught exceptions and outputs contextual information.
 */
public class TraceeClientErrorLoggingHandler extends AbstractTraceeErrorLoggingHandler {

    TraceeClientErrorLoggingHandler(TraceeBackend traceeBackend) {
        super(traceeBackend);
    }

    public TraceeClientErrorLoggingHandler() {
        this(Tracee.getBackend());
    }

    @Override
    protected final void handleIncoming(SOAPMessageContext context) {
        // Do nothing
    }

    @Override
    protected final void handleOutgoing(SOAPMessageContext context) {
        storeMessageInThreadLocal(context);
    }

}

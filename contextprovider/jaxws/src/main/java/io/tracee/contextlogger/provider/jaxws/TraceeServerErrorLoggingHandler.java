package io.tracee.contextlogger.provider.jaxws;

import javax.xml.ws.handler.soap.SOAPMessageContext;

import io.tracee.Tracee;
import io.tracee.TraceeBackend;

/**
 * JaxWs container side handler that detects uncaught exceptions and outputs contextual information.
 */
public class TraceeServerErrorLoggingHandler extends AbstractTraceeErrorLoggingHandler {

    TraceeServerErrorLoggingHandler(TraceeBackend traceeBackend) {
        super(traceeBackend);
    }

    public TraceeServerErrorLoggingHandler() {
        this(Tracee.getBackend());
    }

    @Override
    protected final void handleIncoming(SOAPMessageContext context) {
        storeMessageInThreadLocal(context);
    }

    @Override
    protected final void handleOutgoing(SOAPMessageContext context) {
        // Do nothing
    }
}

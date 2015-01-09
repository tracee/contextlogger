package io.tracee.contextlogger.jaxws.container;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import io.tracee.Tracee;
import io.tracee.TraceeBackend;
import io.tracee.TraceeLogger;
import io.tracee.contextlogger.MessagePrefixProvider;
import io.tracee.contextlogger.TraceeContextLogger;
import io.tracee.contextlogger.api.ImplicitContext;
import io.tracee.contextlogger.api.internal.MessageLogLevel;
import io.tracee.contextlogger.contextprovider.jaxws.JaxWsWrapper;
import io.tracee.jaxws.AbstractTraceeHandler;
import io.tracee.jaxws.container.TraceeServerHandler;

/**
 * Abstract base class for detecting JAX-WS related uncaught exceptions and outputting of contextual information.
 */
public abstract class AbstractTraceeErrorLoggingHandler extends AbstractTraceeHandler {

    private final TraceeLogger logger = traceeBackend.getLoggerFactory().getLogger(
            TraceeServerHandler.class);

    protected static final ThreadLocal<String> THREAD_LOCAL_SOAP_MESSAGE_STR = new ThreadLocal<String>();

    AbstractTraceeErrorLoggingHandler(TraceeBackend traceeBackend) {
        super(traceeBackend);
    }

    public AbstractTraceeErrorLoggingHandler() {
        this(Tracee.getBackend());
    }

    @Override
    public final boolean handleFault(SOAPMessageContext context) {

        // Must pipe out Soap envelope
        SOAPMessage soapMessage = context.getMessage();

        TraceeContextLogger.createDefault().logJsonWithPrefixedMessage(MessagePrefixProvider.provideLogMessagePrefix(MessageLogLevel.ERROR, "JAX-WS"),
                ImplicitContext.COMMON, ImplicitContext.TRACEE,
                JaxWsWrapper.wrap(THREAD_LOCAL_SOAP_MESSAGE_STR.get(), convertSoapMessageAsString(soapMessage)));

        return true;
    }

    /**
     * Converts a SOAPMessage instance to string representation.
     */
    String convertSoapMessageAsString(SOAPMessage soapMessage) {
        if (soapMessage == null) {
            return "null";
        }
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            soapMessage.writeTo(os);
            return new String(os.toByteArray(), determineMessageEncoding(soapMessage));
        }
        catch (Exception e) {
            logger.error("Couldn't create string representation of soapMessage: " + soapMessage.toString());
            return "ERROR";
        }
    }

    Charset determineMessageEncoding(SOAPMessage soapMessage) {
        try {
            final Object encProp = soapMessage.getProperty(SOAPMessage.CHARACTER_SET_ENCODING);
            if (encProp != null) {
                return Charset.forName(String.valueOf(encProp));
            }
            return Charset.forName("UTF-8");
        }
        catch (Exception e) {
            return Charset.forName("UTF-8");
        }
    }

    protected void storeMessageInThreadLocal(SOAPMessageContext context) {

        // Save soap request message in thread local storage for error logging
        SOAPMessage soapMessage = context.getMessage();
        if (soapMessage != null) {
            String soapMessageAsString = convertSoapMessageAsString(soapMessage);
            THREAD_LOCAL_SOAP_MESSAGE_STR.set(soapMessageAsString);
        }
    }

    @Override
    public void close(MessageContext context) {
        super.close(context);
        // cleanup thread local request soap message
        THREAD_LOCAL_SOAP_MESSAGE_STR.remove();
    }

    @Override
    public final Set<QName> getHeaders() {
        return null;
    }

}

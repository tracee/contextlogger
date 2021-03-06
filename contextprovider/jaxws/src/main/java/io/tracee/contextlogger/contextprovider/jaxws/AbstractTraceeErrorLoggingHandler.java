package io.tracee.contextlogger.contextprovider.jaxws;

import io.tracee.contextlogger.MessagePrefixProvider;
import io.tracee.contextlogger.TraceeContextLogger;
import io.tracee.contextlogger.api.internal.MessageLogLevel;
import io.tracee.contextlogger.contextprovider.core.CoreImplicitContextProviders;
import io.tracee.contextlogger.contextprovider.jaxws.contextprovider.JaxWsWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.Set;

/**
 * Abstract base class for detecting JAX-WS related uncaught exceptions and outputting of contextual information.
 */
public abstract class AbstractTraceeErrorLoggingHandler implements SOAPHandler<SOAPMessageContext> {

	private static final Logger logger = LoggerFactory.getLogger(AbstractTraceeErrorLoggingHandler.class);

	protected static final ThreadLocal<String> THREAD_LOCAL_SOAP_MESSAGE_STR = new ThreadLocal<String>();

	/**
	 * The constructor.
	 */
	AbstractTraceeErrorLoggingHandler() {
	}

	@Override
	public final boolean handleMessage(final SOAPMessageContext context) {
		if (this.isOutgoing(context)) {
			this.handleOutgoing(context);
		} else {
			this.handleIncoming(context);
		}
		return true;
	}

	@Override
	public final boolean handleFault(SOAPMessageContext context) {

		// Must pipe out Soap envelope
		SOAPMessage soapMessage = context.getMessage();

		TraceeContextLogger
				.create()
				.enforceOrder()
				.apply()
				.logWithPrefixedMessage(MessagePrefixProvider.provideLogMessagePrefix(MessageLogLevel.ERROR, "JAX-WS"), CoreImplicitContextProviders.COMMON,
						CoreImplicitContextProviders.TRACEE, JaxWsWrapper.wrap(THREAD_LOCAL_SOAP_MESSAGE_STR.get(), convertSoapMessageAsString(soapMessage)));

		return true;
	}

	@Override
	public void close(MessageContext context) {
		// cleanup thread local request soap message
		THREAD_LOCAL_SOAP_MESSAGE_STR.remove();
	}

	@Override
	public final Set<QName> getHeaders() {
		return null;
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
		} catch (Exception e) {
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
		} catch (Exception e) {
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

	protected abstract void handleIncoming(SOAPMessageContext context);

	protected abstract void handleOutgoing(SOAPMessageContext context);

	/**
	 * Checks whether is is an incoming or outgoing call.
	 *
	 * @param messageContext the message context
	 * @return true if is outgoing call, otherwise false
	 */
	private boolean isOutgoing(MessageContext messageContext) {
		Object outboundBoolean = messageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		return outboundBoolean != null && (Boolean) outboundBoolean;
	}

}

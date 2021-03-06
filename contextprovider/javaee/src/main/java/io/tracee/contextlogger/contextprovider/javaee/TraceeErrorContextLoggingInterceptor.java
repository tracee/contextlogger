package io.tracee.contextlogger.contextprovider.javaee;

import io.tracee.contextlogger.MessagePrefixProvider;
import io.tracee.contextlogger.TraceeContextLogger;
import io.tracee.contextlogger.api.ErrorMessage;
import io.tracee.contextlogger.api.internal.MessageLogLevel;
import io.tracee.contextlogger.contextprovider.core.CoreImplicitContextProviders;
import io.tracee.contextlogger.contextprovider.core.tracee.TraceeMessage;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * Message listener to detect exceptions that happened during javaee message processing.
 * In case of an exception contextual information will be written to the log.
 * Created by Tobias Gindler, holisticon AG on 13.03.14.
 */
public class TraceeErrorContextLoggingInterceptor {

	@SuppressWarnings("unused")
	public TraceeErrorContextLoggingInterceptor() {
	}

	@AroundInvoke
	public Object intercept(final InvocationContext ctx) throws Exception {
		try {
			return ctx.proceed();
		} catch (Exception e) {

			// try to get error message annotation
			ErrorMessage errorMessage = ctx.getMethod().getAnnotation(ErrorMessage.class);

			// now log context informations
			if (errorMessage == null) {
				TraceeContextLogger
						.create()
						.enforceOrder()
						.apply()
						.logWithPrefixedMessage(
								MessagePrefixProvider.provideLogMessagePrefix(MessageLogLevel.ERROR, TraceeErrorContextLoggingInterceptor.class),
								CoreImplicitContextProviders.COMMON, CoreImplicitContextProviders.TRACEE, ctx, e);
			} else {
				TraceeContextLogger
						.create()
						.enforceOrder()
						.apply()
						.logWithPrefixedMessage(
								MessagePrefixProvider.provideLogMessagePrefix(MessageLogLevel.ERROR, TraceeErrorContextLoggingInterceptor.class),
								TraceeMessage.wrap(errorMessage.value()), CoreImplicitContextProviders.COMMON, CoreImplicitContextProviders.TRACEE, ctx, e);
			}
			throw e;
		}
	}
}

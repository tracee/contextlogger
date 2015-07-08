package io.tracee.contextlogger.contextprovider.aspectj;

import io.tracee.contextlogger.MessagePrefixProvider;
import io.tracee.contextlogger.TraceeContextLogger;
import io.tracee.contextlogger.api.ErrorMessage;
import io.tracee.contextlogger.api.internal.MessageLogLevel;
import io.tracee.contextlogger.contextprovider.aspectj.contextprovider.WatchdogDataWrapper;
import io.tracee.contextlogger.contextprovider.aspectj.util.WatchdogUtils;
import io.tracee.contextlogger.contextprovider.core.CoreImplicitContextProviders;
import io.tracee.contextlogger.contextprovider.core.tracee.TraceeMessage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Watchdog Assert class.
 * This aspect logs method calls of Watchdog annotated classes and methods in case of an exception is thrown during the execution of the method.
 * <p>
 * Created by Tobias Gindler, holisticon AG on 16.02.14.
 */

@Aspect
public class WatchdogAspect {

	private static final Logger logger = LoggerFactory.getLogger(WatchdogAspect.class);

	private final boolean active;

	public WatchdogAspect() {
		this(Boolean.valueOf(System.getProperty(Constants.SYSTEM_PROPERTY_IS_ACTIVE, "true")));
	}

	WatchdogAspect(boolean active) {
		this.active = active;
	}

	@SuppressWarnings("unused")
	@Pointcut("(execution(* *(..)) && @annotation(io.tracee.contextlogger.contextprovider.aspectj.Watchdog))")
	void withinWatchdogAnnotatedMethods() {
	}

	@SuppressWarnings("unused")
	@Pointcut("within(@io.tracee.contextlogger.contextprovider.aspectj.Watchdog *)")
	void withinClassWithWatchdogAnnotation() {

	}

	@SuppressWarnings("unused")
	@Pointcut("execution(public * *(..))")
	void publicMethods() {

	}

	@SuppressWarnings("unused")
	@Around("withinWatchdogAnnotatedMethods() || (publicMethods() && withinClassWithWatchdogAnnotation()) ")
	public Object guard(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		try {
			return proceedingJoinPoint.proceed();
		} catch (final Throwable e) {

			// check if watchdog processing is flagged as active
			if (active) {

				// make sure that original exception will be passed through
				try {

					// get watchdog annotation
					Watchdog watchdog = WatchdogUtils.getWatchdogAnnotation(proceedingJoinPoint);

					// check if watchdog aspect processing is deactivated by annotation
					if (WatchdogUtils.checkProcessWatchdog(watchdog, proceedingJoinPoint, e)) {

						String annotatedId = watchdog.id().isEmpty() ? null : watchdog.id();
						sendErrorReportToConnectors(proceedingJoinPoint, annotatedId, e);

					}

				} catch (Throwable error) {
					// will be ignored
					logger.error("error", error);
				}
			}
			// rethrow exception
			throw e;
		}

	}

	/**
	 * Sends the error reports to all connectors.
	 *
	 * @param proceedingJoinPoint the aspectj calling context
	 * @param annotatedId         the id defined in the watchdog annotation
	 */
	void sendErrorReportToConnectors(ProceedingJoinPoint proceedingJoinPoint, String annotatedId, Throwable e) {

		// try to get error message annotation
		ErrorMessage errorMessage = WatchdogUtils.getErrorMessageAnnotation(proceedingJoinPoint);

		if (errorMessage == null) {
			TraceeContextLogger
					.create()
					.enforceOrder()
					.apply()
					.logWithPrefixedMessage(MessagePrefixProvider.provideLogMessagePrefix(MessageLogLevel.ERROR, Watchdog.class), CoreImplicitContextProviders.COMMON,
							CoreImplicitContextProviders.TRACEE, WatchdogDataWrapper.wrap(annotatedId, proceedingJoinPoint), e);
		} else {
			TraceeContextLogger
					.create()
					.enforceOrder()
					.apply()
					.logWithPrefixedMessage(MessagePrefixProvider.provideLogMessagePrefix(MessageLogLevel.ERROR, Watchdog.class),
							TraceeMessage.wrap(errorMessage.value()), CoreImplicitContextProviders.COMMON, CoreImplicitContextProviders.TRACEE,
							WatchdogDataWrapper.wrap(annotatedId, proceedingJoinPoint), e);
		}
	}

}

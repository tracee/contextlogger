package io.tracee.contextlogger.contextprovider.aspectj.contextprovider;

import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;

import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderMethod;
import io.tracee.contextlogger.contextprovider.api.WrappedContextData;

/**
 * Context provider for ProceedingJoinPoint.
 * Created by Tobias Gindler, holisticon AG on 20.03.14.
 */
@TraceeContextProvider(displayName = "proceedingJoinPoint")
public class AspectjProceedingJoinPointContextProvider implements WrappedContextData<ProceedingJoinPoint> {

	private ProceedingJoinPoint proceedingJoinPoint;

	@SuppressWarnings("unused")
	public AspectjProceedingJoinPointContextProvider() {
	}

	@SuppressWarnings("unused")
	public AspectjProceedingJoinPointContextProvider(final ProceedingJoinPoint proceedingJoinPoint) {
		this.proceedingJoinPoint = proceedingJoinPoint;
	}

	@Override
	public final void setContextData(Object instance) throws ClassCastException {
		this.proceedingJoinPoint = (ProceedingJoinPoint) instance;
	}

	@Override
	public ProceedingJoinPoint getContextData() {
		return this.proceedingJoinPoint;
	}

	public final Class<ProceedingJoinPoint> getWrappedType() {
		return ProceedingJoinPoint.class;
	}

	public static AspectjProceedingJoinPointContextProvider wrap(final ProceedingJoinPoint proceedingJoinPoint) {
		return new AspectjProceedingJoinPointContextProvider(proceedingJoinPoint);
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "class", order = 20)
	public final String getClazz() {
		if (proceedingJoinPoint != null && proceedingJoinPoint.getSignature() != null) {
			return proceedingJoinPoint.getSignature().getDeclaringTypeName();
		}
		return null;
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "method", order = 30)
	public final String getMethod() {
		if (proceedingJoinPoint != null && proceedingJoinPoint.getSignature() != null) {
			return proceedingJoinPoint.getSignature().getName();
		}
		return null;
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "parameters", order = 40)
	public final List<Object> getParameters() {

		if (proceedingJoinPoint != null && proceedingJoinPoint.getArgs() != null) {
			// output parameters
			return Arrays.asList(proceedingJoinPoint.getArgs());
		}
		return null;
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "serialized-target-instance", order = 50)
	public final Object getSerializedTargetInstance() {
		if (proceedingJoinPoint != null) {
			// output invoked instance
			return proceedingJoinPoint.getTarget();

		}

		return null;
	}

}

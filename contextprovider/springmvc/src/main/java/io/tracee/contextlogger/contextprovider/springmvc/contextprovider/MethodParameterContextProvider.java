package io.tracee.contextlogger.contextprovider.springmvc.contextprovider;

import io.tracee.contextlogger.contextprovider.api.ProfileConfig;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderMethod;
import io.tracee.contextlogger.contextprovider.api.WrappedContextData;
import org.springframework.core.MethodParameter;

/**
 * Context provider dor {@link MethodParameter}.
 */
@TraceeContextProvider(displayName = "methodParameter")
@ProfileConfig(basic = true, enhanced = true, full = true)
public class MethodParameterContextProvider implements WrappedContextData<MethodParameter> {

	private MethodParameter methodParameter;

	@SuppressWarnings("unused")
	public MethodParameterContextProvider() {
	}

	@SuppressWarnings("unused")
	public MethodParameterContextProvider(final MethodParameter methodParameter) {
		this.methodParameter = methodParameter;
	}

	@Override
	public final void setContextData(Object instance) throws ClassCastException {
		this.methodParameter = (MethodParameter) instance;
	}

	@Override
	public MethodParameter getContextData() {
		return this.methodParameter;
	}

	public final Class<MethodParameter> getWrappedType() {
		return MethodParameter.class;
	}

	public static MethodParameterContextProvider wrap(final MethodParameter methodParameter) {
		return new MethodParameterContextProvider(methodParameter);
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "type", order = 20)
	@ProfileConfig(basic = true, enhanced = true, full = true)
	public final String getType() {
		if (methodParameter != null && methodParameter.getParameterType() != null) {
			return methodParameter.getParameterType().getCanonicalName();
		}
		return null;
	}

}

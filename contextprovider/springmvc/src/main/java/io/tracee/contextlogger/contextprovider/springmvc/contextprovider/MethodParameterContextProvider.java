package io.tracee.contextlogger.contextprovider.springmvc.contextprovider;

import org.springframework.core.MethodParameter;

import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderMethod;
import io.tracee.contextlogger.contextprovider.api.WrappedContextData;

/**
 * Context provider dor {@link MethodParameter}.
 */
@TraceeContextProvider(displayName = "methodParameter")
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
        this.methodParameter = (MethodParameter)instance;
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
    public final String getType() {
        if (methodParameter != null && methodParameter.getParameterType() != null) {
            return methodParameter.getParameterType().getCanonicalName();
        }
        return null;
    }

}

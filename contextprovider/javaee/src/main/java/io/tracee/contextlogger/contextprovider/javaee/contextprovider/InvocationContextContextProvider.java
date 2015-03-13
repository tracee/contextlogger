package io.tracee.contextlogger.contextprovider.javaee.contextprovider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.interceptor.InvocationContext;

import io.tracee.contextlogger.contextprovider.Order;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderMethod;
import io.tracee.contextlogger.contextprovider.api.WrappedContextData;
import io.tracee.contextlogger.contextprovider.utility.NameValuePair;

/**
 * Context provider for ProceedingJoinPoint.
 * Created by Tobias Gindler, holisticon AG on 20.03.14.
 */
@TraceeContextProvider(displayName = "invocationContext", order = Order.EJB)
public class InvocationContextContextProvider implements WrappedContextData<InvocationContext> {

    private InvocationContext invocationContext;

    public InvocationContextContextProvider() {
    }

    public InvocationContextContextProvider(final InvocationContext invocationContext) {
        this.invocationContext = invocationContext;
    }

    @Override
    public final void setContextData(Object instance) throws ClassCastException {
        this.invocationContext = (InvocationContext)instance;
    }

    @Override
    public InvocationContext getContextData() {
        return this.invocationContext;
    }

    @Override
    public final Class<InvocationContext> getWrappedType() {
        return InvocationContext.class;
    }

    @TraceeContextProviderMethod(displayName = "typeName", order = 0)
    public final String getTypeName() {
        return this.invocationContext != null && this.invocationContext.getTarget() != null && this.invocationContext.getTarget().getClass() != null
                ? this.invocationContext.getTarget().getClass().getCanonicalName() : null;
    }

    @TraceeContextProviderMethod(displayName = "methodName", order = 10)
    public final String getMethodName() {
        return this.invocationContext != null && this.invocationContext.getMethod() != null ? this.invocationContext.getMethod().getName() : null;
    }

    @TraceeContextProviderMethod(displayName = "parameters", order = 20)
    public final List<String> getParameters() {

        List<String> result = new ArrayList<String>();

        if (this.invocationContext != null) {
            for (Object parameter : invocationContext.getParameters()) {

                result.add(parameter != null ? parameter.toString() : null);

            }
        }

        return result.size() > 0 ? result : null;
    }

    @TraceeContextProviderMethod(displayName = "target-instance", order = 30)
    public final Object getTargetInstance() {
        String result = null;
        if (this.invocationContext != null) {
            return this.invocationContext.getTarget();

        }

        return result;
    }

    @TraceeContextProviderMethod(displayName = "invocationContextData", order = 40)
    public final List<NameValuePair<Object>> getInvocationContextData() {

        List<NameValuePair<Object>> result = new ArrayList<NameValuePair<Object>>();

        if (this.invocationContext != null) {

            for (Map.Entry<String, Object> entry : this.invocationContext.getContextData().entrySet()) {

                String key = entry.getKey();
                Object value = entry.getValue();

                result.add(new NameValuePair<Object>(key, value));

            }

        }

        return result.size() > 0 ? result : null;

    }
}

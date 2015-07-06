package io.tracee.contextlogger.contextprovider.javaee.contextprovider;

import io.tracee.contextlogger.contextprovider.api.Order;
import io.tracee.contextlogger.contextprovider.api.ProfileSettings;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderMethod;
import io.tracee.contextlogger.contextprovider.api.WrappedContextData;
import io.tracee.contextlogger.contextprovider.core.utility.NameValuePair;

import javax.interceptor.InvocationContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Context provider for ProceedingJoinPoint.
 * Created by Tobias Gindler, holisticon AG on 20.03.14.
 */
@TraceeContextProvider(displayName = "invocationContext", order = Order.EJB)
@ProfileSettings(basic = true, enhanced = true, full = true)
public class InvocationContextContextProvider implements WrappedContextData<InvocationContext> {

	private InvocationContext invocationContext;

	public InvocationContextContextProvider() {
	}

	public InvocationContextContextProvider(final InvocationContext invocationContext) {
		this.invocationContext = invocationContext;
	}

	@Override
	public final void setContextData(Object instance) throws ClassCastException {
		this.invocationContext = (InvocationContext) instance;
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
	@ProfileSettings(basic = true, enhanced = true, full = true)
	public final String getTypeName() {
		return this.invocationContext != null && this.invocationContext.getTarget() != null && this.invocationContext.getTarget().getClass() != null
				? this.invocationContext.getTarget().getClass().getCanonicalName() : null;
	}

	@TraceeContextProviderMethod(displayName = "methodName", order = 10)
	@ProfileSettings(basic = true, enhanced = true, full = true)
	public final String getMethodName() {
		return this.invocationContext != null && this.invocationContext.getMethod() != null ? this.invocationContext.getMethod().getName() : null;
	}

	@TraceeContextProviderMethod(displayName = "parameters", order = 20)
	@ProfileSettings(basic = true, enhanced = true, full = true)
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
	@ProfileSettings(basic = false, enhanced = true, full = true)
	public final Object getTargetInstance() {
		String result = null;
		if (this.invocationContext != null) {
			return this.invocationContext.getTarget();

		}

		return result;
	}

	@TraceeContextProviderMethod(displayName = "invocationContextData", order = 40)
	@ProfileSettings(basic = false, enhanced = true, full = true)
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

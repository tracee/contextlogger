package io.tracee.contextlogger.contextprovider.tracee;

import io.tracee.Tracee;
import io.tracee.TraceeBackend;
import io.tracee.contextlogger.provider.api.Flatten;
import io.tracee.contextlogger.api.ImplicitContext;
import io.tracee.contextlogger.api.ImplicitContextData;
import io.tracee.contextlogger.provider.api.TraceeContextProviderMethod;
import io.tracee.contextlogger.contextprovider.Order;
import io.tracee.contextlogger.contextprovider.utility.NameStringValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Common context data provider.
 * Created by Tobias Gindler, holisticon AG on 14.03.14.
 */
@SuppressWarnings("unused")
@io.tracee.contextlogger.provider.api.TraceeContextProvider(displayName = "tracee", order = Order.TRACEE)
public final class TraceeContextProvider implements ImplicitContextData {

    private final TraceeBackend traceeBackend;

    public TraceeContextProvider() {
        this.traceeBackend = Tracee.getBackend();
    }

    @Override
    public ImplicitContext getImplicitContext() {
        return ImplicitContext.TRACEE;
    }

    @SuppressWarnings("unused")
    @Flatten
    @TraceeContextProviderMethod(
            displayName = "DYNAMIC",
            order = 10)
    public List<NameStringValuePair> getNameValuePairs() {

        final List<NameStringValuePair> list = new ArrayList<NameStringValuePair>();

        final Map<String, String> keys = traceeBackend.copyToMap();
		for (Map.Entry<String, String> entry : keys.entrySet()) {
			list.add(new NameStringValuePair(entry.getKey(), entry.getValue()));
		}
        return !list.isEmpty() ? list : null;
    }
}

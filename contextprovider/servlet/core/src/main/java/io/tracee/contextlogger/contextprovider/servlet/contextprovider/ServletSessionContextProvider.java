package io.tracee.contextlogger.contextprovider.servlet.contextprovider;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpSession;

import io.tracee.contextlogger.contextprovider.Order;
import io.tracee.contextlogger.contextprovider.api.Flatten;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderMethod;
import io.tracee.contextlogger.contextprovider.api.WrappedContextData;
import io.tracee.contextlogger.contextprovider.utility.NameValuePair;

/**
 * Context provider for HttpSession.
 * Created by Tobias Gindler, holisticon AG on 19.03.14.
 */
@TraceeContextProvider(displayName = "servletSession", order = Order.SERVLET)
public final class ServletSessionContextProvider implements WrappedContextData<HttpSession> {

    private HttpSession session;

    public ServletSessionContextProvider() {
    }

    public ServletSessionContextProvider(HttpSession session) {
        this.session = session;
    }

    @Override
    public void setContextData(Object instance) throws ClassCastException {
        this.session = (HttpSession)instance;
    }

    @Override
    public HttpSession getContextData() {
        return this.session;
    }

    @Override
    public Class<HttpSession> getWrappedType() {
        return HttpSession.class;
    }

    @SuppressWarnings("unused")
    @Flatten
    @TraceeContextProviderMethod(displayName = "DYNAMIC")
    public List<NameValuePair<Object>> getSessionAttributes() {

        if (session == null) {
            return null;
        }

        final List<NameValuePair<Object>> sessionAttributes = new ArrayList<NameValuePair<Object>>();

        if (session != null) {
            final Enumeration<String> attributeNames = session.getAttributeNames();
            while (attributeNames.hasMoreElements()) {

                final String key = attributeNames.nextElement();
                final Object value = session.getAttribute(key);

                sessionAttributes.add(new NameValuePair<Object>(key, value));

            }
        }

        return (sessionAttributes.size() > 0 ? sessionAttributes : null);

    }

}
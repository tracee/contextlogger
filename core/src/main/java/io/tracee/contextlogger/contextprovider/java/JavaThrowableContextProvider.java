package io.tracee.contextlogger.contextprovider.java;

import io.tracee.contextlogger.provider.api.TraceeContextProvider;
import io.tracee.contextlogger.provider.api.TraceeContextProviderMethod;
import io.tracee.contextlogger.provider.api.WrappedContextData;
import io.tracee.contextlogger.contextprovider.Order;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Provides context information about thrown exceptions.
 * Created by Tobias Gindler, holisticon AG on 17.03.14.
 */
@SuppressWarnings("unused")
@TraceeContextProvider(displayName = "throwable", order = Order.EXCEPTION)
public final class JavaThrowableContextProvider implements WrappedContextData<Throwable> {


    private Throwable throwable;

    @SuppressWarnings("unused")
    public JavaThrowableContextProvider() {
    }

    @SuppressWarnings("unused")
    public JavaThrowableContextProvider(final Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public void setContextData(Object instance) throws ClassCastException {
        this.throwable = (Throwable) instance;
    }

    public Class<Throwable> getWrappedType() {
        return Throwable.class;
    }

    @SuppressWarnings("unused")
    @TraceeContextProviderMethod(
            displayName = "message",
            order = 10)
    public String getMessage() {
        return throwable != null ? throwable.getMessage() : null;
    }

    @SuppressWarnings("unused")
    @TraceeContextProviderMethod(
            displayName = "stacktrace",
            order = 20)
    public String getStacktrace() {
        if (this.throwable != null) {
            StringWriter sw = new StringWriter();
            throwable.printStackTrace(new PrintWriter(sw));
            return sw.toString();
        } else {
            return null;
        }
    }

}
package io.tracee.contextlogger.contextprovider.aspectj.contextprovider;

import io.tracee.contextlogger.contextprovider.Order;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderMethod;
import io.tracee.contextlogger.contextprovider.api.WrappedContextData;

/**
 * Watchdog context data provider.
 * Created by Tobias Gindler, holisticon AG on 17.03.14.
 */
@SuppressWarnings("unused")
@TraceeContextProvider(displayName = "watchdog", order = Order.WATCHDOG)
public final class WatchdogContextProvider implements WrappedContextData<WatchdogDataWrapper> {

    private WatchdogDataWrapper watchdogDataWrapper;

    public WatchdogContextProvider() {
    }

    @SuppressWarnings("unused")
    public WatchdogContextProvider(final WatchdogDataWrapper watchdogDataWrapper) {
        this.watchdogDataWrapper = watchdogDataWrapper;
    }

    @Override
    public void setContextData(Object instance) throws ClassCastException {
        this.watchdogDataWrapper = (WatchdogDataWrapper)instance;
    }

    @Override
    public Class<WatchdogDataWrapper> getWrappedType() {
        return WatchdogDataWrapper.class;
    }

    @SuppressWarnings("unused")
    @TraceeContextProviderMethod(displayName = "id", order = 10)
    public String getId() {
        if (watchdogDataWrapper != null) {
            return watchdogDataWrapper.getAnnotatedId();
        }
        return null;
    }

    @SuppressWarnings("unused")
    @TraceeContextProviderMethod(displayName = "aspectj.proceedingJoinPoint", order = 20)
    public AspectjProceedingJoinPointContextProvider getProceedingJoinPoint() {
        if (watchdogDataWrapper != null && watchdogDataWrapper.getProceedingJoinPoint() != null) {
            return AspectjProceedingJoinPointContextProvider.wrap(watchdogDataWrapper.getProceedingJoinPoint());
        }
        return null;
    }

}

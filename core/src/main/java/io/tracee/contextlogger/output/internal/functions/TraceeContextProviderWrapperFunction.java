package io.tracee.contextlogger.output.internal.functions;

import io.tracee.contextlogger.contextprovider.TypeToWrapper;
import io.tracee.contextlogger.contextprovider.api.WrappedContextData;
import io.tracee.contextlogger.impl.ContextLoggerConfiguration;

/**
 * Wrapper function that wraps a given instance in a matching Tracee context logging provider.
 */
public class TraceeContextProviderWrapperFunction {

    private static final TraceeContextProviderWrapperFunction instance = new TraceeContextProviderWrapperFunction();

    /**
     * Hide constructor.
     */
    private TraceeContextProviderWrapperFunction() {

    }

    public static TraceeContextProviderWrapperFunction getInstance() {
        return instance;
    }

    /**
     * Wraps the passed instance inside a matching tracee context logging provider instance.
     * Will return the passed instance, if no matching tracee context logging provider exists.
     *
     * @param contextLoggerConfiguration the configuration to use
     * @param instanceToWrap the instance to wrap
     * @return either a tracee context provider that encapsulates the passed instance or the instance itself if no matching context provider exists
     */
    public Object apply(ContextLoggerConfiguration contextLoggerConfiguration, Object instanceToWrap) {

        // now try to find instance type in known wrapper types map
        Class matchingWrapperType = contextLoggerConfiguration.getClassToWrapperMap().get(instanceToWrap.getClass());
        if (matchingWrapperType == null) {

            // now try to find instance type in TypeToWrapper List
            for (TypeToWrapper wrapper : contextLoggerConfiguration.getWrapperList()) {
                if (wrapper.getWrappedInstanceType().isAssignableFrom(instanceToWrap.getClass())) {
                    matchingWrapperType = wrapper.getWrapperType();
                    break;
                }
            }

        }

        if (matchingWrapperType != null) {
            // now create wrapper instance
            try {
                WrappedContextData wrapperInstance = (WrappedContextData)createInstance(matchingWrapperType);
                wrapperInstance.setContextData(instanceToWrap);

                return wrapperInstance;
            }
            catch (Exception e) {
                // continue

            }
        }

        return instanceToWrap;
    }

    /**
     * Creates a new instance of the passed type via reflection.
     *
     * @param type the type of the new instance
     * @return a new instance of the passed type or null if an exception occurred during the creation of the instance of if the passed type is null.
     */
    private Object createInstance(final Class type) {
        if (type != null) {
            try {
                return type.newInstance();
            }
            catch (Exception e) {
                // should not occur
            }
        }
        return null;
    }

}

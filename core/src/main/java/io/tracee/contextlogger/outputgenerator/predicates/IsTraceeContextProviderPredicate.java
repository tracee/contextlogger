package io.tracee.contextlogger.outputgenerator.predicates;

import io.tracee.contextlogger.api.ImplicitContextData;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.api.WrappedContextData;
import io.tracee.contextlogger.utility.TraceeContextLogAnnotationUtilities;

/**
 * Determines if the instance is a tracee context provider.
 */
public class IsTraceeContextProviderPredicate implements OutputElementTypePredicate {

    private static final IsTraceeContextProviderPredicate instance = new IsTraceeContextProviderPredicate();

    @Override
    public boolean apply(final Object instance) {
        return hasTraceeContextProviderAnnotation(instance) && isExtendingTraceeContextProvider(instance);
    }

    /**
     * Checks whether the passed instance type is annotated with the {@link io.tracee.contextlogger.contextprovider.api.TraceeContextProvider} annotation.
     *
     * @param instance the instance to check
     * @return true, if the passed instance's type is annotated with the {@link io.tracee.contextlogger.contextprovider.api.TraceeContextProvider} annotation,
     *         otherwise false
     */
    protected boolean hasTraceeContextProviderAnnotation(final Object instance) {

        if (instance == null) {
            return false;
        }

        TraceeContextProvider annotation = TraceeContextLogAnnotationUtilities.getAnnotationFromType(instance);
        return annotation != null;

    }

    /**
     * Checks whether the passed instance extends the {@link io.tracee.contextlogger.api.ImplicitContextData} or
     * {@link io.tracee.contextlogger.contextprovider.api.WrappedContextData}types.
     *
     * @param instance the instance to check
     * @return true, if the passed instance's type extends the ImplicitContextData or WrappedContextData class
     */
    protected boolean isExtendingTraceeContextProvider(final Object instance) {

        if (instance == null) {
            return false;
        }

        return ImplicitContextData.class.isAssignableFrom(instance.getClass()) || WrappedContextData.class.isAssignableFrom(instance.getClass());

    }

    public static IsTraceeContextProviderPredicate getInstance() {
        return instance;
    }

}

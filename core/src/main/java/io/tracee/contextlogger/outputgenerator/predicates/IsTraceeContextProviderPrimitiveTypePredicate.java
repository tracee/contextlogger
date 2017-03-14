package io.tracee.contextlogger.outputgenerator.predicates;

import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderPrimitiveType;
import io.tracee.contextlogger.contextprovider.api.WrappedPrimitiveTypeContextData;
import io.tracee.contextlogger.utility.TraceeContextLogAnnotationUtilities;

/**
 * Determines if the instance is a tracee context provider.
 */
public class IsTraceeContextProviderPrimitiveTypePredicate implements OutputElementTypePredicate {

    private static final IsTraceeContextProviderPrimitiveTypePredicate instance = new IsTraceeContextProviderPrimitiveTypePredicate();

    @Override
    public boolean apply(final Object instance) {
        return hasTraceeContextProviderPrimitiveTypeAnnotation(instance) && isExtendingTraceeContextProvider(instance);
    }

    /**
     * Checks whether the passed instance type is annotated with the {@link io.tracee.contextlogger.contextprovider.api.TraceeContextProvider} annotation.
     *
     * @param instance the instance to check
     * @return true, if the passed instance's type is annotated with the {@link io.tracee.contextlogger.contextprovider.api.TraceeContextProvider} annotation,
     * otherwise false
     */
    protected boolean hasTraceeContextProviderPrimitiveTypeAnnotation(final Object instance) {

        if (instance == null) {
            return false;
        }

        TraceeContextProviderPrimitiveType annotation = TraceeContextLogAnnotationUtilities.getAnnotationFromType(instance, TraceeContextProviderPrimitiveType.class);
        return annotation != null;

    }

    /**
     * Checks whether the passed instance extends the {@link io.tracee.contextlogger.contextprovider.api.ImplicitContextData} or
     * {@link io.tracee.contextlogger.contextprovider.api.WrappedContextData}types.
     *
     * @param instance the instance to check
     * @return true, if the passed instance's type extends the ImplicitContextData or WrappedContextData class
     */
    protected boolean isExtendingTraceeContextProvider(final Object instance) {

        if (instance == null) {
            return false;
        }

        return WrappedPrimitiveTypeContextData.class.isAssignableFrom(instance.getClass());

    }

    public static IsTraceeContextProviderPrimitiveTypePredicate getInstance() {
        return instance;
    }

}

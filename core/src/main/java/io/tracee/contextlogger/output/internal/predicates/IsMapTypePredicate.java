package io.tracee.contextlogger.output.internal.predicates;

import java.util.Map;

/**
 * Checks whether the passed instance is a collection or an array.
 */
public class IsMapTypePredicate implements OutputElementTypePredicate {

    private static final IsMapTypePredicate instance = new IsMapTypePredicate();

    @Override
    public boolean apply(final Object instance) {
        return isMap(instance);
    }

    protected boolean isMap(final Object instance) {

        if (instance == null) {
            return false;
        }

        return Map.class.isAssignableFrom(instance.getClass());

    }

    public static IsMapTypePredicate getInstance() {
        return instance;
    }

}

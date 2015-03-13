package io.tracee.contextlogger.outputgenerator.predicates;

import java.util.Collection;

/**
 * Checks whether the passed instance is a collection or an array.
 */
public class IsCollectionTypePredicate implements OutputElementTypePredicate {

    private static final IsCollectionTypePredicate instance = new IsCollectionTypePredicate();

    @Override
    public boolean apply(final Object instance) {
        return isCollection(instance) || isArray(instance);
    }

    public boolean isCollection(final Object instance) {

        if (instance == null) {
            return false;
        }

        return Collection.class.isAssignableFrom(instance.getClass());

    }

    public boolean isArray(final Object instance) {

        if (instance == null) {
            return false;
        }

        return instance.getClass().isArray();

    }

    public static IsCollectionTypePredicate getInstance() {
        return instance;
    }

}

package io.tracee.contextlogger.outputgenerator.predicates;

import io.tracee.contextlogger.api.ImplicitContext;

/**
 * Predicate to check if passed instance is {@link io.tracee.contextlogger.api.ImplicitContext} enum value.
 */
public class IsImplicitContextEnumValuePredicate {

    private static final IsImplicitContextEnumValuePredicate instance = new IsImplicitContextEnumValuePredicate();

    public boolean apply(final Object instance) {
        return instance != null && instance instanceof ImplicitContext;
    }

    public static IsImplicitContextEnumValuePredicate getInstance() {
        return instance;
    }

}

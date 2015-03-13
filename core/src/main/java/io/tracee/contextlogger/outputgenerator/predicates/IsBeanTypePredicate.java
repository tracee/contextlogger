package io.tracee.contextlogger.outputgenerator.predicates;

import io.tracee.contextlogger.outputgenerator.utility.BeanUtility;

/**
 * Determines is passed instance is bean and therefore a complex type.
 */
public class IsBeanTypePredicate implements OutputElementTypePredicate {

    private static final IsBeanTypePredicate instance = new IsBeanTypePredicate();

    @Override
    public boolean apply(final Object instance) {
        return isBean(instance);
    }

    protected boolean isBean(final Object instance) {

        return instance != null && BeanUtility.getGetterMethodsRecursively(instance.getClass()).size() > 0;

    }

    public static IsBeanTypePredicate getInstance() {
        return instance;
    }

}

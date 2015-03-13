package io.tracee.contextlogger.outputgenerator.predicates;

/**
 * Checks if the class of the passed instance is overwriting the standard to String method
 */
public class IsOverwritingToStringPredicate {

    private static final IsOverwritingToStringPredicate INSTANCE = new IsOverwritingToStringPredicate();

    public boolean apply(final Object instance) {
        return instance != null ? isTypeOverwritingToString(instance.getClass()) : false;
    }

    boolean isTypeOverwritingToString(final Class type) {

        if (!type.equals(Object.class)) {

            try {
                type.getDeclaredMethod("toString");
                return true;
            }
            catch (NoSuchMethodException e) {
                // just ignore
            }

        }

        return false;
    }

    public static IsOverwritingToStringPredicate getInstance() {
        return INSTANCE;
    }

}

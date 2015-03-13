package io.tracee.contextlogger.utility.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import io.tracee.contextlogger.utility.GetterUtilities;

/**
 * Predicated to check if passed method is a getter.
 * Getter methods should reside in the same Class as their fields and should not be overwritten.
 */
public class IsGetterMethodPredicate {

    // possible prefixes for getter methods
    public static final String[] GETTER_PREFIXES = { "get", "is", "has" };

    private static final IsGetterMethodPredicate instance = new IsGetterMethodPredicate();

    public static boolean check(Class type, Method method) {
        return instance.apply(type, method);
    }

    public boolean apply(Class type, Method method) {

        // make it null safe
        if (method == null || type == null) {
            return false;
        }

        // check if method matches naming conventions
        if (!hasGetterPrefixInMethodName(method)) {
            return false;
        }

        // check whether the getter method is public, non-static, takes no parameters and has a return type
        if (!hasNoParameters(method) || !hasReturnValue(method) || !isPublicNonStaticMethod(method)) {
            return false;
        }

        // check if corresponding field exists
        try {

            if (!correspondingFieldExists(type, method) || !hasCompatibleReturnTypes(type, method)) {

                return false;

            }

        }
        catch (SecurityException e) {
            // ignore
        }

        return true;
    }

    /**
     * Getter method names must have a specific prefix.
     *
     * @param method the method to check
     * @return true, if the method name prefix is a getter Prefix, otherwise false
     */
    boolean hasGetterPrefixInMethodName(Method method) {

        String methodName = method.getName();
        if (methodName != null) {

            for (String prefix : GETTER_PREFIXES) {

                if (methodName.startsWith(prefix)) {
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * Getter methods must be public and not be static.
     *
     * @param method the method to check
     * @return true if the method is not static and public, otherwise false
     */
    boolean isPublicNonStaticMethod(final Method method) {
        int modifiers = method.getModifiers();
        return !Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers);
    }

    /**
     * Getter methods take no parameters.
     *
     * @param method the method to check
     * @return true, if the method takes no parameters, otherwise false.
     */
    boolean hasNoParameters(final Method method) {
        return method.getParameterTypes().length == 0;
    }

    /**
     * Getter methods must have return type (must not be of type Void)
     *
     * @param method the method to check
     * @return true if the method has a return type, otherwise false
     */
    boolean hasReturnValue(final Method method) {
        Class returnType = method.getReturnType();
        return !Void.TYPE.equals(returnType);
    }

    /**
     * Getter method should have a corresponding field.
     *
     * @param type the type to search the field in
     * @param method the method to check
     * @return true, if a field for passed getter method exists, otherwise false
     */
    boolean correspondingFieldExists(Class type, Method method) {
        return getCorrespondingField(type, method) != null;
    }

    /**
     * Getter method and field must have compatible fields.
     *
     * @param type the type to search the field in
     * @param method the method to check
     * @return true, if field type is assignable to methods return type
     */
    boolean hasCompatibleReturnTypes(Class type, Method method) {

        Field correspondingField = getCorrespondingField(type, method);

        return correspondingField != null && method.getReturnType().isAssignableFrom(correspondingField.getType());

    }

    /**
     * Helper method for getting a corresponsing field for a method
     *
     * @param type the type to search the field in
     * @param method the getter method to search the field for
     * @return the Field or null if the field for the passed method doesn't exists
     */
    Field getCorrespondingField(Class type, Method method) {

        try {
            return type.getDeclaredField(GetterUtilities.getFieldName(method));
        }
        catch (NoSuchFieldException e) {
            return null;
        }

    }

}

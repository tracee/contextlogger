package io.tracee.contextlogger.output.internal.utility;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import io.tracee.contextlogger.utility.reflection.IsGetterMethodPredicate;

/**
 * Utility class containing common functions to process beans.
 */
public class BeanUtility {

    /**
     * Gets all getter methods recursively (for all superclasses).
     *
     * @param type the type to get the methods for
     * @return the available getter methods
     */
    public static Set<Method> getGetterMethodsRecursively(final Class type) {

        Set<Method> getterMethods = new HashSet<Method>();

        if (type != null) {

            Method[] methods = null;

            try {
                methods = type.getDeclaredMethods();
            }
            catch (SecurityException e) {
                // use fallback - checks against fields might not work
                methods = type.getMethods();
            }

            for (Method method : methods) {

                if (IsGetterMethodPredicate.check(type, method)) {
                    getterMethods.add(method);
                }

            }

            // process supertype
            getterMethods.addAll(getGetterMethodsRecursively(type.getSuperclass()));

        }

        return getterMethods;
    }

}

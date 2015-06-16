package io.tracee.contextlogger.utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * A generic service locator.
 */
public class GenericServiceLocator {

    private GenericServiceLocator() {
    }

    public static <T> T locate(final Class<T> clazz) {
        final List services = locateAll(clazz);
        return services.isEmpty() ? (T)null : (T)services.get(0);
    }

    public static <T> List<T> locateAll(final Class<T> clazz) {

        final Iterator<T> iterator = ServiceLoader.load(clazz).iterator();
        final List<T> services = new ArrayList<T>();

        while (iterator.hasNext()) {
            try {
                services.add(iterator.next());
            }
            catch (Error e) {
                e.printStackTrace(System.err);
            }
        }

        return services;

    }

}

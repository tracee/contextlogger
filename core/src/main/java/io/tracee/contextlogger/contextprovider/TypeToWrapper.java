package io.tracee.contextlogger.contextprovider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.tracee.contextlogger.contextprovider.api.ImplicitContextData;
import io.tracee.contextlogger.contextprovider.api.WrappedContextData;

/**
 * Class to store class to wrapper mapppings.
 */
public final class TypeToWrapper {

    private static List<TypeToWrapper> typeToWrapperList;
    private static final Logger logger = LoggerFactory.getLogger(TypeToWrapper.class);

    private final Class wrappedInstanceType;
    private final Class wrapperType;

    public TypeToWrapper(final Class wrappedInstanceType, final Class wrapperType) {
        this.wrappedInstanceType = wrappedInstanceType;
        this.wrapperType = wrapperType;
    }

    public Class getWrappedInstanceType() {
        return wrappedInstanceType;
    }

    public Class getWrapperType() {
        return wrapperType;
    }

    public static Set<Class> getAllWrappedClasses() {

        final List<TypeToWrapper> localTypeToWrapperList = getTypeToWrapper();

        Set<Class> resultList = new HashSet<Class>();

        if (localTypeToWrapperList != null) {
            for (TypeToWrapper typeToWrapper : localTypeToWrapperList) {
                resultList.add(typeToWrapper.getWrappedInstanceType());
            }
        }

        return resultList;

    }

    public static List<TypeToWrapper> getTypeToWrapper() {
        if (typeToWrapperList == null) {
            typeToWrapperList = getAvailableWrappers();
        }
        return typeToWrapperList;
    }

    /**
     * Gets a list with all wrapper classes.
     *
     * @return a list with all wrapper classes
     */
    public static Set<Class> findWrapperClasses() {

        final List<TypeToWrapper> localTypeToWrapperList = getTypeToWrapper();

        Set<Class> resultList = new HashSet<Class>();

        if (localTypeToWrapperList != null) {
            for (TypeToWrapper typeToWrapper : localTypeToWrapperList) {
                resultList.add(typeToWrapper.getWrapperType());
            }
        }

        return resultList;

    }

    /**
     * Generic function to get a implicit data provider classes from resource files.
     *
     * @return a set that contains all context provider type the were found
     */
    public static Set<ImplicitContextData> getImplicitContextDataProviders() {
        final Set<ImplicitContextData> result = new HashSet<ImplicitContextData>();

        for (Class clazz : ContextProviderServiceLoader.getServiceLocator().getImplicitContextProvider()) {

            try {
                if (ImplicitContextData.class.isAssignableFrom(clazz)) {
                    ImplicitContextData instance = (ImplicitContextData)(clazz.newInstance());
                    result.add(instance);
                }
            }
            catch (Throwable e) {
                // to be ignored
            }

        }

        return result;
    }

    /**
     * Method to get all available wrappers.
     *
     * @return all wrapping between wrapper classes and their wrapped types.
     */
    public static List<TypeToWrapper> getAvailableWrappers() {

        final List<TypeToWrapper> result = new ArrayList<TypeToWrapper>();

        for (Class clazz : ContextProviderServiceLoader.getServiceLocator().getContextProvider()) {

            try {
                if (WrappedContextData.class.isAssignableFrom(clazz)) {
                    // try to create instance to get the wrapped type
                    final WrappedContextData instance = (WrappedContextData)clazz.newInstance();
                    result.add(new TypeToWrapper(instance.getWrappedType(), clazz));
                }
            }
            catch (Throwable e) {
                // to be ignored
            }

        }

        return result;
    }

    private static void logError(final String message, final Throwable e) {
        logger.error(message, e);
    }

    private static void logWarn(final String message) {
        logger.warn(message);
    }
}

package io.tracee.contextlogger.contextprovider.api;

/**
 * Interface that marks a class and provides a method to set the context information.
 */
public interface WrappedContextData<T> {

    /**
     * Used to set context data via reflection.
     *
     * @param instance the context data instance to set
     * @throws ClassCastException if passed instance type is incompatible with wrapper type.
     */
    void setContextData(Object instance) throws ClassCastException;

    /**
     * Used to get wrapped context data.
     *
     * @return
     */
    T getContextData();

    /**
     * Used to determine the wrapped type of the class.
     *
     * @return
     */
    Class<T> getWrappedType();
}

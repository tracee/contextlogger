package io.tracee.contextlogger.contextprovider.api;

/**
 * Interface that marks a class and provides a method to set the context information.
 */
public interface WrappedPrimitiveTypeContextData<T> extends WrappedContextData {

    /**
     * Gets the String representation of the wrapped primitive type
     *
     * @return the string representation of the wrapped type
     */
    String getPrimitiveTypeValue();

}

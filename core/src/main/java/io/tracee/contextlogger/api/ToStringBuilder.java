package io.tracee.contextlogger.api;

/**
 * Interface for generating string representations.
 */
public interface ToStringBuilder {

    /**
     * Creates a string representation of the passed instancesToLog.
     *
     * @param instancesToLog The instances to be converted into a string.
     * @return
     */
    String toString(Object... instancesToLog);

    /**
     * Creates a string representation of the passed instancesToLog.
     * This method is deprecated - please use toString(Object... instancesToLog) method instead
     *
     * @param instancesToLog The instances to be converted into a string.
     * @return
     */
    @Deprecated
    String create(Object... instancesToLog);

    /**
     * Prepares building of output and returns an Wrapper instance. The output will produced by calling WrappedContextLoggerOutput toString method.
     *
     * @param instances the instances to output
     * @return the wrapped output provider
     */
    WrappedContextLoggerOutput wrap(Object... instances);

}

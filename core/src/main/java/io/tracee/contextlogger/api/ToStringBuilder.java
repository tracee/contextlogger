package io.tracee.contextlogger.api;

/**
 * Interface for gernerating string representations.
 */
public interface ToStringBuilder {

    /**
     * Creates a string representation of the passed instancesToLog.
     *
     * @param instancesToLog The instances to be converted into a string.
     * @return
     */
    String create(Object... instancesToLog);

    /**
     * Prepares building of output and returns an Wrapper instance. The output will produced by calling WrappedContextLoggerOutput toString method.
     *
     * @param instances the instances to output
     * @return the wrapped output provider
     */
    WrappedContextLoggerOutput wrap(Object... instances);

}

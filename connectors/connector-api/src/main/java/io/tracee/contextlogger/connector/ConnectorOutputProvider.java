package io.tracee.contextlogger.connector;

/**
 * Log Message provider for excluding context providers.
 */
public interface ConnectorOutputProvider {

    /**
     * Creates a new instance of LogMessageProvider and excludes context providers.
     *
     * @param contextProvidersToInclude the new LogMessageProvider instance
     */
    ConnectorOutputProvider excludeContextProviders(final Class... contextProvidersToInclude);

    /**
     * Provides Log message
     *
     * @return the log message
     */
    String provideOutput();

}

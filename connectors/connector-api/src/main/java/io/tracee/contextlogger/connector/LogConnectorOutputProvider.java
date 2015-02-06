package io.tracee.contextlogger.connector;

/**
 * Provides Prefix Message
 */
public interface LogConnectorOutputProvider extends ConnectorOutputProvider {

    /**
     * Gets the prefix used to provide a prefix message for log output
     *
     * @return
     */
    String getPrefix();

}

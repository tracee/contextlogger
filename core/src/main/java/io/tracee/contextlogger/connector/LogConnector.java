package io.tracee.contextlogger.connector;

import java.util.Map;

import io.tracee.Tracee;
import io.tracee.TraceeLogger;
import io.tracee.contextlogger.contextprovider.tracee.CommonDataContextProvider;
import io.tracee.contextlogger.contextprovider.tracee.TraceeMdcContextProvider;

/**
 * A Connector to send error reports to the logger.
 * Created by Tobias Gindler, holisticon AG on 07.02.14.
 */
public class LogConnector implements Connector {

    public LogConnector() {
        this(Tracee.getBackend().getLoggerFactory().getLogger(LogConnector.class));
    }

    LogConnector(TraceeLogger logger) {
        this.logger = logger;
    }

    private final TraceeLogger logger;

    @Override
    public void init(Map<String, String> properties) {

    }

    @Override
    public final void sendErrorReport(ConnectorOutputProvider connectorOutputProvider) {

        ConnectorOutputProvider localConnectorOutputProvider = connectorOutputProvider.excludeContextProviders(CommonDataContextProvider.class,
                TraceeMdcContextProvider.class);

        String output = localConnectorOutputProvider.provideOutput();
        if (connectorOutputProvider instanceof LogConnectorOutputProvider) {

            LogConnectorOutputProvider logConnectorOutputProvider = (LogConnectorOutputProvider)connectorOutputProvider;
            if (logConnectorOutputProvider.getPrefix() != null) {
                output = logConnectorOutputProvider.getPrefix() + output;
            }

        }

        logger.error(output);

    }
}

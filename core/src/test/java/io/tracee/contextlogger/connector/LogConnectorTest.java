package io.tracee.contextlogger.connector;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import io.tracee.TraceeLogger;
import io.tracee.contextlogger.util.test.ConnectorOutputProviderBuilder;

public class LogConnectorTest {

    private final TraceeLogger logger = mock(TraceeLogger.class);
    private final LogConnector unit = new LogConnector(logger);

    @Test
    public void testLog() {
        unit.sendErrorReport(ConnectorOutputProviderBuilder.createConnectorOutputProvider(null, "{ \"foo\":\"bar\"}"));
        verify(logger).error(eq("{ \"foo\":\"bar\"}"));
    }
}

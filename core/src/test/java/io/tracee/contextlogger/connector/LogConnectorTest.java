package io.tracee.contextlogger.connector;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.TraceeLogger;
import io.tracee.contextlogger.contextprovider.java.JavaThrowableContextProvider;
import io.tracee.contextlogger.contextprovider.tracee.CommonDataContextProvider;
import io.tracee.contextlogger.contextprovider.tracee.TraceeMdcContextProvider;
import io.tracee.contextlogger.util.test.ConnectorOutputProviderBuilder;

public class LogConnectorTest {

    private final TraceeLogger logger = mock(TraceeLogger.class);
    private final LogConnector unit = new LogConnector(logger);

    @Test
    public void testLog() {
        unit.sendErrorReport(ConnectorOutputProviderBuilder.createConnectorOutputProvider(null, "{ \"foo\":\"bar\"}"));
        verify(logger).error(eq("{ \"foo\":\"bar\"}"));
    }

    @Test
    public void should_get_defined_excluded_types_correctly() {

        String excludedTypesSystemPropertyString = CommonDataContextProvider.class.getCanonicalName() + "  ;"
                + TraceeMdcContextProvider.class.getCanonicalName() + ",dsaadasdsadsad," + JavaThrowableContextProvider.class.getCanonicalName();

        System.setProperty(LogConnector.SYSTEM_PROPERTY_NAME_FOT_EXCLUDED_TYPES, excludedTypesSystemPropertyString);

        Class[] result = new LogConnector().getTypesToBeExcluded();

        MatcherAssert.assertThat(result.length, Matchers.is(3));
        MatcherAssert.assertThat(result[0], Matchers.typeCompatibleWith((Class)CommonDataContextProvider.class));
        MatcherAssert.assertThat(result[1], Matchers.typeCompatibleWith((Class)TraceeMdcContextProvider.class));
        MatcherAssert.assertThat(result[2], Matchers.typeCompatibleWith((Class)JavaThrowableContextProvider.class));

    }

    @Test
    public void should_use_default_excluded_types_if_no_system_property_is_defined() {

        Class[] result = new LogConnector().getTypesToBeExcluded();

        MatcherAssert.assertThat(result, Matchers.is(result));

    }

    @Test
    public void should_excluded_no_types_for_empty_system_property() {

        String excludedTypesSystemPropertyString = "";

        System.setProperty(LogConnector.SYSTEM_PROPERTY_NAME_FOT_EXCLUDED_TYPES, excludedTypesSystemPropertyString);

        Class[] result = new LogConnector().getTypesToBeExcluded();

        MatcherAssert.assertThat(result.length, Matchers.is(0));

    }
}

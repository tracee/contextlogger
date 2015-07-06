package io.tracee.contextlogger.connector;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.tracee.contextlogger.contextprovider.core.java.JavaThrowableContextProvider;
import io.tracee.contextlogger.contextprovider.core.tracee.CommonDataContextProvider;
import io.tracee.contextlogger.contextprovider.core.tracee.TraceeMdcContextProvider;
import io.tracee.contextlogger.util.test.ConnectorOutputProviderBuilder;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class LogConnectorTest {

    private final Logger logger = mock(Logger.class);
    private final LogConnector unit = new LogConnector();

    @Before
    public void init() {
        PowerMockito.mockStatic(LoggerFactory.class);
        PowerMockito.when(LoggerFactory.getLogger(Mockito.any(Class.class))).thenReturn(logger);
    }

    /**
     * TODO TG must be fixed or removed
     */
    @Ignore
    @Test
    public void testLog() {

        unit.sendErrorReport(ConnectorOutputProviderBuilder.createConnectorOutputProvider(null, "{ \"foo\":\"bar\"}"));
        verify(logger).error(Mockito.anyString());
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

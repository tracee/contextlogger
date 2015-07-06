package io.tracee.contextlogger.integrationtest;

import io.tracee.contextlogger.TraceeContextLogger;
import io.tracee.contextlogger.connector.LogConnector;
import io.tracee.contextlogger.contextprovider.core.java.JavaThrowableContextProvider;
import io.tracee.contextlogger.contextprovider.core.tracee.CommonDataContextProvider;
import io.tracee.contextlogger.contextprovider.core.tracee.TraceeMdcContextProvider;
import org.junit.Test;

/**
 * Created by TGI on 11.06.2015.
 */
public class ExcludeContextProviderTest {

    @Test
    public void test() {

        String excludedTypesSystemPropertyString = CommonDataContextProvider.class.getCanonicalName() + "  ;"
                + TraceeMdcContextProvider.class.getCanonicalName() + ",dsaadasdsadsad," + JavaThrowableContextProvider.class.getCanonicalName();

        System.setProperty(LogConnector.SYSTEM_PROPERTY_NAME_FOT_EXCLUDED_TYPES, excludedTypesSystemPropertyString);

        TraceeContextLogger.createDefault().log("abc", new NullPointerException("XSA"));

    }

}

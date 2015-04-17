package io.tracee.contextlogger.connector;

import java.util.ArrayList;
import java.util.List;
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

    public final static String SYSTEM_PROPERTY_NAME_FOT_EXCLUDED_TYPES = "io.tracee.contextlogger.connector.Logconnector.excludedTypes";
    protected final static Class[] DEFAULT_EXCLUDED_TYPES = { CommonDataContextProvider.class, TraceeMdcContextProvider.class };
    private final static String SYSTEM_PROPERTY_SPLITTER_REGEX = "[, ;]";

    private final Class[] excludedTypes;

    public LogConnector() {
        this(Tracee.getBackend().getLoggerFactory().getLogger(LogConnector.class));
    }

    LogConnector(TraceeLogger logger) {
        this.logger = logger;
        this.excludedTypes = getTypesToBeExcluded();
    }

    private final TraceeLogger logger;

    @Override
    public void init(Map<String, String> properties) {

    }

    @Override
    public final void sendErrorReport(ConnectorOutputProvider connectorOutputProvider) {

        ConnectorOutputProvider localConnectorOutputProvider = connectorOutputProvider.excludeContextProviders(excludedTypes);

        String output = localConnectorOutputProvider.provideOutput();
        if (connectorOutputProvider instanceof LogConnectorOutputProvider) {

            LogConnectorOutputProvider logConnectorOutputProvider = (LogConnectorOutputProvider)connectorOutputProvider;
            if (logConnectorOutputProvider.getPrefix() != null) {
                output = logConnectorOutputProvider.getPrefix() + output;
            }

        }

        logger.error(output);

    }

    protected Class[] getTypesToBeExcluded() {

        String excludedTypesPropertyString = System.getProperty(SYSTEM_PROPERTY_NAME_FOT_EXCLUDED_TYPES);
        if (excludedTypesPropertyString != null) {

            List<Class> typeList = new ArrayList<Class>();

            for (String className : excludedTypesPropertyString.split(SYSTEM_PROPERTY_SPLITTER_REGEX)) {
                if (!className.isEmpty()) {
                    try {
                        typeList.add(Class.forName(className));
                    }
                    catch (ClassNotFoundException e) {
                        logger.warn("[TracEE contextlogger] - System property '" + SYSTEM_PROPERTY_NAME_FOT_EXCLUDED_TYPES
                                + "' contains nonexisting classname '" + className + "'");
                    }
                }
            }

            return typeList.toArray(new Class[typeList.size()]);

        }
        else {
            return DEFAULT_EXCLUDED_TYPES;
        }

    }
}

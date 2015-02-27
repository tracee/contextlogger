package io.tracee.contextlogger;

import io.tracee.contextlogger.api.ContextLogger;
import io.tracee.contextlogger.api.ContextLoggerBuilder;
import io.tracee.contextlogger.api.TraceeContextStringRepresentationBuilder;
import io.tracee.contextlogger.api.internal.ContextLoggerBuilderAccessable;
import io.tracee.contextlogger.connector.ConnectorOutputProvider;
import io.tracee.contextlogger.connector.LogConnectorOutputProvider;
import io.tracee.contextlogger.impl.ContextLoggerBuilderImpl;
import io.tracee.contextlogger.impl.ContextLoggerConfiguration;

/**
 * The main context logger class.
 * This class is used to generate context information
 * <p/>
 * Created by Tobias Gindler, holisticon AG on 14.03.14.
 */

public final class TraceeContextLogger implements ContextLoggerBuilderAccessable, LogConnectorOutputProvider {

    private ConnectorFactory connectorsWrapper;
    private final ContextLoggerConfiguration contextLoggerConfiguration;
    private TraceeContextStringRepresentationBuilder traceeContextLogBuilder;

    private String prefix;
    private Class[] excludedTypes;
    private Object[] objectsToProcess;

    private TraceeContextLogger(ContextLoggerConfiguration contextLoggerConfiguration) {
        this.contextLoggerConfiguration = contextLoggerConfiguration;
        initConnectors();
    }

    private TraceeContextLogger(TraceeContextLogger instanceToClone) {

        this.connectorsWrapper = instanceToClone.connectorsWrapper;
        this.contextLoggerConfiguration = instanceToClone.contextLoggerConfiguration;
        this.traceeContextLogBuilder = instanceToClone.traceeContextLogBuilder;

        this.prefix = instanceToClone.prefix;
        this.excludedTypes = instanceToClone.excludedTypes;
        this.objectsToProcess = instanceToClone.objectsToProcess;

    }

    /**
     * Initializes all available connectors.
     */
    private void initConnectors() {
        connectorsWrapper = new ConnectorFactory();
    }

    public static ContextLoggerBuilder create() {

        TraceeContextLogger contextLoggerInstance = new TraceeContextLogger(ContextLoggerConfiguration.getOrCreateContextLoggerConfiguration());
        return new ContextLoggerBuilderImpl(contextLoggerInstance);

    }

    public static ContextLogger createDefault() {
        return create().build();
    }

    @Override
    public String createJson(Object... instancesToLog) {
        return traceeContextLogBuilder.createStringRepresentation(instancesToLog);
    }

    @Override
    public void logJson(Object... instancesToLog) {
        this.logJsonWithPrefixedMessage(null, instancesToLog);
    }

    @Override
    public void logJsonWithPrefixedMessage(String prefixedMessage, Object... instancesToLog) {

        this.prefix = prefixedMessage;
        this.objectsToProcess = instancesToLog;

        this.connectorsWrapper.sendErrorReportToConnectors(this);
    }

    @Override
    public ConnectorOutputProvider excludeContextProviders(final Class... contextProvidersToInclude) {

        TraceeContextLogger traceeContextLogger = new TraceeContextLogger(this);
        traceeContextLogger.excludedTypes = contextProvidersToInclude;

        return traceeContextLogger;
    }

    @Override
    public String provideOutput() {

        return traceeContextLogBuilder.createStringRepresentation(objectsToProcess);
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    boolean isInstanceTypeExcluded(Object contextProviderType) {

        if (excludedTypes != null && contextProviderType != null) {

            for (Class type : excludedTypes) {

                if (type.isAssignableFrom(contextProviderType.getClass())) {
                    return true;
                }

            }

        }
        return false;
    }

    @Override
    public void setStringRepresentationBuilder(TraceeContextStringRepresentationBuilder stringRepresentationBuilder) {
        this.traceeContextLogBuilder = stringRepresentationBuilder;
    }

    @Override
    public ContextLoggerConfiguration getContextLoggerConfiguration() {
        return this.contextLoggerConfiguration;
    }
}

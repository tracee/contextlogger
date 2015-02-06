package io.tracee.contextlogger;

import java.util.ArrayList;
import java.util.List;

import io.tracee.contextlogger.api.ContextLogger;
import io.tracee.contextlogger.api.ContextLoggerBuilder;
import io.tracee.contextlogger.api.ImplicitContext;
import io.tracee.contextlogger.api.TraceeContextStringRepresentationBuilder;
import io.tracee.contextlogger.api.internal.ContextLoggerBuilderAccessable;
import io.tracee.contextlogger.connector.ConnectorOutputProvider;
import io.tracee.contextlogger.connector.LogConnectorOutputProvider;
import io.tracee.contextlogger.contextprovider.TypeToWrapper;
import io.tracee.contextlogger.contextprovider.api.CustomImplicitContextData;
import io.tracee.contextlogger.contextprovider.api.WrappedContextData;
import io.tracee.contextlogger.contextprovider.tracee.PassedDataContextProvider;
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
        return propagateToContextLogBuilder(instancesToLog);
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

        List<Object> propagateList = new ArrayList<Object>();
        Object[] propagateArray;

        if (objectsToProcess != null) {

            for (int i = 0; i < objectsToProcess.length; i++) {

                Object contextProviderInstance = wrapInstance(objectsToProcess[i]);
                if (!isInstanceTypeExcluded(contextProviderInstance)) {
                    propagateList.add(contextProviderInstance);
                }
            }
        }

        propagateArray = propagateList.toArray(new Object[propagateList.size()]);

        return traceeContextLogBuilder.createStringRepresentationForPassedDataContextProvider(new PassedDataContextProvider(propagateArray,
                traceeContextLogBuilder.getKeepOrder()));
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

    /**
     * This method handles the wrapping of the incoming object and passes them to the context toJson builder implementation.
     *
     * @param instancesToLog an array of objects to wrap
     * @return the contextual toJson information as a String
     */

    String propagateToContextLogBuilder(Object[] instancesToLog) {

        Object[] propagateArray = null;
        if (instancesToLog != null) {
            propagateArray = new Object[instancesToLog.length];

            for (int i = 0; i < instancesToLog.length; i++) {
                propagateArray[i] = wrapInstance(instancesToLog[i]);
            }
        }

        return traceeContextLogBuilder.createStringRepresentationForPassedDataContextProvider(new PassedDataContextProvider(propagateArray,
                traceeContextLogBuilder.getKeepOrder()));
    }

    /**
     * tries to wrap a single instance into known wrapper class instances.
     *
     * @param instance
     * @return
     */
    private Object wrapInstance(Object instance) {

        if (instance == null) {
            return null;
        }

        // check for known implicit instances
        if (instance instanceof ImplicitContext) {
            return createInstance(contextLoggerConfiguration.getImplicitContextClassMap().get(instance));
        }

        // check for external implicit context provider
        if (instance instanceof CustomImplicitContextData) {
            return instance;
        }

        // check for external implicit context provider type
        if (instance instanceof Class && CustomImplicitContextData.class.isAssignableFrom((Class)instance)) {
            try {
                return ((Class)instance).newInstance();
            }
            catch (Exception e) {
                // ignore exception
            }
        }

        // now try to find instance type in known wrapper types map
        Class knownWrapperType = contextLoggerConfiguration.getClassToWrapperMap().get(instance.getClass());
        if (knownWrapperType != null) {
            Object wrappedInstance = createInstance(knownWrapperType);
            if (WrappedContextData.class.isAssignableFrom(knownWrapperType)) {
                ((WrappedContextData)wrappedInstance).setContextData(instance);
            }
        }

        // now try to find instance type in TypeToWrapper List
        for (TypeToWrapper wrapper : contextLoggerConfiguration.getWrapperList()) {
            if (wrapper.getWrappedInstanceType().isAssignableFrom(instance.getClass())) {
                try {
                    WrappedContextData wrapperInstance = (WrappedContextData)createInstance(wrapper.getWrapperType());
                    wrapperInstance.setContextData(instance);

                    // THIS WON'T WORK ANYMORE BECAUSE MAP IS IMMUTABLE
                    // if (wrapperInstance != null) {
                    // add class to map for future usage
                    // classToWrapperMap.put(instance.getClass(), wrapper.getWrapperType());
                    // }

                    return wrapperInstance;
                }
                catch (Exception e) {
                    // continue
                    return null;
                }

            }
        }

        // if instance can't be wrapped pass instance as is
        return instance;
    }

    /**
     * Creates a new instance of the passed type via reflection.
     *
     * @param type the type of the new instance
     * @return a new instance of the passed type or null if an exception occurred during the creation of the instance of if the passed type is null.
     */
    private Object createInstance(final Class type) {
        if (type != null) {
            try {
                return type.newInstance();
            }
            catch (Exception e) {
                // should not occur
            }
        }
        return null;
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

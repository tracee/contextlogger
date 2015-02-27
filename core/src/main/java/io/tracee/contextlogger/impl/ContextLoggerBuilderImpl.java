package io.tracee.contextlogger.impl;

import io.tracee.contextlogger.api.ConfigBuilder;
import io.tracee.contextlogger.api.ContextLogger;
import io.tracee.contextlogger.api.ContextLoggerBuilder;
import io.tracee.contextlogger.api.internal.Configuration;
import io.tracee.contextlogger.api.internal.ContextLoggerBuilderAccessable;
import io.tracee.contextlogger.output.internal.JsonTraceeContextStringRepresentationBuilder;

/**
 * Class for creating and configuring a gson context logger.
 * Supports the fluent builder api.
 */
public class ContextLoggerBuilderImpl implements ContextLoggerBuilder {

    private final ContextLoggerBuilderAccessable contextLogger;
    private final ContextLoggerConfiguration contextLoggerConfiguration;

    private Configuration configuration = new ConfigBuilderImpl(this);

    public ContextLoggerBuilderImpl(ContextLoggerBuilderAccessable traceeContextLoggerBuilderAccessable) {
        this.contextLogger = traceeContextLoggerBuilderAccessable;
        this.contextLoggerConfiguration = traceeContextLoggerBuilderAccessable.getContextLoggerConfiguration();
    }

    @Override
    public ConfigBuilder config() {
        return configuration;
    }

    @Override
    public ContextLogger build() {
        contextLogger.setStringRepresentationBuilder(createJsonContextStringRepresentationLogBuilder());
        return contextLogger;
    }

    /**
     * Creates a TraceeGsonContextStringRepresentationBuilder instance which can be used for creating the createStringRepresentation message.
     *
     * @return An instance of TraceeGsonContextStringRepresentationBuilder
     */
    private JsonTraceeContextStringRepresentationBuilder createJsonContextStringRepresentationLogBuilder() {

        JsonTraceeContextStringRepresentationBuilder jsonTraceeContextStringRepresentationBuilder = new JsonTraceeContextStringRepresentationBuilder();
        jsonTraceeContextStringRepresentationBuilder.setWrapperClasses(contextLoggerConfiguration.getWrapperClasses());
        jsonTraceeContextStringRepresentationBuilder.setManualContextOverrides(configuration.getManualContextOverrides());
        jsonTraceeContextStringRepresentationBuilder.setProfile(this.configuration.getProfile());
        jsonTraceeContextStringRepresentationBuilder.setKeepOrder(this.configuration.getKeepOrder());

        return jsonTraceeContextStringRepresentationBuilder;
    }
}

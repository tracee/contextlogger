package io.tracee.contextlogger.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.tracee.contextlogger.api.ConfigBuilder;
import io.tracee.contextlogger.api.ToStringBuilder;
import io.tracee.contextlogger.api.internal.Configuration;
import io.tracee.contextlogger.api.internal.ContextLoggerBuilderAccessable;
import io.tracee.contextlogger.outputgenerator.TraceeContextStringRepresentationBuilderImpl;
import io.tracee.contextlogger.outputgenerator.writer.BasicOutputWriterConfiguration;
import io.tracee.contextlogger.outputgenerator.writer.OutputWriterConfiguration;
import io.tracee.contextlogger.profile.Profile;

/**
 * Implementation class to create a configuration by using the fluent api.
 */
public class ConfigBuilderImpl<T extends ToStringBuilder> implements Configuration<T> {

    private final ContextLoggerBuilderAccessable contextLogger;
    private final ContextLoggerConfiguration contextLoggerConfiguration;

    private Profile profile = null;

    private boolean keepOrder = false;

    private Map<String, Boolean> manualContextOverrides = new HashMap<String, Boolean>();

    private OutputWriterConfiguration outputWriterConfiguration = BasicOutputWriterConfiguration.JSON_INTENDED;

    public ConfigBuilderImpl(ContextLoggerBuilderAccessable traceeContextLoggerBuilderAccessable) {

        this.contextLogger = traceeContextLoggerBuilderAccessable;
        this.contextLoggerConfiguration = traceeContextLoggerBuilderAccessable.getContextLoggerConfiguration();

    }

    @Override
    public final ConfigBuilderImpl enforceProfile(Profile profile) {
        this.profile = profile;
        return this;
    }

    @Override
    public ConfigBuilder enable(String... contexts) {
        fillManualContextOverrideMap(contexts, true);
        return this;
    }

    @Override
    public ConfigBuilder disableTypes(final Class... types) {
        List<String> classNames = new ArrayList<String>();
        if (types != null) {

            for (Class type : types) {
                if (type != null) {
                    this.manualContextOverrides.put(type.getCanonicalName(), Boolean.FALSE);
                }
            }

        }
        return this;
    }

    @Override
    public ConfigBuilder disable(String... contexts) {
        fillManualContextOverrideMap(contexts, false);
        return this;
    }

    @Override
    public ConfigBuilder keepOrder() {
        this.keepOrder = true;
        return this;
    }

    @Override
    public ConfigBuilder enforceOutputWriterConfiguration(final BasicOutputWriterConfiguration outputWriterConfiguration) {
        this.outputWriterConfiguration = outputWriterConfiguration;
        return this;
    }

    @Override
    public T apply() {
        contextLogger.setStringRepresentationBuilder(createContextStringRepresentationLogBuilder());
        return (T)contextLogger;
    }

    public Map<String, Boolean> getManualContextOverrides() {
        return manualContextOverrides;
    }

    public Profile getProfile() {
        return profile;
    }

    public boolean getKeepOrder() {
        return keepOrder;
    }

    @Override
    public OutputWriterConfiguration getOutputWriterConfiguration() {
        return this.outputWriterConfiguration;
    }

    /**
     * Adds passed contexts value pairs to manualContextOverrides.
     *
     * @param contexts The property name of the context data.
     * @param value the value which should be set.
     */
    private void fillManualContextOverrideMap(final String[] contexts, final boolean value) {
        if (contexts != null) {

            for (String context : contexts) {

                if (!context.isEmpty()) {
                    this.manualContextOverrides.put(context, value);
                }

            }

        }
    }

    /**
     * Creates a TraceeGsonContextStringRepresentationBuilder instance which can be used for creating the createStringRepresentation message.
     *
     * @return An instance of TraceeGsonContextStringRepresentationBuilder
     */
    private TraceeContextStringRepresentationBuilderImpl createContextStringRepresentationLogBuilder() {

        TraceeContextStringRepresentationBuilderImpl traceeContextStringRepresentationBuilderImpl = new TraceeContextStringRepresentationBuilderImpl();
        traceeContextStringRepresentationBuilderImpl.setWrapperClasses(contextLoggerConfiguration.getWrapperClasses());
        traceeContextStringRepresentationBuilderImpl.setManualContextOverrides(this.getManualContextOverrides());
        traceeContextStringRepresentationBuilderImpl.setProfile(this.getProfile());
        traceeContextStringRepresentationBuilderImpl.setKeepOrder(this.getKeepOrder());
        traceeContextStringRepresentationBuilderImpl.setOutputWriterConfiguration(this.getOutputWriterConfiguration());

        return traceeContextStringRepresentationBuilderImpl;
    }
}

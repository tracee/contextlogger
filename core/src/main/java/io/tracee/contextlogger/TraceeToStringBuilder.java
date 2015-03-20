package io.tracee.contextlogger;

import io.tracee.contextlogger.api.ConfigBuilder;
import io.tracee.contextlogger.api.ToStringBuilder;
import io.tracee.contextlogger.impl.ConfigBuilderImpl;
import io.tracee.contextlogger.impl.ContextLoggerConfiguration;

/**
 * Class for creation of String representations for instance trees.
 */
public class TraceeToStringBuilder extends AbstractToStringBuilder<TraceeToStringBuilder> {

    private TraceeToStringBuilder(ContextLoggerConfiguration contextLoggerConfiguration) {
        super(contextLoggerConfiguration);
    }

    public static ConfigBuilder<TraceeToStringBuilder> create() {

        TraceeToStringBuilder toStringBuilder = new TraceeToStringBuilder(ContextLoggerConfiguration.getOrCreateContextLoggerConfiguration());
        return new ConfigBuilderImpl<TraceeToStringBuilder>(toStringBuilder);

    }

    public static ToStringBuilder createDefault() {
        return create().apply();
    }

    @Override
    public void log(final Object... instancesToLog) {
        // NOT IMPLEMENTED
    }

    @Override
    public void logWithPrefixedMessage(final String prefixedMessage, final Object... instancesToLog) {
        // NOT IMPLEMENTED
    }
}

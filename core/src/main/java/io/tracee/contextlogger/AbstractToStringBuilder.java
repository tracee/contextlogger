package io.tracee.contextlogger;

import io.tracee.contextlogger.api.ToStringBuilder;
import io.tracee.contextlogger.api.TraceeContextStringRepresentationBuilder;
import io.tracee.contextlogger.api.WrappedContextLoggerOutput;
import io.tracee.contextlogger.api.internal.ContextLoggerBuilderAccessable;
import io.tracee.contextlogger.impl.ContextLoggerConfiguration;

/**
 * Abstract to String Builder class.
 */
public abstract class AbstractToStringBuilder<T extends ToStringBuilder> implements ContextLoggerBuilderAccessable {

    protected final ContextLoggerConfiguration contextLoggerConfiguration;
    protected TraceeContextStringRepresentationBuilder traceeContextLogBuilder;

    protected Object[] objectsToProcess;

    protected AbstractToStringBuilder(ContextLoggerConfiguration contextLoggerConfiguration) {
        this.contextLoggerConfiguration = contextLoggerConfiguration;
    }

    protected AbstractToStringBuilder(AbstractToStringBuilder instanceToClone) {

        this.contextLoggerConfiguration = instanceToClone.contextLoggerConfiguration;
        this.traceeContextLogBuilder = instanceToClone.traceeContextLogBuilder;

        this.objectsToProcess = instanceToClone.objectsToProcess;

    }

    @Override
    public String toString(Object... instancesToLog) {
        return traceeContextLogBuilder.createStringRepresentation(instancesToLog);
    }

    @Override
    public String create(Object... instancesToLog) {
        return toString(instancesToLog);
    }

    @Override
    public WrappedContextLoggerOutput wrap(final Object... instances) {
        return WrappedContextLoggerOutput.wrap(this, instances);
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
package io.tracee.contextlogger.api;

import io.tracee.contextlogger.TraceeContextLogger;

/**
 * Class for providing output at runtime vi toString.
 */
public class WrappedContextLoggerOutput {

    private final TraceeContextLogger traceeContextLogger;
    private String generatedOutput;
    private final Object[] instancesToOutput;

    private WrappedContextLoggerOutput(TraceeContextLogger traceeContextLogger, Object... instancesToOutput) {
        this.traceeContextLogger = traceeContextLogger;
        this.instancesToOutput = instancesToOutput;
    }

    @Override
    public String toString() {
        if (generatedOutput == null) {
            generatedOutput = (traceeContextLogger != null ? traceeContextLogger.createJson(instancesToOutput) : null);
        }

        return generatedOutput;
    }

    public static WrappedContextLoggerOutput wrap(TraceeContextLogger traceeContextLogger, Object... instancesToOutput) {
        return new WrappedContextLoggerOutput(traceeContextLogger, instancesToOutput);
    }

}

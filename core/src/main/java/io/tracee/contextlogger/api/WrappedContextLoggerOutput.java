package io.tracee.contextlogger.api;

/**
 * Class for providing output at runtime vi toString.
 */
public class WrappedContextLoggerOutput {

    private final ContextLogger traceeContextLogger;
    private String generatedOutput;
    private final Object[] instancesToOutput;

    private WrappedContextLoggerOutput(ContextLogger traceeContextLogger, Object... instancesToOutput) {
        this.traceeContextLogger = traceeContextLogger;
        this.instancesToOutput = instancesToOutput;
    }

    @Override
    public String toString() {
        if (generatedOutput == null) {
            generatedOutput = (traceeContextLogger != null ? traceeContextLogger.create(instancesToOutput) : null);
        }

        return generatedOutput;
    }

    public static WrappedContextLoggerOutput wrap(ContextLogger traceeContextLogger, Object... instancesToOutput) {
        return new WrappedContextLoggerOutput(traceeContextLogger, instancesToOutput);
    }

}

package io.tracee.contextlogger.output.internal.writer.nullvalue;

import io.tracee.contextlogger.output.internal.outputelements.NullValueOutputElement;

/**
 * Simple null value output element writer implementation.
 */
public class SimpleNullValueOutputElementWriter implements NullValueOutputElementWriter {

    @Override
    public String produceOutput(final NullValueOutputElement outputElement) {

        return "null";
    }
}

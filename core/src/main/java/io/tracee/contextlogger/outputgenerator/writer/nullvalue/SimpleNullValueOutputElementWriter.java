package io.tracee.contextlogger.outputgenerator.writer.nullvalue;

import io.tracee.contextlogger.outputgenerator.outputelements.NullValueOutputElement;
import io.tracee.contextlogger.outputgenerator.writer.api.NullValueOutputElementWriter;

/**
 * Simple null value output element writer implementation.
 */
public class SimpleNullValueOutputElementWriter implements NullValueOutputElementWriter {

    @Override
    public String produceOutput(final NullValueOutputElement outputElement) {

        return "null";
    }
}

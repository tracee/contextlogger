package io.tracee.contextlogger.outputgenerator.writer.api;

import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;
import io.tracee.contextlogger.outputgenerator.writer.OutputWriterConfiguration;

/**
 * Interface for processing output elements recursively.
 */
public interface OutputWriter {

    /**
     * Initializes all output writer types.
     *
     * @param outputWriterConfiguration the writers configuration.
     */
    void init(final OutputWriterConfiguration outputWriterConfiguration);

    /**
     * Method for recursive creation of string output. Will be calles by all OutputElementWriters
     */
    void produceOutputRecursively(final StringBuilder stringBuilder, final OutputStyle outputStyle, OutputElement outputElement);

}

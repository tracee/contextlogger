package io.tracee.contextlogger.output.internal.writer;

import io.tracee.contextlogger.output.internal.outputelements.OutputElement;
import io.tracee.contextlogger.output.internal.writer.atomic.AtomicOutputElementWriter;
import io.tracee.contextlogger.output.internal.writer.collection.CollectionOutputElementWriter;
import io.tracee.contextlogger.output.internal.writer.complex.ComplexOutputElementWriter;
import io.tracee.contextlogger.output.internal.writer.styles.OutputStyle;

/**
 * Interface for processing output elements recursively.
 */
public interface OutputWriter {

    /**
     * Initializes all output writer types.
     *
     * @param complexOutputElementWriter the writer for all complex output elements
     * @param collectionOutputElementWriter the writer for all collection output elements
     * @param atomicOutputElementWriter the writer for all atomic output elements
     */
    void init(final ComplexOutputElementWriter complexOutputElementWriter, final CollectionOutputElementWriter collectionOutputElementWriter,
            final AtomicOutputElementWriter atomicOutputElementWriter);

    /**
     * Method for recursive creation of string output. Will be calles by all OutputElementWriters
     */
    void produceOutputRecursively(final StringBuilder stringBuilder, final OutputStyle outputStyle, OutputElement outputElement);

}

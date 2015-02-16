package io.tracee.contextlogger.output.internal.writer;

import io.tracee.contextlogger.output.internal.OutputElement;
import io.tracee.contextlogger.output.internal.writer.atomic.AtomicOutputElementWriter;
import io.tracee.contextlogger.output.internal.writer.circular.CircularReferenceOutputElementWriter;
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
     * @param circularReferenceOutputElementWriter the writer for all circular reference output elements
     */
    void init(final ComplexOutputElementWriter complexOutputElementWriter, final CollectionOutputElementWriter collectionOutputElementWriter,
            final AtomicOutputElementWriter atomicOutputElementWriter, final CircularReferenceOutputElementWriter circularReferenceOutputElementWriter);

    /**
     * Method for recursive creation of string output. Will be calles by all OutputElementWriters
     */
    void produceOutputRecursively(final StringBuilder stringBuilder, final OutputStyle outputStyle, OutputElement outputElement);

}

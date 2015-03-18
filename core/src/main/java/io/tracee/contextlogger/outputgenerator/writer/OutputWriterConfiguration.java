package io.tracee.contextlogger.outputgenerator.writer;

import io.tracee.contextlogger.outputgenerator.writer.api.*;

/**
 * Interface for Output writer configurations.
 */
public interface OutputWriterConfiguration {

    OutputStyle getOutputStyle();

    AlreadyProcessedReferenceOutputElementWriter getAlreadyProcessedReferenceOutputElementWriter();

    AtomicOutputElementWriter getAtomicOutputElementWriter();

    CollectionOutputElementWriter getCollectionOutputElementWriter();

    ComplexOutputElementWriter getComplexOutputElementWriter();

    NullValueOutputElementWriter getNullValueOutputElementWriter();

}

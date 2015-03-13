package io.tracee.contextlogger.outputgenerator.writer;

import io.tracee.contextlogger.outputgenerator.writer.alreadyprocessed.SimpleAlreadyProcessedReferenceOutputElementWriter;
import io.tracee.contextlogger.outputgenerator.writer.api.*;
import io.tracee.contextlogger.outputgenerator.writer.atomic.TypedWithInstanceIdToStringAtomicOutputElementWriter;
import io.tracee.contextlogger.outputgenerator.writer.collection.SimpleCollectionOutputElementWriter;
import io.tracee.contextlogger.outputgenerator.writer.complex.SimpleComplexOutputElementWriter;
import io.tracee.contextlogger.outputgenerator.writer.nullvalue.SimpleNullValueOutputElementWriter;
import io.tracee.contextlogger.outputgenerator.writer.styles.json.IntendedJsonOutputStyle;
import io.tracee.contextlogger.outputgenerator.writer.styles.json.SimpleJsonOutputStyle;

/**
 * Class that is used to group writers for an OutputWriterConfiguration.
 */
public enum OutputWriterConfiguration {

    JSON_INLINE(new SimpleJsonOutputStyle(), new SimpleComplexOutputElementWriter(), new SimpleCollectionOutputElementWriter(),
            new TypedWithInstanceIdToStringAtomicOutputElementWriter(), new SimpleAlreadyProcessedReferenceOutputElementWriter(),
            new SimpleNullValueOutputElementWriter()),
    JSON_INTENDED(new IntendedJsonOutputStyle(), new SimpleComplexOutputElementWriter(), new SimpleCollectionOutputElementWriter(),
            new TypedWithInstanceIdToStringAtomicOutputElementWriter(), new SimpleAlreadyProcessedReferenceOutputElementWriter(),
            new SimpleNullValueOutputElementWriter());

    private final OutputStyle outputStyle;
    private final ComplexOutputElementWriter complexOutputElementWriter;
    private final CollectionOutputElementWriter collectionOutputElementWriter;
    private final AtomicOutputElementWriter atomicOutputElementWriter;
    private final AlreadyProcessedReferenceOutputElementWriter alreadyProcessedReferenceOutputElementWriter;
    private final NullValueOutputElementWriter nullValueOutputElementWriter;

    OutputWriterConfiguration(final OutputStyle outputStyle, final ComplexOutputElementWriter complexOutputElementWriter,
            final CollectionOutputElementWriter collectionOutputElementWriter, final AtomicOutputElementWriter atomicOutputElementWriter,
            final AlreadyProcessedReferenceOutputElementWriter alreadyProcessedReferenceOutputElementWriter,
            final NullValueOutputElementWriter nullValueOutputElementWriter) {

        this.outputStyle = outputStyle;
        this.complexOutputElementWriter = complexOutputElementWriter;
        this.collectionOutputElementWriter = collectionOutputElementWriter;
        this.atomicOutputElementWriter = atomicOutputElementWriter;
        this.alreadyProcessedReferenceOutputElementWriter = alreadyProcessedReferenceOutputElementWriter;
        this.nullValueOutputElementWriter = nullValueOutputElementWriter;

    }

    /**
     * Please be adviced that output style keeps state in recursion. Therefore check always if the configuration is valid before use.
     *
     * @return the output style representing the initial recursion state
     */
    public OutputStyle getOutputStyle() {
        return outputStyle;
    }

    public AlreadyProcessedReferenceOutputElementWriter getAlreadyProcessedReferenceOutputElementWriter() {
        return alreadyProcessedReferenceOutputElementWriter;
    }

    public AtomicOutputElementWriter getAtomicOutputElementWriter() {
        return atomicOutputElementWriter;
    }

    public CollectionOutputElementWriter getCollectionOutputElementWriter() {
        return collectionOutputElementWriter;
    }

    public ComplexOutputElementWriter getComplexOutputElementWriter() {
        return complexOutputElementWriter;
    }

    public NullValueOutputElementWriter getNullValueOutputElementWriter() {
        return nullValueOutputElementWriter;
    }
}

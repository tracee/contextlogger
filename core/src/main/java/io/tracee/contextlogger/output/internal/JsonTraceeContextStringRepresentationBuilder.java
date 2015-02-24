package io.tracee.contextlogger.output.internal;

import io.tracee.contextlogger.impl.AbstractContextStringRepresentationBuilder;
import io.tracee.contextlogger.output.internal.outputelements.OutputElement;
import io.tracee.contextlogger.output.internal.writer.OutputWriterToOutputTransformer;
import io.tracee.contextlogger.output.internal.writer.atomic.AtomicOutputElementWriter;
import io.tracee.contextlogger.output.internal.writer.atomic.TypedWithInstanceIdToStringAtomicOutputElementWriter;
import io.tracee.contextlogger.output.internal.writer.collection.CollectionOutputElementWriter;
import io.tracee.contextlogger.output.internal.writer.collection.SimpleCollectionOutputElementWriter;
import io.tracee.contextlogger.output.internal.writer.complex.ComplexOutputElementWriter;
import io.tracee.contextlogger.output.internal.writer.complex.SimpleComplexOutputElementWriter;
import io.tracee.contextlogger.output.internal.writer.styles.OutputStyle;
import io.tracee.contextlogger.output.internal.writer.styles.json.IntendedJsonOutputStyle;
import io.tracee.contextlogger.profile.ProfileSettings;

/**
 * Main class for creating json output.
 */
public class JsonTraceeContextStringRepresentationBuilder extends AbstractContextStringRepresentationBuilder {

    private final OutputStyle outputStyle;
    private final AtomicOutputElementWriter atomicOutputElementWriter;
    private final ComplexOutputElementWriter complexOutputElementWriter;
    private final CollectionOutputElementWriter collectionOutputElementWriter;

    public JsonTraceeContextStringRepresentationBuilder() {
        outputStyle = new IntendedJsonOutputStyle();
        atomicOutputElementWriter = new TypedWithInstanceIdToStringAtomicOutputElementWriter();
        collectionOutputElementWriter = new SimpleCollectionOutputElementWriter();
        complexOutputElementWriter = new SimpleComplexOutputElementWriter();
    }

    @Override
    public String createStringRepresentation(final Object... instancesToLog) {
        OutputElement tmp = ContextDeserializer.deserializeContexts(new ProfileSettings(this.getProfile(), this.getManualContextOverrides()),
                instancesToLog);

        return OutputWriterToOutputTransformer.produceOutput(outputStyle, complexOutputElementWriter, collectionOutputElementWriter,
                atomicOutputElementWriter, tmp);
    }
}

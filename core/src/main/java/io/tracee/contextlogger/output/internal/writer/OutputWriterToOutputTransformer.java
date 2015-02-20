package io.tracee.contextlogger.output.internal.writer;

import io.tracee.contextlogger.output.internal.outputelements.*;
import io.tracee.contextlogger.output.internal.writer.atomic.AtomicOutputElementWriter;
import io.tracee.contextlogger.output.internal.writer.circular.CircularReferenceOutputElementWriter;
import io.tracee.contextlogger.output.internal.writer.circular.SimpleCircularReferenceOutputElementWriter;
import io.tracee.contextlogger.output.internal.writer.collection.CollectionOutputElementWriter;
import io.tracee.contextlogger.output.internal.writer.complex.ComplexOutputElementWriter;
import io.tracee.contextlogger.output.internal.writer.styles.OutputStyle;

/**
 * Generates the output for an OutputElement tree
 */
public class OutputWriterToOutputTransformer implements OutputWriter {

    private ComplexOutputElementWriter complexOutputElementWriter;
    private CollectionOutputElementWriter collectionOutputElementWriter;
    private AtomicOutputElementWriter atomicOutputElementWriter;
    private CircularReferenceOutputElementWriter circularReferenceOutputElementWriter = new SimpleCircularReferenceOutputElementWriter();
    private OutputElementPool outputElementPool = new OutputElementPool();

    /**
     * Hidden constructor.
     */
    private OutputWriterToOutputTransformer() {

    }

    @Override
    public void init(final ComplexOutputElementWriter complexOutputElementWriter, final CollectionOutputElementWriter collectionOutputElementWriter,
            final AtomicOutputElementWriter atomicOutputElementWriter) {
        this.complexOutputElementWriter = complexOutputElementWriter;
        this.collectionOutputElementWriter = collectionOutputElementWriter;
        this.atomicOutputElementWriter = atomicOutputElementWriter;
    }

    @Override
    public void produceOutputRecursively(final StringBuilder stringBuilder, final OutputStyle outputStyle, OutputElement outputElement) {

        if (!OutputElementType.ATOMIC.equals(outputElement.getOutputElementType()) && outputElementPool.isInstanceInPool(outputElement)) {
            stringBuilder.append(outputStyle.openingAtomicType());
            stringBuilder.append(outputStyle.escapeString(circularReferenceOutputElementWriter.produceOutput(outputElement)));
            stringBuilder.append(outputStyle.closingAtomicType());
            return;
        }

        outputElementPool.add(outputElement);

        switch (outputElement.getOutputElementType()) {

            case COMPLEX: {

                ComplexOutputElement complexOutputElement = (ComplexOutputElement)outputElement;

                complexOutputElementWriter.produceOutput(this, stringBuilder, outputStyle, complexOutputElement);

                break;
            }
            case COLLECTION: {

                CollectionOutputElement collectionOutputElement = (CollectionOutputElement)outputElement;

                this.collectionOutputElementWriter.produceOutput(this, stringBuilder, outputStyle, collectionOutputElement);

                break;
            }
            case ATOMIC:
            default: {
                AtomicOutputElement atomicOutputElement = (AtomicOutputElement)outputElement;

                stringBuilder.append(outputStyle.openingAtomicType());
                stringBuilder.append(outputStyle.escapeString(atomicOutputElementWriter.produceOutput(atomicOutputElement)));
                stringBuilder.append(outputStyle.closingAtomicType());

            }
        }

    }

    public static String produceOutput(final OutputStyle outputStyle, final ComplexOutputElementWriter complexOutputElementWriter,
            final CollectionOutputElementWriter collectionOutputElementWriter, final AtomicOutputElementWriter atomicOutputElementWriter,
            final OutputElement outputElement) {

        StringBuilder stringBuilder = new StringBuilder();
        OutputWriterToOutputTransformer outputWriterToOutputTransformer = new OutputWriterToOutputTransformer();
        outputWriterToOutputTransformer.init(complexOutputElementWriter, collectionOutputElementWriter, atomicOutputElementWriter);
        outputWriterToOutputTransformer.produceOutputRecursively(stringBuilder, outputStyle, outputElement);
        return stringBuilder.toString();
    }
}

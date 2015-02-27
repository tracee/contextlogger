package io.tracee.contextlogger.output.internal.writer;

import io.tracee.contextlogger.output.internal.outputelements.*;
import io.tracee.contextlogger.output.internal.writer.api.OutputStyle;
import io.tracee.contextlogger.output.internal.writer.api.OutputWriter;

/**
 * Generates the output for an OutputElement tree
 */
public class OutputWriterToOutputTransformer implements OutputWriter {

    private OutputWriterConfiguration outputWriterConfiguration;
    private OutputElementPool outputElementPool = new OutputElementPool();

    /**
     * Hidden constructor.
     */
    private OutputWriterToOutputTransformer() {

    }

    @Override
    public void init(final OutputWriterConfiguration outputWriterConfiguration) {
        this.outputWriterConfiguration = outputWriterConfiguration;
    }

    @Override
    public void produceOutputRecursively(final StringBuilder stringBuilder, final OutputStyle outputStyle, OutputElement outputElement) {

        if ((!OutputElementType.ATOMIC.equals(outputElement.getOutputElementType()) && !OutputElementType.NULL.equals(outputElement.getOutputElementType()))
                && outputElementPool.isInstanceInPool(outputElement)) {
            stringBuilder.append(outputStyle.openingAtomicType());
            stringBuilder.append(outputStyle.escapeString(outputWriterConfiguration.getAlreadyProcessedReferenceOutputElementWriter().produceOutput(
                    outputElement)));
            stringBuilder.append(outputStyle.closingAtomicType());
            return;
        }

        outputElementPool.add(outputElement);

        switch (outputElement.getOutputElementType()) {

            case COMPLEX: {

                ComplexOutputElement complexOutputElement = (ComplexOutputElement)outputElement;

                outputWriterConfiguration.getComplexOutputElementWriter().produceOutput(this, stringBuilder, outputStyle, complexOutputElement);

                break;
            }
            case COLLECTION: {

                CollectionOutputElement collectionOutputElement = (CollectionOutputElement)outputElement;

                this.outputWriterConfiguration.getCollectionOutputElementWriter().produceOutput(this, stringBuilder, outputStyle, collectionOutputElement);

                break;
            }
            case NULL: {

                NullValueOutputElement nullValueOutputElement = (NullValueOutputElement)outputElement;

                stringBuilder.append(this.outputWriterConfiguration.getNullValueOutputElementWriter().produceOutput(nullValueOutputElement));

                break;
            }

            case ATOMIC: {
                AtomicOutputElement atomicOutputElement = (AtomicOutputElement)outputElement;

                stringBuilder.append(outputStyle.openingAtomicType());
                stringBuilder.append(outputStyle.escapeString(this.outputWriterConfiguration.getAtomicOutputElementWriter().produceOutput(
                        atomicOutputElement)));
                stringBuilder.append(outputStyle.closingAtomicType());
                break;
            }
            default: {
                // nothing to do
            }
        }

    }

    public static String produceOutput(final OutputWriterConfiguration outputWriterConfiguration, final OutputElement outputElement) {

        StringBuilder stringBuilder = new StringBuilder();
        OutputWriterToOutputTransformer outputWriterToOutputTransformer = new OutputWriterToOutputTransformer();
        outputWriterToOutputTransformer.init(outputWriterConfiguration);
        outputWriterToOutputTransformer.produceOutputRecursively(stringBuilder, outputWriterConfiguration.getOutputStyle(), outputElement);
        return stringBuilder.toString();
    }
}

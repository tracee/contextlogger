package io.tracee.contextlogger.outputgenerator.writer;

import io.tracee.contextlogger.outputgenerator.outputelements.AtomicOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.CollectionOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.ComplexOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.NullValueOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;
import io.tracee.contextlogger.outputgenerator.writer.api.OutputStyle;
import io.tracee.contextlogger.outputgenerator.writer.api.OutputWriter;

/**
 * Generates the output for an OutputElement tree
 */
public class OutputWriterToOutputTransformer implements OutputWriter {

    private OutputWriterConfiguration outputWriterConfiguration;
    protected OutputElementPool outputElementPool = new OutputElementPool();

    /**
     * Hidden constructor.
     */
    protected OutputWriterToOutputTransformer() {

    }

    @Override
    public void init(final OutputWriterConfiguration outputWriterConfiguration) {
        this.outputWriterConfiguration = outputWriterConfiguration;
    }

    @Override
    public void produceOutputRecursively(final StringBuilder stringBuilder, final OutputStyle outputStyle, OutputElement outputElement) {

        if (outputElement.useReferencesIfMarkedAsMultipleReferenced() && outputElementPool.isInstanceInPool(outputElement)) {
            stringBuilder.append(outputStyle.openingAtomicType());
            stringBuilder.append(outputStyle.escapeString(outputWriterConfiguration.getAlreadyProcessedReferenceOutputElementWriter().produceOutput(
                    outputElement)));
            stringBuilder.append(outputStyle.closingAtomicType());
            return;
        }

        outputElementPool.add(outputElement);

        switch (outputElement.getOutputElementType()) {

            case COMPLEX: {

                ComplexOutputElement complexOutputElement = (ComplexOutputElement) outputElement;

                outputWriterConfiguration.getComplexOutputElementWriter().produceOutput(this, stringBuilder, outputStyle, complexOutputElement);

                break;
            }
            case COLLECTION: {

                CollectionOutputElement collectionOutputElement = (CollectionOutputElement) outputElement;

                this.outputWriterConfiguration.getCollectionOutputElementWriter().produceOutput(this, stringBuilder, outputStyle, collectionOutputElement);

                break;
            }
            case NULL: {

                NullValueOutputElement nullValueOutputElement = (NullValueOutputElement) outputElement;

                stringBuilder.append(this.outputWriterConfiguration.getNullValueOutputElementWriter().produceOutput(nullValueOutputElement));

                break;
            }

            case ATOMIC: {
                AtomicOutputElement atomicOutputElement = (AtomicOutputElement) outputElement;

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

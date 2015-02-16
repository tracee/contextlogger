package io.tracee.contextlogger.output.internal.writer;

import java.util.Map;

import io.tracee.contextlogger.output.internal.AtomicOutputElement;
import io.tracee.contextlogger.output.internal.CollectionOutputElement;
import io.tracee.contextlogger.output.internal.ComplexOutputElement;
import io.tracee.contextlogger.output.internal.OutputElement;
import io.tracee.contextlogger.output.internal.writer.atomic.AtomicOutputElementWriter;
import io.tracee.contextlogger.output.internal.writer.styles.OutputStyle;

/**
 * Generates the output for an OutputElement tree
 */
public class OutputWriterToOutputTransformer {

    /**
     * Hidden constructor.
     */
    private OutputWriterToOutputTransformer() {

    }

    void produceOutputRecursively(final StringBuilder stringBuilder, final OutputStyle outputStyle,
            final AtomicOutputElementWriter atomicOutputElementWriter, OutputElement outputElement) {

        switch (outputElement.getOutputElementType()) {

            case COMPLEX: {

                ComplexOutputElement complexOutputElement = (ComplexOutputElement)outputElement;

                stringBuilder.append(outputStyle.openingComplexType());

                // create style for children
                OutputStyle childrenOutputStyle = outputStyle.getChildConfiguration();

                boolean isFirst = true;
                for (Map.Entry<String, OutputElement> child : complexOutputElement.getOutputElements().entrySet()) {

                    if (!isFirst) {
                        stringBuilder.append(outputStyle.complexTypeElementSeparator());
                    }
                    else {
                        isFirst = false;
                    }

                    stringBuilder.append(outputStyle.complexTypeOpeningName());
                    stringBuilder.append(outputStyle.escapeString(child.getKey()));
                    stringBuilder.append(outputStyle.complexTypeClosingName());

                    stringBuilder.append(outputStyle.complexTypeNameValueSeparator());

                    produceOutputRecursively(stringBuilder, childrenOutputStyle, atomicOutputElementWriter, child.getValue());

                }

                stringBuilder.append(outputStyle.closingComplexType());

                break;
            }
            case COLLECTION: {

                CollectionOutputElement collectionOutputElement = (CollectionOutputElement)outputElement;

                stringBuilder.append(outputStyle.openingCollectionType());

                // create style for children
                OutputStyle childrenOutputStyle = outputStyle.getChildConfiguration();

                boolean isFirst = true;
                for (OutputElement child : collectionOutputElement.getOutputElements()) {

                    if (!isFirst) {
                        stringBuilder.append(outputStyle.collectionTypeElementSeparator());
                    }
                    else {
                        isFirst = false;
                    }

                    produceOutputRecursively(stringBuilder, childrenOutputStyle, atomicOutputElementWriter, child);

                }

                stringBuilder.append(outputStyle.closingCollectionType());

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

    public static String produceOutput(final OutputStyle outputStyle, final AtomicOutputElementWriter atomicOutputElementWriter,
            final OutputElement outputElement) {

        StringBuilder stringBuilder = new StringBuilder();
        new OutputWriterToOutputTransformer().produceOutputRecursively(stringBuilder, outputStyle, atomicOutputElementWriter, outputElement);
        return stringBuilder.toString();
    }
}

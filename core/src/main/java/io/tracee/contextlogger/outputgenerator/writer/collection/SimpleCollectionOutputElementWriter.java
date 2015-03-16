package io.tracee.contextlogger.outputgenerator.writer.collection;

import io.tracee.contextlogger.outputgenerator.outputelements.CollectionOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;
import io.tracee.contextlogger.outputgenerator.writer.api.CollectionOutputElementWriter;
import io.tracee.contextlogger.outputgenerator.writer.api.OutputStyle;
import io.tracee.contextlogger.outputgenerator.writer.api.OutputWriter;
import io.tracee.contextlogger.outputgenerator.writer.function.TypeProviderFunction;

/**
 * Simple collection output element writer implementation.
 */
public class SimpleCollectionOutputElementWriter implements CollectionOutputElementWriter {

    @Override
    public void produceOutput(final OutputWriter outputWriterInstance, final StringBuilder stringBuilder, final OutputStyle outputStyle,
            CollectionOutputElement nodeOutputElement) {

        stringBuilder.append(outputStyle.openingCollectionType());

        // Prepend type
        boolean shouldWriteSeparator = TypeProviderFunction.getInstance().apply(stringBuilder, outputStyle, nodeOutputElement);

        // create style for children
        OutputStyle childrenOutputStyle = outputStyle.getChildConfiguration();

        boolean isFirst = true;
        for (OutputElement child : nodeOutputElement.getOutputElements()) {

            if (!shouldWriteSeparator) {
                shouldWriteSeparator = true;
            }
            else {
                stringBuilder.append(outputStyle.collectionTypeElementSeparator());
            }

            outputWriterInstance.produceOutputRecursively(stringBuilder, childrenOutputStyle, child);

        }

        stringBuilder.append(outputStyle.closingCollectionType());

    }

}

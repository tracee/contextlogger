package io.tracee.contextlogger.output.internal.writer.collection;

import io.tracee.contextlogger.output.internal.outputelements.CollectionOutputElement;
import io.tracee.contextlogger.output.internal.outputelements.OutputElement;
import io.tracee.contextlogger.output.internal.writer.api.OutputWriter;
import io.tracee.contextlogger.output.internal.writer.api.CollectionOutputElementWriter;
import io.tracee.contextlogger.output.internal.writer.api.OutputStyle;

/**
 * Simple collection output element writer implementation.
 */
public class SimpleCollectionOutputElementWriter implements CollectionOutputElementWriter {

    @Override
    public void produceOutput(final OutputWriter outputWriterInstance, final StringBuilder stringBuilder, final OutputStyle outputStyle,
            CollectionOutputElement nodeOutputElement) {

        stringBuilder.append(outputStyle.openingCollectionType());

        // create style for children
        OutputStyle childrenOutputStyle = outputStyle.getChildConfiguration();

        boolean isFirst = true;
        for (OutputElement child : nodeOutputElement.getOutputElements()) {

            if (!isFirst) {
                stringBuilder.append(outputStyle.collectionTypeElementSeparator());
            }
            else {
                isFirst = false;
            }

            outputWriterInstance.produceOutputRecursively(stringBuilder, childrenOutputStyle, child);

        }

        stringBuilder.append(outputStyle.closingCollectionType());

    }

}

package io.tracee.contextlogger.outputgenerator.writer.type;

import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;
import io.tracee.contextlogger.outputgenerator.writer.api.NodeOutputElementWriter;
import io.tracee.contextlogger.outputgenerator.writer.api.OutputWriter;
import io.tracee.contextlogger.outputgenerator.writer.api.OutputStyle;

/**
 * Writer for outputting type and identity hashcode of output element.
 */
public class TypeForComplexAndCollectionWriter implements NodeOutputElementWriter<OutputElement> {

    private static final TypeForComplexAndCollectionWriter instance = new TypeForComplexAndCollectionWriter();

    public static TypeForComplexAndCollectionWriter getInstance() {
        return instance;
    }

    @Override
    public void produceOutput(final OutputWriter outputWriterInstance, final StringBuilder stringBuilder, final OutputStyle outputStyle,
            final OutputElement nodeOutputElement) {

    }

}

package io.tracee.contextlogger.outputgenerator.writer.complex;

import java.util.Map;

import io.tracee.contextlogger.outputgenerator.outputelements.ComplexOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;
import io.tracee.contextlogger.outputgenerator.writer.api.ComplexOutputElementWriter;
import io.tracee.contextlogger.outputgenerator.writer.api.OutputStyle;
import io.tracee.contextlogger.outputgenerator.writer.api.OutputWriter;
import io.tracee.contextlogger.outputgenerator.writer.function.TypeProviderFunction;

/**
 * Simple complex output element writer implementation.
 */
public class SimpleComplexOutputElementWriter implements ComplexOutputElementWriter {

    @Override
    public void produceOutput(final OutputWriter outputWriterInstance, final StringBuilder stringBuilder, final OutputStyle outputStyle,
            final ComplexOutputElement nodeOutputElement) {
        stringBuilder.append(outputStyle.openingComplexType());

        // Prepend type
        boolean shouldWriteSeparator = TypeProviderFunction.getInstance().apply(stringBuilder, outputStyle, nodeOutputElement);

        // create style for children
        OutputStyle childrenOutputStyle = outputStyle.getChildConfiguration();

        for (Map.Entry<String, OutputElement> child : nodeOutputElement.getOutputElements().entrySet()) {

            if (!shouldWriteSeparator) {
                shouldWriteSeparator = true;
            }
            else {
                stringBuilder.append(outputStyle.complexTypeElementSeparator());
            }

            stringBuilder.append(outputStyle.complexTypeOpeningName());
            stringBuilder.append(outputStyle.escapeString(child.getKey()));
            stringBuilder.append(outputStyle.complexTypeClosingName());

            stringBuilder.append(outputStyle.complexTypeNameValueSeparator());

            outputWriterInstance.produceOutputRecursively(stringBuilder, childrenOutputStyle, child.getValue());

        }

        stringBuilder.append(outputStyle.closingComplexType());
    }
}

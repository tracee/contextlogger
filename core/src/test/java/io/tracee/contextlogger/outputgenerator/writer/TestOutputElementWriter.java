package io.tracee.contextlogger.outputgenerator.writer;

import java.util.ArrayList;
import java.util.List;

import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;
import io.tracee.contextlogger.outputgenerator.writer.api.OutputStyle;
import io.tracee.contextlogger.outputgenerator.writer.api.OutputWriter;

/**
 * Output element writer used by tests to limit tests scopes for all output element writers.
 */
public class TestOutputElementWriter implements OutputWriter {

    private List<OutputElement> outputElementList = new ArrayList<OutputElement>();

    @Override
    public void init(final OutputWriterConfiguration outputWriterConfiguration) {

    }

    @Override
    public void produceOutputRecursively(final StringBuilder stringBuilder, final OutputStyle outputStyle, final OutputElement outputElement) {
        outputElementList.add(outputElement);
        stringBuilder.append(outputElement.getEncapsulatedInstance().toString());
    }

    public List<OutputElement> getOutputElementList() {
        return outputElementList;
    }
}

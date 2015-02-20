package io.tracee.contextlogger.output.internal.writer.circular;

import io.tracee.contextlogger.output.internal.outputelements.OutputElement;

/**
 * Simple implementation to output a circular dependency.
 */
public class SimpleCircularReferenceOutputElementWriter implements CircularReferenceOutputElementWriter {

    @Override
    public String produceOutput(final OutputElement outputElement) {
        if (outputElement != null && outputElement.getOutputElementsBaseType() != null) {
            String type = outputElement.getOutputElementsBaseType().getSimpleName();
            String instanceId = "";

            if (outputElement.getEncapsulatedInstance() != null) {
                instanceId = "@" + System.identityHashCode(outputElement.getEncapsulatedInstance());
            }

            return "see " + type + instanceId;
        }

        return "<null>";
    }
}

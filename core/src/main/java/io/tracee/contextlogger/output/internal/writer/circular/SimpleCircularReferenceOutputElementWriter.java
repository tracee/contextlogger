package io.tracee.contextlogger.output.internal.writer.circular;

import io.tracee.contextlogger.output.internal.CircularReferenceOutputElement;

/**
 * Simple implementation to output a circular dependency.
 */
public class SimpleCircularReferenceOutputElementWriter implements CircularReferenceOutputElementWriter {

    @Override
    public String produceOutput(final CircularReferenceOutputElement circularReferenceOutputElement) {
        if (circularReferenceOutputElement != null && circularReferenceOutputElement.getOutputElementsBaseType() != null) {
            String type = circularReferenceOutputElement.getOutputElementsBaseType().getSimpleName();
            String instanceId = "";

            if (circularReferenceOutputElement.getInstance() != null) {
                instanceId = "@" + System.identityHashCode(circularReferenceOutputElement.getInstance());
            }

            return "see " + type + instanceId;
        }

        return "<null>";
    }
}

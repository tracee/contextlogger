package io.tracee.contextlogger.outputgenerator.writer.alreadyprocessed;

import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;
import io.tracee.contextlogger.outputgenerator.writer.api.AlreadyProcessedReferenceOutputElementWriter;

/**
 * Simple implementation to output a alreadyprocessed dependency.
 */
public class SimpleAlreadyProcessedReferenceOutputElementWriter implements AlreadyProcessedReferenceOutputElementWriter {

    @Override
    public String produceOutput(final OutputElement outputElement) {
        if (outputElement != null && outputElement.getOutputElementsBaseType() != null) {
            String type = outputElement.getOutputElementsBaseType().getSimpleName();
            String instanceId = "";

            if (outputElement.getEncapsulatedInstance() != null) {
                instanceId = "@" + outputElement.getIdentityHashCode();
            }

            return "==> " + type + instanceId;
        }

        return "<NULL>";
    }
}

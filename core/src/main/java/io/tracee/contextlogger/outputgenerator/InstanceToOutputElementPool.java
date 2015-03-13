package io.tracee.contextlogger.outputgenerator;

import java.util.HashMap;
import java.util.Map;

import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;

/**
 * Class to handle deserialization state of instances. This is used to prevent cycles.
 */
public class InstanceToOutputElementPool {

    class ProxyOutput {

    }

    private Map<Integer, OutputElement> instanceIdToOutputElementMap = new HashMap<Integer, OutputElement>();

    public void add(OutputElement outputElement) {

        if (outputElement.getEncapsulatedInstance() != null) {
            instanceIdToOutputElementMap.put(outputElement.getIdentityHashCode(), outputElement);
        }

    }

    public boolean isInstanceMarkedAsProcessed(OutputElement outputElement) {

        if (outputElement != null && outputElement.getEncapsulatedInstance() != null) {
            return instanceIdToOutputElementMap.containsKey(outputElement.getIdentityHashCode());
        }

        return false;
    }

    public OutputElement getOutputElement(OutputElement outputElement) {

        if (outputElement != null && outputElement.getEncapsulatedInstance() != null) {
            return instanceIdToOutputElementMap.get(outputElement.getIdentityHashCode());
        }
        return null;
    }
}

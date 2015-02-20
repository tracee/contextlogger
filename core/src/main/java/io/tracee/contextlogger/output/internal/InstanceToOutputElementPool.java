package io.tracee.contextlogger.output.internal;

import io.tracee.contextlogger.output.internal.outputelements.OutputElement;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class to handle deserialization state of instances. This is used to prevent cycles.
 */
public class InstanceToOutputElementPool {

	class ProxyOutput{


	}

    private Set<Integer> instanceIdSet = new HashSet<Integer>();
    private Map<Integer, OutputElement> instanceIdToOutputElementMap = new HashMap<Integer, OutputElement>();

    public void markInstanceAsProcessed(Object instance) {
        if (instance != null) {
            instanceIdSet.add(System.identityHashCode(instance));
        }
    }

    public void add(Object instance, OutputElement outputElement) {
        if (instance != null) {
            instanceIdSet.add(System.identityHashCode(instance));
            instanceIdToOutputElementMap.put(System.identityHashCode(instance), outputElement);
        }
    }

    public boolean isInstanceMarkedAsProcessed(Object instance) {
        if (instance != null) {
            return instanceIdSet.contains(System.identityHashCode(instance)) || instanceIdToOutputElementMap.containsKey(System.identityHashCode(instance));
        }

        return false;
    }

    public OutputElement getOutputElement(Object instance) {

        if (instance != null) {
            return instanceIdToOutputElementMap.get(System.identityHashCode(instance));
        }
        return null;
    }

}

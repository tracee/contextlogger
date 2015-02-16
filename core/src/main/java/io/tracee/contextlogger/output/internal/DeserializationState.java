package io.tracee.contextlogger.output.internal;

import java.util.HashSet;
import java.util.Set;

/**
 * Class to handle deserialization state of instances. This is used to prevent cycles.
 */
public class DeserializationState {

    private Set<Integer> instanceIdSet = new HashSet<Integer>();

    public void add(Object instance) {
        if (instance != null) {
            instanceIdSet.add(System.identityHashCode(instance));
        }
    }

    public boolean instanceAlreadyExists(Object instance) {
        if (instance != null) {
            return instanceIdSet.contains(System.identityHashCode(instance));
        }

        return false;
    }

}

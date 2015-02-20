package io.tracee.contextlogger.output.internal.writer;

import java.util.HashSet;
import java.util.Set;

import io.tracee.contextlogger.output.internal.outputelements.OutputElement;

/**
 * Pool for checking if instance was already processed.
 */
public class OutputElementPool {

    private Set<Integer> instanceSet = new HashSet<Integer>();

    public void add(final OutputElement outputElement) {
        if (outputElement != null) {
            instanceSet.add(outputElement.getIdentityHashCode());
        }
    }

    public boolean isInstanceInPool(final OutputElement outputElement) {
        if (outputElement != null) {
            return instanceSet.contains(outputElement.getIdentityHashCode());
        }

        return false;
    }

}

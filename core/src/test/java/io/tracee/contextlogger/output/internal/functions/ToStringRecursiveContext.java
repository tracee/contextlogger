package io.tracee.contextlogger.output.internal.functions;

import io.tracee.contextlogger.output.internal.RecursiveContextDeserializer;
import io.tracee.contextlogger.output.internal.outputelements.AtomicOutputElement;
import io.tracee.contextlogger.output.internal.outputelements.OutputElement;

/**
 * Simple recursive context deserializer for testing purposes.
 * Create an {@link io.tracee.contextlogger.output.internal.outputelements.AtomicOutputElement} by calling toString on passed instance.
 */
public class ToStringRecursiveContext implements RecursiveContextDeserializer {

    @Override
    public OutputElement convertInstanceRecursively(final Object instanceToDeserialize) {
        if (instanceToDeserialize == null) {
            return new AtomicOutputElement(Void.class, null);
        }

        return new AtomicOutputElement(instanceToDeserialize.getClass(), instanceToDeserialize.toString());
    }

    @Override
    public void registerOutputElement(final OutputElement outputElement) {

    }

    @Override
    public boolean checkIfInstanceIsAlreadyRegistered(final Object instance) {
        return false;
    }

    @Override
    public OutputElement getRegisteredOutputElement(final Object instance) {
        return null;
    }
}

package io.tracee.contextlogger.output.internal.functions;

import io.tracee.contextlogger.output.internal.AtomicOutputElement;
import io.tracee.contextlogger.output.internal.OutputElement;
import io.tracee.contextlogger.output.internal.RecursiveContextDeserializer;

/**
 * Simple recursive context deserializer for testing purposes.
 * Create an {@link io.tracee.contextlogger.output.internal.AtomicOutputElement} by calling toString on passed instance.
 */
public class ToStringRecursiveContext implements RecursiveContextDeserializer {

    @Override
    public OutputElement convertInstanceRecursively(final Object instanceToDeserialize) {
        if (instanceToDeserialize == null) {
            return new AtomicOutputElement(Void.class, null);
        }

        return new AtomicOutputElement(instanceToDeserialize.getClass(), instanceToDeserialize.toString());
    }
}

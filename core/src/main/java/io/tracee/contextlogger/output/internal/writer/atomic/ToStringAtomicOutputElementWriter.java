package io.tracee.contextlogger.output.internal.writer.atomic;

import io.tracee.contextlogger.output.internal.outputelements.AtomicOutputElement;

/**
 * Atomic output element writer that calls toString on encapsulated value instance.
 */
public class ToStringAtomicOutputElementWriter implements AtomicOutputElementWriter {

    @Override
    public String produceOutput(final AtomicOutputElement atomicOutputElement) {
        return atomicOutputElement != null && atomicOutputElement.getEncapsulatedInstance() != null ? atomicOutputElement.getEncapsulatedInstance()
                .toString() : "<NULL>";
    }
}

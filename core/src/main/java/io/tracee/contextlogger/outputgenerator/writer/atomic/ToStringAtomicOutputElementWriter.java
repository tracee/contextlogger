package io.tracee.contextlogger.outputgenerator.writer.atomic;

import io.tracee.contextlogger.outputgenerator.outputelements.AtomicOutputElement;
import io.tracee.contextlogger.outputgenerator.writer.api.AtomicOutputElementWriter;

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

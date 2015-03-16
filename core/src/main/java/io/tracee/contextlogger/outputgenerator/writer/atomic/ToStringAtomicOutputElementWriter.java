package io.tracee.contextlogger.outputgenerator.writer.atomic;

import io.tracee.contextlogger.outputgenerator.outputelements.AtomicOutputElement;
import io.tracee.contextlogger.outputgenerator.predicates.IsOverwritingToStringPredicate;
import io.tracee.contextlogger.outputgenerator.writer.api.AtomicOutputElementWriter;

/**
 * Atomic output element writer that calls toString on encapsulated value instance.
 */
public class ToStringAtomicOutputElementWriter implements AtomicOutputElementWriter {

    @Override
    public String produceOutput(final AtomicOutputElement atomicOutputElement) {

        if (atomicOutputElement != null && atomicOutputElement.getEncapsulatedInstance() != null) {
            if (IsOverwritingToStringPredicate.getInstance().apply(atomicOutputElement.getEncapsulatedInstance())) {
                return atomicOutputElement.getEncapsulatedInstance().toString();
            }
            else {
                return "";
            }
        }
        return "<NULL>";

    }
}

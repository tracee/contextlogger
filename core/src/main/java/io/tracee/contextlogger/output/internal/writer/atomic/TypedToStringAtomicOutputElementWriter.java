package io.tracee.contextlogger.output.internal.writer.atomic;

import io.tracee.contextlogger.output.internal.AtomicOutputElement;

/**
 * Produces String representation for atomic value.
 * The produces output looks like: classname['toString value']
 */
public class TypedToStringAtomicOutputElementWriter extends ToStringAtomicOutputElementWriter {

    @Override
    public String produceOutput(final AtomicOutputElement atomicOutputElement) {

        String type = "";

        if (atomicOutputElement != null && atomicOutputElement.getOutputElementsBaseType() != null) {
            type = atomicOutputElement.getOutputElementsBaseType().getSimpleName();
            return type + "['" + super.produceOutput(atomicOutputElement) + "']";
        }

        return super.produceOutput(atomicOutputElement);
    }
}

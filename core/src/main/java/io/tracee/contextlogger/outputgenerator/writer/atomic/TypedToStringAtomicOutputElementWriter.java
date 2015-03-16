package io.tracee.contextlogger.outputgenerator.writer.atomic;

import io.tracee.contextlogger.outputgenerator.outputelements.AtomicOutputElement;

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

            String toStringValue = super.produceOutput(atomicOutputElement);

            return type + (!toStringValue.isEmpty() ? ("['" + toStringValue + "']") : "");
        }

        return super.produceOutput(atomicOutputElement);
    }
}

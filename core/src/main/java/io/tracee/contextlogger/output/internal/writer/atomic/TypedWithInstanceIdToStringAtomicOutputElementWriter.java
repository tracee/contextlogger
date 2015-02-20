package io.tracee.contextlogger.output.internal.writer.atomic;

import io.tracee.contextlogger.output.internal.outputelements.AtomicOutputElement;

/**
 * Produces String representation for atomic value.
 * The produces output looks like: classname@instanceid['toString value']
 */
public class TypedWithInstanceIdToStringAtomicOutputElementWriter extends ToStringAtomicOutputElementWriter {

    @Override
    public String produceOutput(final AtomicOutputElement atomicOutputElement) {

        if (atomicOutputElement != null && atomicOutputElement.getOutputElementsBaseType() != null) {
            String type = atomicOutputElement.getOutputElementsBaseType().getSimpleName();
            String instanceId = "";

            if (atomicOutputElement.getEncapsulatedInstance() != null) {
                instanceId = "@" + System.identityHashCode(atomicOutputElement.getEncapsulatedInstance());
            }

            return type + instanceId + "['" + super.produceOutput(atomicOutputElement) + "']";
        }

        return super.produceOutput(atomicOutputElement);
    }
}

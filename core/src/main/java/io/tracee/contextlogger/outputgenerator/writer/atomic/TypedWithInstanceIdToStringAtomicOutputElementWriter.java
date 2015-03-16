package io.tracee.contextlogger.outputgenerator.writer.atomic;

import io.tracee.contextlogger.outputgenerator.outputelements.AtomicOutputElement;
import io.tracee.contextlogger.outputgenerator.predicates.IsOverwritingToStringPredicate;

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

            if (atomicOutputElement.getIsAsMarkedAsMultipleReferenced() && atomicOutputElement.getEncapsulatedInstance() != null) {
                instanceId = "@" + atomicOutputElement.getIdentityHashCode();
            }

            String toStringValue = "";
            if (IsOverwritingToStringPredicate.getInstance().apply(atomicOutputElement.getEncapsulatedInstance())) {
                String value = super.produceOutput(atomicOutputElement);
                toStringValue = !value.isEmpty() ? "'" + value + "'" : "";
            }

            return type + instanceId + "[" + toStringValue + "]";
        }

        return super.produceOutput(atomicOutputElement);
    }
}

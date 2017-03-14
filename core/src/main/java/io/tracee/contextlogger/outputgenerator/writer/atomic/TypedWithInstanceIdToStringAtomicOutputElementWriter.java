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

            Class baseType = atomicOutputElement.getOutputElementsBaseType();

            if (atomicOutputElement.isNonStringBasedAtomic()) {
                return handleNonStringBaseAtomicValue(atomicOutputElement);
            } else {


                String type = atomicOutputElement.getOutputElementsBaseType().getSimpleName();
                String instanceId = "";

                if (atomicOutputElement.getIsAsMarkedAsMultipleReferenced() && atomicOutputElement.getEncapsulatedInstance() != null && atomicOutputElement.useReferencesIfMarkedAsMultipleReferenced()) {
                    instanceId = "@" + atomicOutputElement.getIdentityHashCode();
                }

                String toStringValue = "";
                if (IsOverwritingToStringPredicate.getInstance().apply(atomicOutputElement.getEncapsulatedInstance())) {
                    String value = super.produceOutput(atomicOutputElement);
                    toStringValue = "'" + value + "'";
                }

                return type + instanceId + "<" + toStringValue + ">";
            }
        }

        return super.produceOutput(atomicOutputElement);
    }


    private String handleNonStringBaseAtomicValue(final AtomicOutputElement atomicOutputElement) {
        String type = atomicOutputElement.getOutputElementsBaseType().getSimpleName();
        String toStringValue = "";

        if (IsOverwritingToStringPredicate.getInstance().apply(atomicOutputElement.getEncapsulatedInstance())) {
            toStringValue = super.produceOutput(atomicOutputElement);
        }

        return type + "<" + toStringValue + ">";


    }


}

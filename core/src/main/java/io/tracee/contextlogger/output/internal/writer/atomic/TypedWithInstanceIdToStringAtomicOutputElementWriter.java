package io.tracee.contextlogger.output.internal.writer.atomic;

import io.tracee.contextlogger.output.internal.AtomicOutputElement;

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

			if (atomicOutputElement.getValue() != null) {
				instanceId = "@" + System.identityHashCode(atomicOutputElement.getValue());
			}

			return type + instanceId + "['" + super.produceOutput(atomicOutputElement) + "']";
		}

		return super.produceOutput(atomicOutputElement);
	}
}

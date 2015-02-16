package io.tracee.contextlogger.output.internal.writer.circular;

import io.tracee.contextlogger.output.internal.CircularReferenceOutputElement;

/**
 * Public interface for all writers of {@link io.tracee.contextlogger.output.internal.CircularReferenceOutputElement}s.
 */
public interface CircularReferenceOutputElementWriter {

    /**
     * Produces output for the passed CircularReferenceOutputElement
     *
     * @param circularReferenceOutputElement the CircularReferenceOutputElement to be processed
     * @return the String representation of the atomic output element
     */
    String produceOutput(CircularReferenceOutputElement circularReferenceOutputElement);
}

package io.tracee.contextlogger.output.internal.writer.atomic;

import io.tracee.contextlogger.output.internal.AtomicOutputElement;

/**
 * Public interface for all writers of {@link io.tracee.contextlogger.output.internal.AtomicOutputElement}s.
 */
public interface AtomicOutputElementWriter {

    /**
     * Produces output for the passed AtomicOutputElement
     *
     * @param atomicOutputElement the AtomicOutputElement to be processed
     * @return the String representation of the atomic output element
     */
    String produceOutput(AtomicOutputElement atomicOutputElement);

}

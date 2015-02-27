package io.tracee.contextlogger.output.internal.writer.api;

/**
 * Base interface for all leaf based output element writers
 */
public interface LeafOutputElementWriter<T> {

    /**
     * Produces output for the passed OutputElement
     *
     * @param outputElement the OutputElement to be processed
     * @return the String representation of the output element
     */
    String produceOutput(T outputElement);

}

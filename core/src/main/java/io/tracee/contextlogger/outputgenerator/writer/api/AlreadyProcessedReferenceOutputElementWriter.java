package io.tracee.contextlogger.outputgenerator.writer.api;

import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;

/**
 * Public interface for all writers of already plotted output elements.
 */
public interface AlreadyProcessedReferenceOutputElementWriter {

    /**
     * Produces output for the passed CircularReferenceOutputElement
     *
     * @param outputElement the output element reference to be processed
     * @return the String representation of the atomic output element
     */
    String produceOutput(OutputElement outputElement);
}

package io.tracee.contextlogger.outputgenerator.functions;

import io.tracee.contextlogger.outputgenerator.RecursiveOutputElementTreeBuilderState;
import io.tracee.contextlogger.outputgenerator.RecursiveOutputElementTreeBuilder;
import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;

/**
 * Base interface for transforming passed instance to Output Elements.
 */
public interface ToOutputElementTransformerFunction<C> {

    /**
     * Transforms passed instance to Output Element
     *
     * @param recursiveOutputElementTreeBuilder the recursiveContextDeserializer instance to be called.
     * @param state the state of the recusive output element tree creation
     * @param instance the instance to be processed
     * @return the output element or null if instance cannot bbe processed correctly
     */
    OutputElement apply(RecursiveOutputElementTreeBuilder recursiveOutputElementTreeBuilder, final RecursiveOutputElementTreeBuilderState state, C instance);

}

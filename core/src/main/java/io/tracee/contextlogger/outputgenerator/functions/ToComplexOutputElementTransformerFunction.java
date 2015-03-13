package io.tracee.contextlogger.outputgenerator.functions;

import io.tracee.contextlogger.outputgenerator.RecursiveOutputElementTreeBuilderState;
import io.tracee.contextlogger.outputgenerator.RecursiveOutputElementTreeBuilder;
import io.tracee.contextlogger.outputgenerator.outputelements.ComplexOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;

/**
 * Abstract base class for creating {@link io.tracee.contextlogger.outputgenerator.outputelements.ComplexOutputElement}.
 */
public abstract class ToComplexOutputElementTransformerFunction<T> implements ToOutputElementTransformerFunction<T> {

    protected void addChildToComplexOutputElement(final RecursiveOutputElementTreeBuilder recursiveOutputElementTreeBuilder,
            final RecursiveOutputElementTreeBuilderState state, final ComplexOutputElement complexOutputElement, final String name,
            final Object childInstance) {

        OutputElement childOutputElement = recursiveOutputElementTreeBuilder.convertInstanceRecursively(state.next(), childInstance);
        if (childOutputElement != null) {
            complexOutputElement.addOutputElement(name, childOutputElement);
        }

    }

}

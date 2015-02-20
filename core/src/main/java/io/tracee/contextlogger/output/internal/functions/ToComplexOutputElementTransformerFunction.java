package io.tracee.contextlogger.output.internal.functions;

import io.tracee.contextlogger.output.internal.RecursiveContextDeserializer;
import io.tracee.contextlogger.output.internal.outputelements.ComplexOutputElement;
import io.tracee.contextlogger.output.internal.outputelements.OutputElement;

/**
 * Abstract base class for creating {@link io.tracee.contextlogger.output.internal.outputelements.ComplexOutputElement}.
 */
public abstract class ToComplexOutputElementTransformerFunction<T> implements ToOutputElementTransformerFunction<T> {

    protected void addChildToComplexOutputElement(final RecursiveContextDeserializer recursiveContextDeserializer,
            final ComplexOutputElement complexOutputElement, final String name, final Object childInstance) {

        OutputElement childOutputElement = recursiveContextDeserializer.convertInstanceRecursively(childInstance);
        if (childOutputElement != null) {
            complexOutputElement.addOutputElement(name, childOutputElement);
        }

    }

}

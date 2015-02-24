package io.tracee.contextlogger.output.internal.functions;

import io.tracee.contextlogger.output.internal.RecursiveContextDeserializer;
import io.tracee.contextlogger.output.internal.outputelements.OutputElement;

/**
 * Base interface for transforming passed instance to Output Elements.
 */
public interface ToOutputElementTransformerFunction<C> {

    /**
     * Transforms passed instance to Output Element
     *
     * @param recursiveContextDeserializer the recursiveContextDeserializer instance to be called.
     * @param instance the instance to be processed
     * @return the output element or null if instance cannot bbe processed correctly
     */
    OutputElement apply(RecursiveContextDeserializer recursiveContextDeserializer, C instance);

}

package io.tracee.contextlogger.output.internal.functions;

import io.tracee.contextlogger.output.internal.OutputElement;
import io.tracee.contextlogger.output.internal.RecursiveContextDeserializer;

/**
 * Base interface for transforming passed instance to Output Elements.
 */
public interface ToOutputElementTransformerFunction<T extends OutputElement, C> {

    /**
     * Transforms passed instance to Output Element
     *
     * @param instance the instance to be processed
     * @return the output element or null if instance cannot bbe processed correctly
     */
    T apply(RecursiveContextDeserializer recursiveContextDeserializer, C instance);

}

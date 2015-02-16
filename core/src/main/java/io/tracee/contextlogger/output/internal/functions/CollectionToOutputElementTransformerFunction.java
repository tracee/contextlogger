package io.tracee.contextlogger.output.internal.functions;

import java.util.Collection;

import io.tracee.Tracee;
import io.tracee.TraceeLogger;
import io.tracee.contextlogger.output.internal.CollectionOutputElement;
import io.tracee.contextlogger.output.internal.OutputElement;
import io.tracee.contextlogger.output.internal.RecursiveContextDeserializer;

/**
 * Transformer function that transforms the passed collection to an {@link io.tracee.contextlogger.output.internal.CollectionOutputElement}.
 */
public class CollectionToOutputElementTransformerFunction implements ToOutputElementTransformerFunction<CollectionOutputElement, Collection> {

    private static final CollectionToOutputElementTransformerFunction instance = new CollectionToOutputElementTransformerFunction();

    private static final TraceeLogger logger = Tracee.getBackend().getLoggerFactory().getLogger(CollectionToOutputElementTransformerFunction.class);

    public static CollectionToOutputElementTransformerFunction getInstance() {
        return instance;
    }

    @Override
    public CollectionOutputElement apply(final RecursiveContextDeserializer recursiveContextDeserializer, final Collection collection) {

        if (collection == null) {
            return null;
        }

        CollectionOutputElement outputElement = new CollectionOutputElement(collection.getClass());

        for (Object element : collection) {
            OutputElement childOutputElement = recursiveContextDeserializer.convertInstanceRecursively(element);
            if (childOutputElement != null) {
                outputElement.addElement(childOutputElement);
            }
        }

        return outputElement;
    }

}

package io.tracee.contextlogger.output.internal.functions;

import java.util.Collection;

import io.tracee.Tracee;
import io.tracee.TraceeLogger;
import io.tracee.contextlogger.output.internal.RecursiveContextDeserializer;
import io.tracee.contextlogger.output.internal.outputelements.CollectionOutputElement;
import io.tracee.contextlogger.output.internal.outputelements.NullValueOutputElement;
import io.tracee.contextlogger.output.internal.outputelements.OutputElement;

/**
 * Transformer function that transforms the passed collection to an {@link io.tracee.contextlogger.output.internal.outputelements.CollectionOutputElement}.
 */
public class CollectionToOutputElementTransformerFunction implements ToOutputElementTransformerFunction<Collection> {

    private static final CollectionToOutputElementTransformerFunction instance = new CollectionToOutputElementTransformerFunction();

    private static final TraceeLogger logger = Tracee.getBackend().getLoggerFactory().getLogger(CollectionToOutputElementTransformerFunction.class);

    public static CollectionToOutputElementTransformerFunction getInstance() {
        return instance;
    }

    @Override
    public OutputElement apply(final RecursiveContextDeserializer recursiveContextDeserializer, final Collection collection) {

        if (collection == null) {
            return new NullValueOutputElement();
        }
        else if (recursiveContextDeserializer.checkIfInstanceIsAlreadyRegistered(collection)) {
            return recursiveContextDeserializer.getRegisteredOutputElement(collection);
        }

        CollectionOutputElement outputElement = new CollectionOutputElement(collection.getClass(), collection);

        // must register output element before processing children to prevent problems with circular references
        recursiveContextDeserializer.registerOutputElement(outputElement);

        for (Object element : collection) {
            OutputElement childOutputElement = recursiveContextDeserializer.convertInstanceRecursively(element);
            if (childOutputElement != null) {
                outputElement.addElement(childOutputElement);
            }
        }

        return outputElement;
    }

}

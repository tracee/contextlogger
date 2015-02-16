package io.tracee.contextlogger.output.internal.functions;

import io.tracee.Tracee;
import io.tracee.TraceeLogger;
import io.tracee.contextlogger.output.internal.CollectionOutputElement;
import io.tracee.contextlogger.output.internal.OutputElement;
import io.tracee.contextlogger.output.internal.RecursiveContextDeserializer;

/**
 * Transformer function that transforms the passed array to an {@link CollectionOutputElement}.
 */
public class ArrayToOutputElementTransformerFunction implements ToOutputElementTransformerFunction<CollectionOutputElement, Object[]> {

    private static final ArrayToOutputElementTransformerFunction instance = new ArrayToOutputElementTransformerFunction();

    private static final TraceeLogger logger = Tracee.getBackend().getLoggerFactory().getLogger(ArrayToOutputElementTransformerFunction.class);

    public static ArrayToOutputElementTransformerFunction getInstance() {
        return instance;
    }

    @Override
    public CollectionOutputElement apply(final RecursiveContextDeserializer recursiveContextDeserializer, final Object[] array) {

        if (array == null) {
            return null;
        }

        CollectionOutputElement outputElement = new CollectionOutputElement(array.getClass());

        for (Object element : array) {
            OutputElement childOutputElement = recursiveContextDeserializer.convertInstanceRecursively(element);
            if (childOutputElement != null) {
                outputElement.addElement(childOutputElement);
            }
        }

        return outputElement;
    }
}

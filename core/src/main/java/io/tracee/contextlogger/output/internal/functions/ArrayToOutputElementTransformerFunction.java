package io.tracee.contextlogger.output.internal.functions;

import io.tracee.Tracee;
import io.tracee.TraceeLogger;
import io.tracee.contextlogger.output.internal.RecursiveContextDeserializer;
import io.tracee.contextlogger.output.internal.outputelements.CollectionOutputElement;
import io.tracee.contextlogger.output.internal.outputelements.OutputElement;

/**
 * Transformer function that transforms the passed array to an {@link CollectionOutputElement}.
 */
public class ArrayToOutputElementTransformerFunction implements ToOutputElementTransformerFunction<Object[]> {

    private static final ArrayToOutputElementTransformerFunction instance = new ArrayToOutputElementTransformerFunction();

    private static final TraceeLogger logger = Tracee.getBackend().getLoggerFactory().getLogger(ArrayToOutputElementTransformerFunction.class);

    public static ArrayToOutputElementTransformerFunction getInstance() {
        return instance;
    }

    @Override
    public OutputElement apply(final RecursiveContextDeserializer recursiveContextDeserializer, final Object[] array) {

        if (array == null) {
            return null;
        }
        else if (recursiveContextDeserializer.checkIfInstanceIsAlreadyRegistered(array)) {
            return recursiveContextDeserializer.getRegisteredOutputElement(instance);
        }

        CollectionOutputElement outputElement = new CollectionOutputElement(array.getClass(), array);

        // must register output element before processing children to prevent problems with circular references
        recursiveContextDeserializer.registerOutputElement(outputElement);

        for (Object element : array) {
            OutputElement childOutputElement = recursiveContextDeserializer.convertInstanceRecursively(element);
            if (childOutputElement != null) {
                outputElement.addElement(childOutputElement);
            }
        }

        return outputElement;
    }
}

package io.tracee.contextlogger.output.internal.functions;

import io.tracee.Tracee;
import io.tracee.TraceeLogger;
import io.tracee.contextlogger.output.internal.RecursiveOutputElementTreeBuilder;
import io.tracee.contextlogger.output.internal.outputelements.CollectionOutputElement;
import io.tracee.contextlogger.output.internal.outputelements.NullValueOutputElement;
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
    public OutputElement apply(final RecursiveOutputElementTreeBuilder recursiveOutputElementTreeBuilder, final Object[] array) {

        if (array == null) {
            // this shouldn't occur but should be caught
            return NullValueOutputElement.INSTANCE;
        }

        CollectionOutputElement outputElement = new CollectionOutputElement(array.getClass(), array);

        if (recursiveOutputElementTreeBuilder.checkIfInstanceIsAlreadyRegistered(outputElement)) {
            return recursiveOutputElementTreeBuilder.getRegisteredOutputElement(outputElement);
        }

        // must register output element before processing children to prevent problems with alreadyprocessed references
        recursiveOutputElementTreeBuilder.registerOutputElement(outputElement);

        for (Object element : array) {
            OutputElement childOutputElement = recursiveOutputElementTreeBuilder.convertInstanceRecursively(element);
            if (childOutputElement != null) {
                outputElement.addElement(childOutputElement);
            }
        }

        return outputElement;
    }

    /**
     * Gets component type of an array.
     *
     * @param array the array to get the component type for
     * @return the component type of the array.
     */
    Class getTypeOfArray(final Object[] array) {
        return array.getClass().getComponentType();
    }
}
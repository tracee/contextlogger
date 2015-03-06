package io.tracee.contextlogger.output.internal.functions;

import java.util.Collection;

import io.tracee.Tracee;
import io.tracee.TraceeLogger;
import io.tracee.contextlogger.output.internal.RecursiveOutputElementTreeBuilderState;
import io.tracee.contextlogger.output.internal.RecursiveOutputElementTreeBuilder;
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
    public OutputElement apply(final RecursiveOutputElementTreeBuilder recursiveOutputElementTreeBuilder,
            final RecursiveOutputElementTreeBuilderState state, final Collection collection) {

        if (collection == null) {
            return NullValueOutputElement.INSTANCE;
        }

        CollectionOutputElement outputElement = new CollectionOutputElement(collection.getClass(), collection);

        if (recursiveOutputElementTreeBuilder.checkIfInstanceIsAlreadyRegistered(outputElement)) {
            return recursiveOutputElementTreeBuilder.getRegisteredOutputElement(outputElement);
        }

        // must register output element before processing children to prevent problems with alreadyprocessed references
        recursiveOutputElementTreeBuilder.registerOutputElement(outputElement);

        for (Object element : collection) {
            OutputElement childOutputElement = recursiveOutputElementTreeBuilder.convertInstanceRecursively(state.next(), element);
            if (childOutputElement != null) {
                outputElement.addElement(childOutputElement);
            }
        }

        return outputElement;
    }

}

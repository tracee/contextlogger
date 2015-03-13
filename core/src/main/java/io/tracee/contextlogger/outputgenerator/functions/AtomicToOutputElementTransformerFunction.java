package io.tracee.contextlogger.outputgenerator.functions;

import io.tracee.Tracee;
import io.tracee.TraceeLogger;
import io.tracee.contextlogger.outputgenerator.RecursiveOutputElementTreeBuilderState;
import io.tracee.contextlogger.outputgenerator.RecursiveOutputElementTreeBuilder;
import io.tracee.contextlogger.outputgenerator.outputelements.AtomicOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.NullValueOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;

/**
 * Transforms the passed instance to an {@link AtomicOutputElement}.
 */
public class AtomicToOutputElementTransformerFunction implements ToOutputElementTransformerFunction<Object> {

    private static final AtomicToOutputElementTransformerFunction instance = new AtomicToOutputElementTransformerFunction();

    private static final TraceeLogger logger = Tracee.getBackend().getLoggerFactory().getLogger(AtomicToOutputElementTransformerFunction.class);

    public static AtomicToOutputElementTransformerFunction getInstance() {
        return instance;
    }

    @Override
    public OutputElement apply(final RecursiveOutputElementTreeBuilder recursiveOutputElementTreeBuilder,
            final RecursiveOutputElementTreeBuilderState state, final Object instance) {

        if (instance == null) {
            return NullValueOutputElement.INSTANCE;
        }
        else {

            OutputElement outputElement = new AtomicOutputElement(instance.getClass(), instance);

            if (recursiveOutputElementTreeBuilder.checkIfInstanceIsAlreadyRegistered(outputElement)) {
                return recursiveOutputElementTreeBuilder.getRegisteredOutputElementAndMarkItAsMultipleRegistered(outputElement);
            }

            recursiveOutputElementTreeBuilder.registerOutputElement(outputElement);
            return outputElement;
        }
    }
}

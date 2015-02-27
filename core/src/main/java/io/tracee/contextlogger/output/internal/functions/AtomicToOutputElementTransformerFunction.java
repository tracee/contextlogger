package io.tracee.contextlogger.output.internal.functions;

import io.tracee.Tracee;
import io.tracee.TraceeLogger;
import io.tracee.contextlogger.output.internal.RecursiveOutputElementTreeBuilder;
import io.tracee.contextlogger.output.internal.outputelements.AtomicOutputElement;
import io.tracee.contextlogger.output.internal.outputelements.NullValueOutputElement;
import io.tracee.contextlogger.output.internal.outputelements.OutputElement;

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
    public OutputElement apply(final RecursiveOutputElementTreeBuilder recursiveOutputElementTreeBuilder, final Object instance) {

        if (instance == null) {
            return NullValueOutputElement.INSTANCE;
        }
        else {

            OutputElement outputElement = new AtomicOutputElement(instance.getClass(), instance);

            if (recursiveOutputElementTreeBuilder.checkIfInstanceIsAlreadyRegistered(outputElement)) {
                return recursiveOutputElementTreeBuilder.getRegisteredOutputElement(outputElement);
            }

            recursiveOutputElementTreeBuilder.registerOutputElement(outputElement);
            return outputElement;
        }
    }
}

package io.tracee.contextlogger.output.internal.functions;

import io.tracee.Tracee;
import io.tracee.TraceeLogger;
import io.tracee.contextlogger.output.internal.RecursiveContextDeserializer;
import io.tracee.contextlogger.output.internal.outputelements.AtomicOutputElement;
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
    public OutputElement apply(final RecursiveContextDeserializer recursiveContextDeserializer, final Object instance) {

        if (instance == null) {
            return new AtomicOutputElement(Void.class, null);
        }
        else {

            if (recursiveContextDeserializer.checkIfInstanceIsAlreadyRegistered(instance)) {
                return recursiveContextDeserializer.getRegisteredOutputElement(instance);
            }
            OutputElement outputElement = new AtomicOutputElement(instance.getClass(), instance);
            recursiveContextDeserializer.registerOutputElement(outputElement);
            return outputElement;
        }
    }
}

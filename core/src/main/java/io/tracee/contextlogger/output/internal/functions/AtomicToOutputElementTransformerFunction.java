package io.tracee.contextlogger.output.internal.functions;

import io.tracee.Tracee;
import io.tracee.TraceeLogger;
import io.tracee.contextlogger.output.internal.AtomicOutputElement;
import io.tracee.contextlogger.output.internal.RecursiveContextDeserializer;

/**
 * Transforms the passed instance to an {@link AtomicOutputElement}.
 */
public class AtomicToOutputElementTransformerFunction implements ToOutputElementTransformerFunction<AtomicOutputElement, Object> {

    private static final AtomicToOutputElementTransformerFunction instance = new AtomicToOutputElementTransformerFunction();

    private static final TraceeLogger logger = Tracee.getBackend().getLoggerFactory().getLogger(AtomicToOutputElementTransformerFunction.class);

    public static AtomicToOutputElementTransformerFunction getInstance() {
        return instance;
    }

    @Override
    public AtomicOutputElement apply(final RecursiveContextDeserializer recursiveContextDeserializer, final Object instance) {

        if (instance == null) {
            return new AtomicOutputElement(Void.class, null);
        }
        else {
            return new AtomicOutputElement(instance.getClass(), instance);
        }
    }
}

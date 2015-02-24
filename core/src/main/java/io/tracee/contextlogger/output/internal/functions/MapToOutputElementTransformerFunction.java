package io.tracee.contextlogger.output.internal.functions;

import java.util.Map;

import io.tracee.Tracee;
import io.tracee.TraceeLogger;
import io.tracee.contextlogger.output.internal.RecursiveContextDeserializer;
import io.tracee.contextlogger.output.internal.outputelements.ComplexOutputElement;
import io.tracee.contextlogger.output.internal.outputelements.NullValueOutputElement;
import io.tracee.contextlogger.output.internal.outputelements.OutputElement;

/**
 * Transforms the passed map instance to a {@link io.tracee.contextlogger.output.internal.outputelements.ComplexOutputElement}.
 */
public class MapToOutputElementTransformerFunction extends ToComplexOutputElementTransformerFunction<Map> {

    private static final MapToOutputElementTransformerFunction instance = new MapToOutputElementTransformerFunction();

    private static final TraceeLogger logger = Tracee.getBackend().getLoggerFactory().getLogger(MapToOutputElementTransformerFunction.class);

    public static MapToOutputElementTransformerFunction getInstance() {
        return instance;
    }

    @Override
    public OutputElement apply(final RecursiveContextDeserializer recursiveContextDeserializer, final Map map) {

        if (map == null) {
            return NullValueOutputElement.INSTANCE;
        }

        ComplexOutputElement complexOutputElement = new ComplexOutputElement(map.getClass(), map);

        if (recursiveContextDeserializer.checkIfInstanceIsAlreadyRegistered(complexOutputElement)) {
            return recursiveContextDeserializer.getRegisteredOutputElement(complexOutputElement);
        }

        // must register output element before processing children to prevent problems with circular references
        recursiveContextDeserializer.registerOutputElement(complexOutputElement);

        for (Object entryObj : map.entrySet()) {

            Map.Entry entry = (Map.Entry)entryObj;

            addChildToComplexOutputElement(recursiveContextDeserializer, complexOutputElement, entry.getKey().toString(), entry.getValue());

        }

        return complexOutputElement;
    }

}

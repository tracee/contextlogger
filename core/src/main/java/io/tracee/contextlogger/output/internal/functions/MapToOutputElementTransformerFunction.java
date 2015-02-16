package io.tracee.contextlogger.output.internal.functions;

import java.util.Map;

import io.tracee.Tracee;
import io.tracee.TraceeLogger;
import io.tracee.contextlogger.output.internal.ComplexOutputElement;
import io.tracee.contextlogger.output.internal.RecursiveContextDeserializer;

/**
 * Transforms the passed map instance to a {@link io.tracee.contextlogger.output.internal.ComplexOutputElement}.
 */
public class MapToOutputElementTransformerFunction extends ToComplexOutputElementTransformerFunction<Map> {

    private static final MapToOutputElementTransformerFunction instance = new MapToOutputElementTransformerFunction();

    private static final TraceeLogger logger = Tracee.getBackend().getLoggerFactory().getLogger(MapToOutputElementTransformerFunction.class);

    public static MapToOutputElementTransformerFunction getInstance() {
        return instance;
    }

    @Override
    public ComplexOutputElement apply(final RecursiveContextDeserializer recursiveContextDeserializer, final Map map) {

        if (map == null) {
            return null;
        }

        ComplexOutputElement complexOutputElement = new ComplexOutputElement(map.getClass());

        for (Object entryObj : map.entrySet()) {

            Map.Entry entry = (Map.Entry)entryObj;

            addChildToComplexOutputElement(recursiveContextDeserializer, complexOutputElement, entry.getKey().toString(), entry.getValue());

        }

        return complexOutputElement;
    }

}

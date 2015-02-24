package io.tracee.contextlogger.output.internal.functions;

import java.util.Collections;
import java.util.List;

import io.tracee.Tracee;
import io.tracee.TraceeLogger;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.utility.NameObjectValuePair;
import io.tracee.contextlogger.contextprovider.utility.NameStringValuePair;
import io.tracee.contextlogger.contextprovider.utility.NameValuePair;
import io.tracee.contextlogger.impl.gson.MethodAnnotationPair;
import io.tracee.contextlogger.impl.gson.MethodAnnotationPairComparator;
import io.tracee.contextlogger.output.internal.RecursiveContextDeserializer;
import io.tracee.contextlogger.output.internal.outputelements.NullValueOutputElement;
import io.tracee.contextlogger.output.internal.outputelements.OutputElement;
import io.tracee.contextlogger.output.internal.outputelements.TraceeContextProviderOutputElement;
import io.tracee.contextlogger.utility.ListUtilities;
import io.tracee.contextlogger.utility.TraceeContextLogAnnotationUtilities;

/**
 * Transforms a tracee context provider instance to a {@link io.tracee.contextlogger.output.internal.outputelements.CollectionOutputElement}.
 */
public class TraceeContextProviderToOutputElementTransformerFunction extends ToComplexOutputElementTransformerFunction<Object> {

    private static final TraceeContextProviderToOutputElementTransformerFunction instance = new TraceeContextProviderToOutputElementTransformerFunction();

    private static final TraceeLogger logger = Tracee.getBackend().getLoggerFactory()
            .getLogger(TraceeContextProviderToOutputElementTransformerFunction.class);

    public static TraceeContextProviderToOutputElementTransformerFunction getInstance() {
        return instance;
    }

    @Override
    public OutputElement apply(final RecursiveContextDeserializer recursiveContextDeserializer, final Object instance) {
        TraceeContextProviderOutputElement complexOutputElement = new TraceeContextProviderOutputElement(instance.getClass(), instance);

        TraceeContextProvider annotation = TraceeContextLogAnnotationUtilities.getAnnotationFromType(instance);
        if (annotation == null) {

            // should not happen - but will be ignored
            logger.debug("TRACEE-CONTEXTLOGGER-DESERIALIZER - Got non annotated class");
            return NullValueOutputElement.INSTANCE;

        }
        else {

            // get those annotated methods
            final List<MethodAnnotationPair> entriesToPrint = TraceeContextLogAnnotationUtilities.getAnnotatedMethodsOfInstance(instance);

            // sort those methods
            Collections.sort(entriesToPrint, new MethodAnnotationPairComparator());

            for (MethodAnnotationPair singleEntry : entriesToPrint) {

                // if (!singleEntry.shouldBeProcessed(profileSettings)) {
                // continue;
                // }

                try {

                    Object returnValue = singleEntry.getMethod().invoke(instance, null);

                    if (TraceeContextLogAnnotationUtilities.isFlatable(singleEntry.getMethod())
                            && (isNameStringValuePair(returnValue) || isNameObjectValuePair(returnValue))) {

                        // returnValue is single NameStringValuePair
                        final NameValuePair nameValuePair = (NameStringValuePair)returnValue;
                        addChildToComplexOutputElement(recursiveContextDeserializer, complexOutputElement, nameValuePair.getName(),
                                nameValuePair.getValue());

                    }
                    else if (TraceeContextLogAnnotationUtilities.isFlatable(singleEntry.getMethod())
                            && (ListUtilities.isListOfType(returnValue, NameStringValuePair.class) || ListUtilities.isListOfType(returnValue,
                                    NameObjectValuePair.class))) {

                        // returnValue is List of NameValuePairs
                        final List<NameValuePair> list = (List<NameValuePair>)returnValue;

                        for (NameValuePair nameValuePair : list) {
                            addChildToComplexOutputElement(recursiveContextDeserializer, complexOutputElement, nameValuePair.getName(),
                                    nameValuePair.getValue());
                        }

                    }
                    else {

                        addChildToComplexOutputElement(recursiveContextDeserializer, complexOutputElement, singleEntry.getAnnotation().displayName(),
                                returnValue);

                    }

                }
                catch (Exception e) {

                    // to be ignored
                    logger.debug("TRACEE-CONTEXTLOGGER-DESERIALIZER - Exception during serialization.", e);

                }

            }

        }

        return complexOutputElement;
    }

    /**
     * Checks if the passed instance is of type {@link io.tracee.contextlogger.contextprovider.utility.NameStringValuePair}.
     *
     * @param instance the instance to check
     * @return returns true if the instance is of type {@link io.tracee.contextlogger.contextprovider.utility.NameStringValuePair}, otherwise false
     */
    static boolean isNameStringValuePair(Object instance) {

        return instance != null && NameStringValuePair.class.isInstance(instance);

    }

    /**
     * Checks if the passed instance is of type {@link io.tracee.contextlogger.contextprovider.utility.NameStringValuePair}.
     *
     * @param instance the instance to check
     * @return returns true if the instance is of type {@link io.tracee.contextlogger.contextprovider.utility.NameStringValuePair}, otherwise false
     */
    static boolean isNameObjectValuePair(Object instance) {

        return instance != null && NameObjectValuePair.class.isInstance(instance);

    }

}

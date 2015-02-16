package io.tracee.contextlogger.output.internal.functions;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import io.tracee.Tracee;
import io.tracee.TraceeLogger;
import io.tracee.contextlogger.TraceeContextLoggerConstants;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.utility.NameObjectValuePair;
import io.tracee.contextlogger.contextprovider.utility.NameStringValuePair;
import io.tracee.contextlogger.impl.gson.MethodAnnotationPair;
import io.tracee.contextlogger.impl.gson.MethodAnnotationPairComparator;
import io.tracee.contextlogger.output.internal.ComplexOutputElement;
import io.tracee.contextlogger.output.internal.RecursiveContextDeserializer;
import io.tracee.contextlogger.utility.ListUtilities;
import io.tracee.contextlogger.utility.RecursiveReflectionToStringStyle;
import io.tracee.contextlogger.utility.TraceeContextLogAnnotationUtilities;

/**
 * Transforms a tracee context provider instance to a {@link io.tracee.contextlogger.output.internal.CollectionOutputElement}.
 */
public class TraceeContextProviderToOutputElementTransformerFunction extends ToComplexOutputElementTransformerFunction<Object> {

    private static final TraceeContextProviderToOutputElementTransformerFunction instance = new TraceeContextProviderToOutputElementTransformerFunction();

    private static final TraceeLogger logger = Tracee.getBackend().getLoggerFactory()
            .getLogger(TraceeContextProviderToOutputElementTransformerFunction.class);

    public static TraceeContextProviderToOutputElementTransformerFunction getInstance() {
        return instance;
    }

    @Override
    public ComplexOutputElement apply(final RecursiveContextDeserializer recursiveContextDeserializer, final Object instance) {
        ComplexOutputElement complexOutputElement = new ComplexOutputElement(instance.getClass());

        TraceeContextProvider annotation = TraceeContextLogAnnotationUtilities.getAnnotationFromType(instance);
        if (annotation == null) {

            // should not happen - but will be ignored
            logger.debug("TRACEE-CONTEXTLOGGER-DESERIALIZER - Got non annotated class");
            return null;

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

                    if (TraceeContextLogAnnotationUtilities.isFlatable(singleEntry.getMethod()) && (isNameStringValuePair(returnValue))) {

                        // returnValue is single NameStringValuePair
                        final NameStringValuePair nameStringValuePair = (NameStringValuePair)returnValue;
                        addChildToComplexOutputElement(recursiveContextDeserializer, complexOutputElement, nameStringValuePair.getName(),
                                nameStringValuePair.getValue());

                    }
                    if (TraceeContextLogAnnotationUtilities.isFlatable(singleEntry.getMethod()) && (isNameObjectValuePair(returnValue))) {

                        // returnValue is single NameObjectValuePair
                        final NameObjectValuePair nameObjectValuePair = (NameObjectValuePair)returnValue;

                        // ObjectValuePairs will be deserialized by ReflectionToStringBuilder
                        final Object value = getValueOfNameObjectValuePair(nameObjectValuePair);
                        addChildToComplexOutputElement(recursiveContextDeserializer, complexOutputElement, nameObjectValuePair.getName(), value);

                    }
                    else if (TraceeContextLogAnnotationUtilities.isFlatable(singleEntry.getMethod())
                            && ListUtilities.isListOfType(returnValue, NameStringValuePair.class)) {

                        // returnValue is List of NameValuePairs
                        final List<NameStringValuePair> list = (List<NameStringValuePair>)returnValue;

                        for (NameStringValuePair nameStringValuePair : list) {
                            addChildToComplexOutputElement(recursiveContextDeserializer, complexOutputElement, nameStringValuePair.getName(),
                                    nameStringValuePair.getValue());
                        }

                    }
                    else if (TraceeContextLogAnnotationUtilities.isFlatable(singleEntry.getMethod())
                            && ListUtilities.isListOfType(returnValue, NameObjectValuePair.class)) {

                        // returnValue is List of NameValuePairs
                        List<NameObjectValuePair> list = (List<NameObjectValuePair>)returnValue;

                        for (NameObjectValuePair nameObjectValuePair : list) {
                            // ObjectValuePairs will be deserialized by ReflectionToStringBuilder
                            final Object value = getValueOfNameObjectValuePair(nameObjectValuePair);
                            addChildToComplexOutputElement(recursiveContextDeserializer, complexOutputElement, nameObjectValuePair.getName(), value);
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

    /**
     * Checks whether the passed instance has to be ignore at the deserialization.
     *
     * @param instance the instance to check
     * @return true if passed instance is null or type of passed instance is in IGNORED_AT_DESERIALIZATION set.
     */
    static boolean shouldBeIgnoreAtDeSerialization(final Object instance) {
        return instance == null || TraceeContextLoggerConstants.IGNORED_AT_DESERIALIZATION.contains(instance.getClass());
    }

    /**
     * selects the best macthing serialization depending on value type.
     *
     * @param nameObjectValuePair
     * @return
     */
    private Object getValueOfNameObjectValuePair(final NameObjectValuePair nameObjectValuePair) {
        if (nameObjectValuePair != null && nameObjectValuePair.getValue() != null) {

            if (TraceeContextLogAnnotationUtilities.getAnnotationFromType(nameObjectValuePair.getValue()) != null) {
                return nameObjectValuePair.getValue();
            }
            else if (!shouldBeIgnoreAtDeSerialization(nameObjectValuePair.getValue())) {
                return ReflectionToStringBuilder.reflectionToString(nameObjectValuePair.getValue(), new RecursiveReflectionToStringStyle());
            }
            else {
                // not null value - but type is in IGNORED_AT_DESERIALIZATION set
                return nameObjectValuePair.getValue().toString();
            }

        }
        else {
            return null;
        }
    }

}

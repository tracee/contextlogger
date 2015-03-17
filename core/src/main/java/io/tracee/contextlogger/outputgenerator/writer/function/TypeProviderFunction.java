package io.tracee.contextlogger.outputgenerator.writer.function;

import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.OutputElementType;
import io.tracee.contextlogger.outputgenerator.writer.api.OutputStyle;
import io.tracee.contextlogger.utility.TraceeContextLogAnnotationUtilities;

/**
 * Function that provides a type definition. Used for complex and collection types.
 */
public class TypeProviderFunction {

    private static final String OUTPUT_STRING_TYPE = "TYPE";

    private static final TypeProviderFunction INSTANCE = new TypeProviderFunction();

    /**
     * Write the type string to the Stringbuilder
     *
     * @param stringBuilder the stringbuilder to write to
     * @param outputStyle the output style to use
     * @param outputElement the output element to write the type string for.
     * @return true, if any output was written to the stringbuilder, otherwise false.
     */
    public boolean apply(final StringBuilder stringBuilder, final OutputStyle outputStyle, final OutputElement outputElement) {

        boolean result = false;

        if (outputElement != null) {

            if (OutputElementType.COLLECTION.equals(outputElement.getOutputElementType())) {
                result = handleCollectionType(stringBuilder, outputStyle, outputElement);
            }
            else if (OutputElementType.COMPLEX.equals(outputElement.getOutputElementType())) {

                if (TraceeContextLogAnnotationUtilities.getAnnotationFromType(outputElement.getEncapsulatedInstance()) != null) {
                    result = handleTraceeContextprovider(stringBuilder, outputStyle, outputElement);
                }
                else {
                    result = handleComplexType(stringBuilder, outputStyle, outputElement);
                }

            }

        }

        return result;
    }

    boolean handleCollectionType(final StringBuilder stringBuilder, final OutputStyle outputStyle, final OutputElement outputElement) {

        String tmpTypeString = OUTPUT_STRING_TYPE + ":" + outputElement.getOutputElementsBaseType().getSimpleName();
        if (outputElement.getIsAsMarkedAsMultipleReferenced()) {
            tmpTypeString = tmpTypeString + "@" + outputElement.getIdentityHashCode();
        }

        stringBuilder.append(outputStyle.openingAtomicType());
        stringBuilder.append(outputStyle.escapeString(tmpTypeString));
        stringBuilder.append(outputStyle.closingAtomicType());

        return true;
    }

    boolean handleComplexType(final StringBuilder stringBuilder, final OutputStyle outputStyle, final OutputElement outputElement) {
        createComplexOutput(stringBuilder, outputStyle, outputElement, outputElement.getOutputElementsBaseType().getSimpleName());
        return true;
    }

    boolean handleTraceeContextprovider(final StringBuilder stringBuilder, final OutputStyle outputStyle, final OutputElement outputElement) {

        String typeString = outputElement.getOutputElementsBaseType().getSimpleName();

        TraceeContextProvider traceeContextProviderAnnotation = TraceeContextLogAnnotationUtilities.getAnnotationFromType(outputElement
                .getEncapsulatedInstance());
        if (traceeContextProviderAnnotation != null) {
            if (traceeContextProviderAnnotation.suppressTypeInOutput()) {
                return false;
            }
            typeString = traceeContextProviderAnnotation.displayName();
        }

        createComplexOutput(stringBuilder, outputStyle, outputElement, typeString);

        return true;
    }

    void createComplexOutput(final StringBuilder stringBuilder, final OutputStyle outputStyle, final OutputElement outputElement, final String typeString) {

        String tmpTypeString = typeString;
        if (outputElement.getIsAsMarkedAsMultipleReferenced()) {
            tmpTypeString = tmpTypeString + "@" + outputElement.getIdentityHashCode();
        }

        stringBuilder.append(outputStyle.complexTypeOpeningName());
        stringBuilder.append(outputStyle.escapeString(OUTPUT_STRING_TYPE));
        stringBuilder.append(outputStyle.complexTypeClosingName());
        stringBuilder.append(outputStyle.complexTypeNameValueSeparator());
        stringBuilder.append(outputStyle.openingAtomicType());
        stringBuilder.append(outputStyle.escapeString(tmpTypeString));
        stringBuilder.append(outputStyle.closingAtomicType());

    }

    public static TypeProviderFunction getInstance() {
        return INSTANCE;
    }

}

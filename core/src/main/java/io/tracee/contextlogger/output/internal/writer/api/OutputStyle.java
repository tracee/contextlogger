package io.tracee.contextlogger.output.internal.writer.api;

/**
 * Main interface for defining an output style.
 */
public interface OutputStyle {

    /**
     * Generates an immutable Style for the child instances.
     *
     * @return the child json output style instance
     */
    abstract OutputStyle getChildConfiguration();

    /**
     * All styles should offer an implementation for escaping values and names.
     *
     * @param stringToBeEscaped the string that has to be escaped
     * @return the escaped string
     */
    abstract String escapeString(final String stringToBeEscaped);

    String openingComplexType();

    String closingComplexType();

    String complexTypeOpeningName();

    String complexTypeClosingName();

    String complexTypeNameValueSeparator();

    String complexTypeElementSeparator();

    String openingCollectionType();

    String closingCollectionType();

    String collectionTypeElementSeparator();

    String openingAtomicType();

    String closingAtomicType();

}

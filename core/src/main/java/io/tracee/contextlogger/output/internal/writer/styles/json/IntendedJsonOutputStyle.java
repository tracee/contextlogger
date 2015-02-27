package io.tracee.contextlogger.output.internal.writer.styles.json;

import io.tracee.contextlogger.output.internal.writer.api.OutputStyle;

/**
 * Json output writer that supports indents.
 */
public class IntendedJsonOutputStyle extends JsonOutputStyle {

    private final int indent;
    private final String indentString;

    /**
     * Must have a no arg public constructor to enable creation via reflection.
     */
    public IntendedJsonOutputStyle() {
        super();
        indent = 1;
        indentString = createIndent(indent);
    }

    /**
     * Must have a no arg public constructor to enable creation via reflection.
     */
    public IntendedJsonOutputStyle(int indent) {
        super();
        this.indent = indent;
        indentString = createIndent(indent);
    }

    @Override
    public OutputStyle getChildConfiguration() {
        return new IntendedJsonOutputStyle(indent + 1);
    }

    public String openingComplexType() {
        return "{\n" + indentString;
    }

    public String closingComplexType() {
        return "\n" + createIndent(indent - 1) + "}";
    }

    public String complexTypeOpeningName() {
        return "\"";
    }

    public String complexTypeClosingName() {
        return "\"";
    }

    public String complexTypeNameValueSeparator() {
        return ":";
    }

    public String complexTypeElementSeparator() {
        return ",\n" + indentString;
    }

    public String openingCollectionType() {
        return "[\n" + indentString;
    }

    public String closingCollectionType() {
        return "\n" + createIndent(indent - 1) + "]";
    }

    public String collectionTypeElementSeparator() {
        return ",\n" + indentString;
    }

    public String openingAtomicType() {
        return "\"";
    }

    public String closingAtomicType() {
        return "\"";
    }

    private String createIndent(int indent) {

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < indent; i++) {
            stringBuilder.append("  ");
        }

        return stringBuilder.toString();

    }
}

package io.tracee.contextlogger.output.internal.writer.styles.json;

import io.tracee.contextlogger.output.internal.writer.api.OutputStyle;

/**
 * Created by TGI on 13.02.2015.
 */
public abstract class JsonOutputStyle implements OutputStyle {

    @Override
    public String escapeString(final String stringToBeEscaped) {
        if (stringToBeEscaped == null || stringToBeEscaped.length() == 0) {
            return "\"\"";
        }

        char c = 0;
        int i;
        int len = stringToBeEscaped.length();
        StringBuilder sb = new StringBuilder(len + 4);
        String t;

        for (i = 0; i < len; i += 1) {
            c = stringToBeEscaped.charAt(i);
            switch (c) {
                case '\\':
                case '"':
                    sb.append('\\');
                    sb.append(c);
                    break;
                case '/':
                    // if (b == '<') {
                    sb.append('\\');
                    // }
                    sb.append(c);
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                default:
                    if (c < ' ') {
                        t = "000" + Integer.toHexString(c);
                        sb.append("\\u" + t.substring(t.length() - 4));
                    }
                    else {
                        sb.append(c);
                    }
            }
        }
        return sb.toString();

    }

    public String openingComplexType() {
        return "{";
    }

    public String closingComplexType() {
        return "}";
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
        return ",";
    }

    public String openingCollectionType() {
        return "[";
    }

    public String closingCollectionType() {
        return "]";
    }

    public String collectionTypeElementSeparator() {
        return ",";
    }

    public String openingAtomicType() {
        return "\"";
    }

    public String closingAtomicType() {
        return "\"";
    }

}

package io.tracee.contextlogger.contextprovider.core.java.arrays;

import io.tracee.contextlogger.contextprovider.api.ProfileConfig;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderPrimitiveType;
import io.tracee.contextlogger.contextprovider.api.WrappedPrimitiveTypeContextData;

@SuppressWarnings("unused")
@TraceeContextProviderPrimitiveType
@ProfileConfig(basic = true, enhanced = true, full = true)
public class CharArrayContextProvider implements WrappedPrimitiveTypeContextData<char[]> {

    protected final static boolean OUTPUT_AS_CONCATENATED_STRING = true;

    private char[] array;


    @SuppressWarnings("unused")
    public CharArrayContextProvider() {
    }

    @SuppressWarnings("unused")
    public CharArrayContextProvider(final char[] array) {
        this.array = array;
    }

    @Override
    public void setContextData(Object instance) throws ClassCastException {
        this.array = (char[]) instance;
    }

    @Override
    public char[] getContextData() {
        return this.array;
    }

    public Class<char[]> getWrappedType() {
        return char[].class;
    }

    @SuppressWarnings("unused")
    public String getPrimitiveTypeValue() {

        if (array != null) {

            return OUTPUT_AS_CONCATENATED_STRING ? createOutputAsConcatenatedString() : createOutputAsArray();

        } else {
            return null;
        }

    }

    private String createOutputAsArray() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        boolean first = true;

        for (char element : array) {
            if (first) {
                first = false;
            } else {
                sb.append(ArrayContextProviderConstants.ELEMENT_SEPARATOR);
            }
            sb.append("'").append(element).append("'");
        }

        sb.append("]");

        return sb.toString();
    }

    private String createOutputAsConcatenatedString() {
        StringBuilder sb = new StringBuilder();

        for (char element : array) {
            sb.append(element);
        }

        return sb.toString();
    }


}

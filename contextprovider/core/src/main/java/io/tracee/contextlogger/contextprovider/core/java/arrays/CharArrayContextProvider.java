package io.tracee.contextlogger.contextprovider.core.java.arrays;

import io.tracee.contextlogger.contextprovider.api.ProfileConfig;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderMethod;
import io.tracee.contextlogger.contextprovider.api.WrappedContextData;

@SuppressWarnings("unused")
@TraceeContextProvider(displayName = "char[]")
@ProfileConfig(basic = true, enhanced = true, full = true)
public class CharArrayContextProvider implements WrappedContextData<char[]> {
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
    @TraceeContextProviderMethod(displayName = "value", order = 10)
    @ProfileConfig(basic = true, enhanced = true, full = true)
    public String getArrayStringRepresentation() {

        if (array != null) {

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
        } else {
            return null;
        }

    }


}

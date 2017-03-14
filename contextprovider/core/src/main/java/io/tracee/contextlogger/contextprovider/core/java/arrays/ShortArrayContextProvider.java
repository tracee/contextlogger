package io.tracee.contextlogger.contextprovider.core.java.arrays;

import io.tracee.contextlogger.contextprovider.api.ProfileConfig;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderPrimitiveType;
import io.tracee.contextlogger.contextprovider.api.WrappedPrimitiveTypeContextData;

@SuppressWarnings("unused")
@TraceeContextProviderPrimitiveType
@ProfileConfig(basic = true, enhanced = true, full = true)
public class ShortArrayContextProvider implements WrappedPrimitiveTypeContextData<short[]> {

    private short[] array;

    @SuppressWarnings("unused")
    public ShortArrayContextProvider() {
    }

    @SuppressWarnings("unused")
    public ShortArrayContextProvider(final short[] array) {
        this.array = array;
    }

    @Override
    public void setContextData(Object instance) throws ClassCastException {
        this.array = (short[]) instance;
    }

    @Override
    public short[] getContextData() {
        return this.array;
    }

    public Class<short[]> getWrappedType() {
        return short[].class;
    }

    @SuppressWarnings("unused")
    public String getPrimitiveTypeValue() {

        if (array != null) {

            StringBuilder sb = new StringBuilder();
            sb.append("[");

            boolean first = true;

            for (short element : array) {
                if (first) {
                    first = false;
                } else {
                    sb.append(ArrayContextProviderConstants.ELEMENT_SEPARATOR);
                }
                sb.append(element);
            }

            sb.append("]");

            return sb.toString();
        } else {
            return null;
        }

    }


}
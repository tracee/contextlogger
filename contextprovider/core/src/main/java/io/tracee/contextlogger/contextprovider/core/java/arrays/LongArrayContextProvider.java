package io.tracee.contextlogger.contextprovider.core.java.arrays;

import io.tracee.contextlogger.contextprovider.api.ProfileConfig;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderPrimitiveType;
import io.tracee.contextlogger.contextprovider.api.WrappedPrimitiveTypeContextData;


@SuppressWarnings("unused")
@TraceeContextProviderPrimitiveType
@ProfileConfig(basic = true, enhanced = true, full = true)
public class LongArrayContextProvider implements WrappedPrimitiveTypeContextData<long[]> {

    private long[] array;

    @SuppressWarnings("unused")
    public LongArrayContextProvider() {
    }

    @SuppressWarnings("unused")
    public LongArrayContextProvider(final long[] array) {
        this.array = array;
    }

    @Override
    public void setContextData(Object instance) throws ClassCastException {
        this.array = (long[]) instance;
    }

    @Override
    public long[] getContextData() {
        return this.array;
    }

    public Class<long[]> getWrappedType() {
        return long[].class;
    }

    @SuppressWarnings("unused")
    public String getPrimitiveTypeValue() {

        if (array != null) {

            StringBuilder sb = new StringBuilder();
            sb.append("[");

            boolean first = true;

            for (long element : array) {
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
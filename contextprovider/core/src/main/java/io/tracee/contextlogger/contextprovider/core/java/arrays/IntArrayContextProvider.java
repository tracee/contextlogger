package io.tracee.contextlogger.contextprovider.core.java.arrays;

import io.tracee.contextlogger.contextprovider.api.ProfileConfig;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderMethod;
import io.tracee.contextlogger.contextprovider.api.WrappedContextData;

@SuppressWarnings("unused")
@TraceeContextProvider(displayName = "int[]")
@ProfileConfig(basic = true, enhanced = true, full = true)
public class IntArrayContextProvider implements WrappedContextData<int[]> {
    private int[] array;


    @SuppressWarnings("unused")
    public IntArrayContextProvider() {
    }

    @SuppressWarnings("unused")
    public IntArrayContextProvider(final int[] array) {
        this.array = array;
    }

    @Override
    public void setContextData(Object instance) throws ClassCastException {
        this.array = (int[]) instance;
    }

    @Override
    public int[] getContextData() {
        return this.array;
    }

    public Class<int[]> getWrappedType() {
        return int[].class;
    }

    @SuppressWarnings("unused")
    @TraceeContextProviderMethod(displayName = "value", order = 10)
    @ProfileConfig(basic = true, enhanced = true, full = true)
    public String getArrayStringRepresentation() {

        if (array != null) {

            StringBuilder sb = new StringBuilder();
            sb.append("[");

            boolean first = true;

            for (int element : array) {
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
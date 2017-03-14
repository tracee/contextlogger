package io.tracee.contextlogger.contextprovider.core.java.arrays;

import io.tracee.contextlogger.contextprovider.api.ProfileConfig;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderPrimitiveType;
import io.tracee.contextlogger.contextprovider.api.WrappedPrimitiveTypeContextData;

@SuppressWarnings("unused")
@TraceeContextProviderPrimitiveType
@ProfileConfig(basic = true, enhanced = true, full = true)
public class BooleanArrayContextProvider implements WrappedPrimitiveTypeContextData<boolean[]> {
    private boolean[] array;


    @SuppressWarnings("unused")
    public BooleanArrayContextProvider() {
    }

    @SuppressWarnings("unused")
    public BooleanArrayContextProvider(final boolean[] booleanArray) {
        this.array = booleanArray;
    }

    @Override
    public void setContextData(Object instance) throws ClassCastException {
        this.array = (boolean[]) instance;
    }

    @Override
    public boolean[] getContextData() {
        return this.array;
    }

    public Class<boolean[]> getWrappedType() {
        return boolean[].class;
    }

    @SuppressWarnings("unused")
    public String getPrimitiveTypeValue() {

        if (array != null) {

            StringBuilder sb = new StringBuilder();
            sb.append("[");

            boolean first = true;

            for (boolean element : array) {
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

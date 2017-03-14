package io.tracee.contextlogger.contextprovider.core.java.arrays;

import io.tracee.contextlogger.contextprovider.api.ProfileConfig;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderPrimitiveType;
import io.tracee.contextlogger.contextprovider.api.WrappedPrimitiveTypeContextData;

@SuppressWarnings("unused")
@TraceeContextProviderPrimitiveType
@ProfileConfig(basic = true, enhanced = true, full = true)
public class FloatArrayContextProvider implements WrappedPrimitiveTypeContextData<float[]> {
    private float[] array;


    @SuppressWarnings("unused")
    public FloatArrayContextProvider() {
    }

    @SuppressWarnings("unused")
    public FloatArrayContextProvider(final float[] array) {
        this.array = array;
    }

    @Override
    public void setContextData(Object instance) throws ClassCastException {
        this.array = (float[]) instance;
    }

    @Override
    public float[] getContextData() {
        return this.array;
    }

    public Class<float[]> getWrappedType() {
        return float[].class;
    }

    @SuppressWarnings("unused")
    public String getPrimitiveTypeValue() {

        if (array != null) {

            StringBuilder sb = new StringBuilder();
            sb.append("[");

            boolean first = true;

            for (float element : array) {
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

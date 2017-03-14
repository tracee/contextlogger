package io.tracee.contextlogger.contextprovider.core.java.arrays;

import io.tracee.contextlogger.contextprovider.api.ProfileConfig;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderPrimitiveType;
import io.tracee.contextlogger.contextprovider.api.WrappedPrimitiveTypeContextData;


@SuppressWarnings("unused")
@TraceeContextProviderPrimitiveType
@ProfileConfig(basic = true, enhanced = true, full = true)
public class DoubleArrayContextProvider implements WrappedPrimitiveTypeContextData<double[]> {
    private double[] array;


    @SuppressWarnings("unused")
    public DoubleArrayContextProvider() {
    }

    @SuppressWarnings("unused")
    public DoubleArrayContextProvider(final double[] array) {
        this.array = array;
    }

    @Override
    public void setContextData(Object instance) throws ClassCastException {
        this.array = (double[]) instance;
    }

    @Override
    public double[] getContextData() {
        return this.array;
    }

    public Class<double[]> getWrappedType() {
        return double[].class;
    }

    @SuppressWarnings("unused")
    public String getPrimitiveTypeValue() {

        if (array != null) {

            StringBuilder sb = new StringBuilder();
            sb.append("[");

            boolean first = true;

            for (double element : array) {
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

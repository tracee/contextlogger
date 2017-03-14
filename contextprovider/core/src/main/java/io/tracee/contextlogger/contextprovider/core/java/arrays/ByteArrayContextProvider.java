package io.tracee.contextlogger.contextprovider.core.java.arrays;

import io.tracee.contextlogger.contextprovider.api.ProfileConfig;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderPrimitiveType;
import io.tracee.contextlogger.contextprovider.api.WrappedPrimitiveTypeContextData;

import javax.xml.bind.DatatypeConverter;

@SuppressWarnings("unused")
@TraceeContextProviderPrimitiveType
@ProfileConfig(basic = true, enhanced = true, full = true)
public class ByteArrayContextProvider implements WrappedPrimitiveTypeContextData<byte[]> {
    private byte[] array;


    @SuppressWarnings("unused")
    public ByteArrayContextProvider() {
    }

    @SuppressWarnings("unused")
    public ByteArrayContextProvider(final byte[] array) {
        this.array = array;
    }

    @Override
    public void setContextData(Object instance) throws ClassCastException {
        this.array = (byte[]) instance;
    }

    @Override
    public byte[] getContextData() {
        return this.array;
    }

    public Class<byte[]> getWrappedType() {
        return byte[].class;
    }

    @SuppressWarnings("unused")
    public String getPrimitiveTypeValue() {

        if (array != null) {
            return DatatypeConverter.printBase64Binary(array);
        } else {
            return null;
        }

    }


}

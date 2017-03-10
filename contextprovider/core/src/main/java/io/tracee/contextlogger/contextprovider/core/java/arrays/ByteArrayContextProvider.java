package io.tracee.contextlogger.contextprovider.core.java.arrays;

import io.tracee.contextlogger.contextprovider.api.ProfileConfig;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderMethod;
import io.tracee.contextlogger.contextprovider.api.WrappedContextData;

import java.util.Base64;

@SuppressWarnings("unused")
@TraceeContextProvider(displayName = "byte[]")
@ProfileConfig(basic = true, enhanced = true, full = true)
public class ByteArrayContextProvider implements WrappedContextData<byte[]> {
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
    @TraceeContextProviderMethod(displayName = "value", order = 10)
    @ProfileConfig(basic = true, enhanced = true, full = true)
    public String getArrayStringRepresentation() {

        if (array != null) {
            return Base64.getEncoder().encodeToString(array);
        } else {
            return null;
        }

    }


}

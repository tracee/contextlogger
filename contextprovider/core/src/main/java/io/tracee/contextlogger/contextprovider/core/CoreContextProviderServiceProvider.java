package io.tracee.contextlogger.contextprovider.core;

import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderServiceProvider;
import io.tracee.contextlogger.contextprovider.core.java.JavaThrowableContextProvider;
import io.tracee.contextlogger.contextprovider.core.java.arrays.BooleanArrayContextProvider;
import io.tracee.contextlogger.contextprovider.core.java.arrays.ByteArrayContextProvider;
import io.tracee.contextlogger.contextprovider.core.java.arrays.CharArrayContextProvider;
import io.tracee.contextlogger.contextprovider.core.java.arrays.DoubleArrayContextProvider;
import io.tracee.contextlogger.contextprovider.core.java.arrays.FloatArrayContextProvider;
import io.tracee.contextlogger.contextprovider.core.java.arrays.IntArrayContextProvider;
import io.tracee.contextlogger.contextprovider.core.java.arrays.LongArrayContextProvider;
import io.tracee.contextlogger.contextprovider.core.java.arrays.ShortArrayContextProvider;
import io.tracee.contextlogger.contextprovider.core.tracee.CommonDataContextProvider;
import io.tracee.contextlogger.contextprovider.core.tracee.TraceeMdcContextProvider;
import io.tracee.contextlogger.contextprovider.core.tracee.TraceeMessageContextProvider;
import io.tracee.contextlogger.contextprovider.core.utility.NameValuePair;

/**
 * Service provider for all core context providers.
 */
public class CoreContextProviderServiceProvider implements TraceeContextProviderServiceProvider {

    public static final Class[] IMPLICIT_CONTEXT_PROVIDER = {CommonDataContextProvider.class, TraceeMdcContextProvider.class};
    public static final Class[] CONTEXT_PROVIDER = {
            JavaThrowableContextProvider.class,
            NameValuePair.class,
            TraceeMessageContextProvider.class,
            CharArrayContextProvider.class,
            ByteArrayContextProvider.class,
            BooleanArrayContextProvider.class,
            ShortArrayContextProvider.class,
            IntArrayContextProvider.class,
            LongArrayContextProvider.class,
            FloatArrayContextProvider.class,
            DoubleArrayContextProvider.class

    };

    @Override
    public Class[] getImplicitContextProvider() {
        return IMPLICIT_CONTEXT_PROVIDER;
    }

    @Override
    public Class[] getContextProvider() {
        return CONTEXT_PROVIDER;
    }


}

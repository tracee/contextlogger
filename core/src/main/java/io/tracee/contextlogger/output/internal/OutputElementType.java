package io.tracee.contextlogger.output.internal;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by TGI on 06.02.2015.
 */
public enum OutputElementType {

    ATOMIC(Collections.EMPTY_LIST, Collections.EMPTY_LIST),
    COMPLEX(Collections.EMPTY_LIST, Collections.EMPTY_LIST),
    COLLECTION(Collections.EMPTY_LIST, Collections.EMPTY_LIST);

    private final List<Class> supertypes;
    private final List<String> fqnPrefixes;

    private OutputElementType(final List<Class> supertypes, final List<String> fqnPrefixes) {
        this.supertypes = supertypes;
        this.fqnPrefixes = fqnPrefixes;
    }

    private static <T> List<T> provideListForArray(final T... types) {
        return Arrays.asList(types);
    }

    public static OutputElementType getOutputElementTypeForInstance(final Object instance) {
        return null;
    }

}

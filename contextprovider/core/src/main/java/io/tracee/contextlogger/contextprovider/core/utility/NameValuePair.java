package io.tracee.contextlogger.contextprovider.core.utility;

import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderMethod;

/**
 * Main Interface for name value pairs.
 * Created by Tobias Gindler on 21.03.14.
 */
@TraceeContextProvider(displayName = "name-value-pair", suppressTypeInOutput = true)
public class NameValuePair<T> {

    protected static final String DEFAULT_NAME = "<null>";

    private final String name;
    private final T value;

    public NameValuePair(final String name, final T value) {
        this.name = name != null ? name : DEFAULT_NAME;
        this.value = value;
    }

    /**
     * Gets the name for the value.
     *
     * @return the name for the value
     */
    @TraceeContextProviderMethod(displayName = "name", order = 1)
    public final String getName() {
        return this.name;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    @TraceeContextProviderMethod(displayName = "value", order = 2)
    public final T getValue() {
        return this.value;
    }

}

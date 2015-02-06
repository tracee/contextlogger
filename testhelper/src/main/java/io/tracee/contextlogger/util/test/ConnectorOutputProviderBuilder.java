package io.tracee.contextlogger.util.test;

import io.tracee.contextlogger.connector.ConnectorOutputProvider;
import io.tracee.contextlogger.connector.LogConnectorOutputProvider;

/**
 * Creates a {@link io.tracee.contextlogger.connector.ConnectorOutputProvider} or {@link io.tracee.contextlogger.connector.LogConnectorOutputProvider} instance.
 */
public class ConnectorOutputProviderBuilder {

    public static ConnectorOutputProvider createConnectorOutputProvider(final String prefix, final String json) {

        if (prefix == null) {
            return new ConnectorOutputProvider() {

                public ConnectorOutputProvider excludeContextProviders(final Class... contextProvidersToInclude) {
                    return this;
                }

                public String provideOutput() {
                    return json;
                }
            };
        }
        else {
            return new LogConnectorOutputProvider() {

                public ConnectorOutputProvider excludeContextProviders(final Class... contextProvidersToInclude) {
                    return this;
                }

                public String provideOutput() {
                    return json;
                }

                public String getPrefix() {
                    return prefix;
                }

            };
        }

    }

}

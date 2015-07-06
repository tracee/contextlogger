package io.tracee.contextlogger.api;

import io.tracee.contextlogger.contextprovider.api.Profile;
import io.tracee.contextlogger.outputgenerator.writer.BasicOutputWriterConfiguration;

/**
 * Interface for manual overrides. (Enabling / Disabling of certain logger context data)
 * Used to enable fluent API.
 * Created by Tobias Gindler on 28.03.14.
 */
public interface ConfigBuilder<T extends ToStringBuilder> {

    /**
     * Enforces profile for a single createStringRepresentation statement.
     *
     * @param profile the profile to use
     * @return this instance
     */
    ConfigBuilder<T> enforceProfile(Profile profile);

    /**
     * Manually enables context data output for a single createStringRepresentation statement.
     *
     * @param contexts The context data to be enabled.
     * @return This instance
     */
    ConfigBuilder<T> enable(String... contexts);

    /**
     * Manually disables context data output for a single createStringRepresentation statement.
     *
     * @param contexts The context data to be disabled.
     * @return This instance
     */
    ConfigBuilder<T> disable(String... contexts);

    /**
     * Manually disables context data output for a single createStringRepresentation statement.
     *
     * @param types The context data to be disabled.
     * @return This instance
     */
    ConfigBuilder<T> disableTypes(Class... types);

    /**
     * Enforces sorting of passed tracee context provider instances. Other instances will be prepended in passed order.
     *
     * @return This instance
     */
    ConfigBuilder<T> enforceOrder();

    /**
     * Closes configuration.
     *
     * @return This instance cast as a ContextLoggerBuilder.
     */
    T apply();

    /**
     * Manually defines the OutputWriterConfiguration to use.
     *
     * @param outputWriterConfiguration the outputWriterConfiguration to use
     * @return
     */
    ConfigBuilder<T> enforceOutputWriterConfiguration(BasicOutputWriterConfiguration outputWriterConfiguration);

}

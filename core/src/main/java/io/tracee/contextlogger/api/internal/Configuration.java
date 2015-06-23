package io.tracee.contextlogger.api.internal;

import java.util.Map;

import io.tracee.contextlogger.api.ConfigBuilder;
import io.tracee.contextlogger.api.ToStringBuilder;
import io.tracee.contextlogger.contextprovider.api.Profile;
import io.tracee.contextlogger.outputgenerator.writer.OutputWriterConfiguration;

/**
 * Interface for getting configuration from ConfigBuilder.
 * Created by Tobias Gindler on 19.06.14.
 */
public interface Configuration<T extends ToStringBuilder> extends ConfigBuilder<T> {

    /**
     * Gets a map containing all manual context overrides.
     *
     * @return a map containing all manual context overrides
     */
    Map<String, Boolean> getManualContextOverrides();

    /**
     * Gets the profile.
     *
     * @return the profile
     */
    Profile getProfile();

    /**
     * Gets if the order must be kept
     *
     * @return
     */
    boolean getEnforceOrder();

    /**
     * Gets the OutputWriterConfiguration to use
     *
     * @return the OutputWriterConfiguration to use
     */
    OutputWriterConfiguration getOutputWriterConfiguration();

}

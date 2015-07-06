package io.tracee.contextlogger;

/**
 * Common Constants used by Tracee Context logging.
 * Created by Tobias Gindler, holisticon AG on 16.12.13.
 */
public final class TraceeContextLoggerConstants {

    @SuppressWarnings("unused")
    private TraceeContextLoggerConstants() {
        // hide constructor
    }

    public static final String SYSTEM_PROPERTY_PREFIX = "io.tracee.contextlogger.";
    public static final String SYSTEM_PROPERTY_CONNECTOR_PREFIX = SYSTEM_PROPERTY_PREFIX + "connector.";



    public static final String SYSTEM_PROPERTY_CONTEXT_LOGGER_PRESET = SYSTEM_PROPERTY_PREFIX + "preset";
    public static final String SYSTEM_PROPERTY_CONTEXT_LOGGER_PRESET_CLASS = SYSTEM_PROPERTY_PREFIX + "preset.class";

    public static final String SYSTEM_PROPERTY_CONTEXT_LOGGER_CONNECTOR_TYPE = "class";
    public static final String SYSTEM_PROPERTY_CONTEXT_LOGGER_CONNECTOR_KEY_PATTERN = "io\\.tracee\\.contextlogger\\.connector\\.(\\w*?)\\."
            + SYSTEM_PROPERTY_CONTEXT_LOGGER_CONNECTOR_TYPE;

}

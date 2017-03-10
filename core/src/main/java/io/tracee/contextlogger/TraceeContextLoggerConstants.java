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


    public static final int DEFAULT_MAX_DEPTH = 10;
    public static final String SYSTEM_PROPERTY_MAX_DEPTH = SYSTEM_PROPERTY_PREFIX + "maxDepth";
    public static final int MAX_DEPTH;

    static {

        Integer tmpMaxDepth = null;

        try {
            tmpMaxDepth = Integer.valueOf(System.getProperty(SYSTEM_PROPERTY_MAX_DEPTH));
        } catch (NumberFormatException e) {
            // ignore
        }

        if (tmpMaxDepth == null) {
            tmpMaxDepth = DEFAULT_MAX_DEPTH;
        }

        MAX_DEPTH = tmpMaxDepth;
    }

}

package io.tracee.contextlogger.connector;

/**
 * Keeps classnames of well known connector classes. Needed for creating new connector instances via reflection.
 * Connector artifacts should contain a unit test, which checks if the values and the real full qualified class name match.
 */
public final class WellKnownConnectorClassNames {

    private WellKnownConnectorClassNames() {
        // hide Constructor
    }

    public static final String HTTP_CONNECTOR = "io.tracee.contextlogger.connector.http.HttpConnector";

}

package io.tracee.contextlogger.utility;

import java.util.Map;

import io.tracee.contextlogger.connector.Connector;

/**
 * Dummy Connector for unit test.
 */
public class DummyConnector implements Connector {

    private static boolean wasInvoked = false;

    public void init(Map<String, String> properties) {
    }

    public void sendErrorReport(String json) {
        wasInvoked = true;
    }

    public static boolean isWasInvoked() {
        return wasInvoked;
    }
}

package io.tracee.contextlogger.contextprovider.core.tracee;

/**
 * Class used to provide custom message at context logger output.
 */
public class TraceeMessage {

    private final Object message;

    public TraceeMessage(final Object message) {
        this.message = message;
    }

    public Object getMessage() {
        return message;
    }

    /**
     * Wraps a message instance in TraceeMessage instance.
     *
     * @param message the message instance to wrap
     * @return the context provider instance
     */
    public static TraceeMessage wrap(final Object message) {
        return new TraceeMessage(message);
    }
}

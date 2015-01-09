package io.tracee.contextlogger;

import io.tracee.contextlogger.api.internal.MessageLogLevel;

/**
 * Provides a strictly formatted log message prefix.
 */
public final class MessagePrefixProvider {

    public static final MessageLogLevel DEFAULT_LEVEL = MessageLogLevel.INFO;

    static final String JSON_PREFIXED_MESSAGE = "TRACEE_CL_{1}[{2}]  : ";

    public static String provideLogMessagePrefix(final MessageLogLevel logLevel, final String type) {
        String prefix = JSON_PREFIXED_MESSAGE.replace("{1}", logLevel != null ? logLevel.getLevel() : DEFAULT_LEVEL.getLevel());
        prefix = prefix.replace("{2}", type != null ? type : "");
        return prefix;
    }

    public static String provideLogMessagePrefix(final MessageLogLevel logLevel, final Class type) {
        return provideLogMessagePrefix(logLevel, type != null ? type.getSimpleName() : null);
    }
}

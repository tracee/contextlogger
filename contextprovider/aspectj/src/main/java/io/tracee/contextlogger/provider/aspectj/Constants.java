package io.tracee.contextlogger.provider.aspectj;

/**
 * Constants class for watchdog.
 * Created by Tobias Gindler on 07.03.14.
 */
public final class Constants {

    @SuppressWarnings("unused")
    private Constants() {

    }

    public static final String SYSTEM_PROPERTY_IS_ACTIVE = "Watchdog.isActive";
    public static final String TRACEE_ATTRIBUTE_NAME = "context-info-stack";
    public static final String SEPARATOR = "|||---|||";

}

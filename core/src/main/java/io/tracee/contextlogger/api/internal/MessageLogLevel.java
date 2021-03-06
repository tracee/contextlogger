package io.tracee.contextlogger.api.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Enum for defining log level for log output.
 */
public enum MessageLogLevel {

    ERROR() {

        @Override
        public void log(final Logger logger, final String logMessage) {
            logger.error(logMessage);
        }
    },
    WARNING() {

        @Override
        public void log(final Logger logger, final String logMessage) {
            logger.warn(logMessage);
        }
    },
    INFO() {

        @Override
        public void log(final Logger logger, final String logMessage) {
            logger.info(logMessage);
        }
    },
    DEBUG() {

        @Override
        public void log(final Logger logger, final String logMessage) {
            logger.debug(logMessage);
        }
    };

    public String getLevel() {
        return this.name();
    }

    /**
     * Writes the log message with this type.
     *
     * @param logMessage the log message to write
     */
    public void writeLogMessage(final String logMessage) {
        this.writeLogMessage(this.getClass(), logMessage);
    }

    /**
     * Writes a given log message.
     *
     * @param clazz the type used to get the logger for
     * @param logMessage the log message to write
     */
    public void writeLogMessage(final Class clazz, final String logMessage) {
        Logger logger = LoggerFactory.getLogger(clazz);
        this.log(logger, logMessage);
    }

    /**
     * Abstract method to provide different log level imlementations.
     *
     * @param logger the TraceeLogger to use
     * @param logMessage the log message to write
     */
    public abstract void log(Logger logger, final String logMessage);

}

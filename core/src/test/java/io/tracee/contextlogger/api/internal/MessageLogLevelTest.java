package io.tracee.contextlogger.api.internal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit test for {@link io.tracee.contextlogger.api.internal.MessageLogLevel}.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class MessageLogLevelTest {

    private final static String LOG_MESSAGE = "log message";

    private Logger logger = Mockito.mock(Logger.class);

    @Before
    public void init() {

        PowerMockito.mockStatic(LoggerFactory.class);
        PowerMockito.when(LoggerFactory.getLogger(Mockito.any(Class.class))).thenReturn(logger);

    }

    @Test
    public void shouldLogWithDebugLevel() {

        MessageLogLevel.DEBUG.writeLogMessage(LOG_MESSAGE);
        Mockito.verify(logger).debug(LOG_MESSAGE);

    }

    @Test
    public void shouldLogWithWarnLevel() {

        MessageLogLevel.WARNING.writeLogMessage(LOG_MESSAGE);
        Mockito.verify(logger).warn(LOG_MESSAGE);

    }

    @Test
    public void shouldLogWithInfoLevel() {

        MessageLogLevel.INFO.writeLogMessage(LOG_MESSAGE);
        Mockito.verify(logger).info(LOG_MESSAGE);

    }

    @Test
    public void shouldLogWithErrorLevel() {

        MessageLogLevel.ERROR.writeLogMessage(LOG_MESSAGE);
        Mockito.verify(logger).error(LOG_MESSAGE);

    }

    @Test
    public void shouldUsePassedClassForLogging() {

        PowerMockito.when(LoggerFactory.getLogger(MessageLogLevelTest.class)).thenReturn(logger);

		MessageLogLevel.DEBUG.writeLogMessage(this.getClass(), LOG_MESSAGE);

        Mockito.verify(logger).debug(LOG_MESSAGE);

    }

}

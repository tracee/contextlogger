package io.tracee.contextlogger.api.internal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import io.tracee.Tracee;
import io.tracee.TraceeBackend;
import io.tracee.TraceeLogger;
import io.tracee.TraceeLoggerFactory;

/**
 * Unit test for {@link io.tracee.contextlogger.api.internal.MessageLogLevel}.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Tracee.class)
public class MessageLogLevelTest {

    private final static String LOG_MESSAGE = "log message";

    private TraceeBackend traceeBackend = Mockito.mock(TraceeBackend.class);
    private TraceeLoggerFactory traceeLoggerFactory = Mockito.mock(TraceeLoggerFactory.class);
    private TraceeLogger traceeLogger = Mockito.mock(TraceeLogger.class);

    @Before
    public void init() {

        PowerMockito.mockStatic(Tracee.class);

        traceeBackend = Mockito.mock(TraceeBackend.class);
        traceeLoggerFactory = Mockito.mock(TraceeLoggerFactory.class);
        traceeLogger = Mockito.mock(TraceeLogger.class);

        PowerMockito.when(Tracee.getBackend()).thenReturn(traceeBackend);
        Mockito.when(traceeBackend.getLoggerFactory()).thenReturn(traceeLoggerFactory);
        Mockito.when(traceeLoggerFactory.getLogger(Mockito.any(Class.class))).thenReturn(traceeLogger);

    }

    @Test
    public void shouldLogWithDebugLevel() {

        MessageLogLevel.DEBUG.writeLogMessage(LOG_MESSAGE);
        Mockito.verify(traceeLogger).debug(LOG_MESSAGE);

    }

    @Test
    public void shouldLogWithWarnLevel() {

        MessageLogLevel.WARNING.writeLogMessage(LOG_MESSAGE);
        Mockito.verify(traceeLogger).warn(LOG_MESSAGE);

    }

    @Test
    public void shouldLogWithInfoLevel() {

        MessageLogLevel.INFO.writeLogMessage(LOG_MESSAGE);
        Mockito.verify(traceeLogger).info(LOG_MESSAGE);

    }

    @Test
    public void shouldLogWithErrorLevel() {

        MessageLogLevel.ERROR.writeLogMessage(LOG_MESSAGE);
        Mockito.verify(traceeLogger).error(LOG_MESSAGE);

    }

    @Test
    public void shouldUsePassedClassForLogging() {

        MessageLogLevel.DEBUG.writeLogMessage(MessageLogLevelTest.class, LOG_MESSAGE);

        Mockito.verify(traceeLoggerFactory).getLogger(MessageLogLevelTest.class);
        Mockito.verify(traceeLogger).debug(LOG_MESSAGE);

    }

}

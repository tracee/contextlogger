package io.tracee.contextlogger.contextprovider.javaee;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import javax.interceptor.InvocationContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import io.tracee.contextlogger.MessagePrefixProvider;
import io.tracee.contextlogger.TraceeContextLogger;
import io.tracee.contextlogger.api.ConfigBuilder;
import io.tracee.contextlogger.api.ContextLogger;
import io.tracee.contextlogger.api.ErrorMessage;
import io.tracee.contextlogger.contextprovider.api.ImplicitContext;
import io.tracee.contextlogger.api.internal.MessageLogLevel;
import io.tracee.contextlogger.contextprovider.core.tracee.TraceeMessage;

/**
 * Test class for {@link TraceeErrorContextLoggingInterceptor}.
 * Created by Tobias Gindler on 19.06.14.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ TraceeContextLogger.class, TraceeMessage.class, MessagePrefixProvider.class })
public class TraceeErrorContextLoggingInterceptorTest {

    private final static String LOG_MESSAGE_PREFIX = "XX";

    public static final String ERROR_MESSAGE = "ABC";

    private final TraceeErrorContextLoggingInterceptor unit = mock(TraceeErrorContextLoggingInterceptor.class);

    private final ContextLogger contextLogger = mock(ContextLogger.class);
    private final ConfigBuilder<ContextLogger> configBuilder = mock(ConfigBuilder.class);

    private final TraceeMessage traceeMessage = new TraceeMessage("ABC");

    @Before
    public void setupMocks() throws Exception {

        // Let's return true for every Message. The intercept-method must call teh real method. otherwise
        // we would test the mocking framework!
        // java.lang.reflect.Method is not suitable for mocks. Also Powermock is unable to create a mock for it!
        when(unit.intercept(Mockito.any(InvocationContext.class))).thenCallRealMethod();
        mockStatic(TraceeContextLogger.class);
        when(TraceeContextLogger.create()).thenReturn(configBuilder);
        when(configBuilder.enforceOrder()).thenReturn(configBuilder);
        when(configBuilder.apply()).thenReturn(contextLogger);
        mockStatic(TraceeMessage.class);
        when(TraceeMessage.wrap(ERROR_MESSAGE)).thenReturn(traceeMessage);

        // mock log message prefix creation
        mockStatic(MessagePrefixProvider.class);
        when(MessagePrefixProvider.provideLogMessagePrefix(Mockito.any(MessageLogLevel.class), Mockito.any(String.class))).thenReturn(LOG_MESSAGE_PREFIX);
        when(MessagePrefixProvider.provideLogMessagePrefix(Mockito.any(MessageLogLevel.class), Mockito.any(Class.class))).thenReturn(LOG_MESSAGE_PREFIX);
    }

    @Test
    public void shouldBeInitializable() {
        assertThat(new TraceeErrorContextLoggingInterceptor(), is(not(nullValue())));
    }

    @Test
    public void noInteractionWhenNoExceptionOccurs() throws Exception {
        final InvocationContext invocationContext = mock(InvocationContext.class);

        unit.intercept(invocationContext);
        verify(invocationContext).proceed();
        verify(contextLogger, never()).logWithPrefixedMessage(anyString(), any(), any(), any(), any());
    }

    @Test(expected = RuntimeException.class)
    public void logJsonWithPrefixedMessageIfAnExceptionOccurs() throws Exception {
        final InvocationContext invocationContext = mock(InvocationContext.class);
        final RuntimeException exception = new RuntimeException();
        when(invocationContext.proceed()).thenThrow(exception);
        when(invocationContext.getMethod()).thenReturn(
                TraceeErrorContextLoggingInterceptorTest.class.getMethod("logJsonWithPrefixedMessageIfAnExceptionOccurs", null));

        try {
            unit.intercept(invocationContext);
        }
        catch (Exception e) {
            verify(invocationContext).proceed();
            verify(contextLogger).logWithPrefixedMessage(LOG_MESSAGE_PREFIX, ImplicitContext.COMMON, ImplicitContext.TRACEE, invocationContext, exception);
            throw e;
        }
    }

    @ErrorMessage(ERROR_MESSAGE)
    @Test(expected = RuntimeException.class)
    public void logJsonWithAnnotatedMessageAndPrefixedMessageIfAnExceptionOccurs() throws Exception {
        final InvocationContext invocationContext = mock(InvocationContext.class);
        final RuntimeException exception = new RuntimeException();
        when(invocationContext.proceed()).thenThrow(exception);
        when(invocationContext.getMethod()).thenReturn(
                TraceeErrorContextLoggingInterceptorTest.class.getMethod("logJsonWithAnnotatedMessageAndPrefixedMessageIfAnExceptionOccurs", null));

        try {
            unit.intercept(invocationContext);
        }
        catch (Exception e) {
            verify(invocationContext).proceed();
            verify(contextLogger).logWithPrefixedMessage(LOG_MESSAGE_PREFIX, traceeMessage, ImplicitContext.COMMON, ImplicitContext.TRACEE,
                    invocationContext, exception);
            throw e;
        }
    }
}

package io.tracee.contextlogger.javaee;

import io.tracee.contextlogger.ImplicitContext;
import io.tracee.contextlogger.builder.ContextLogger;
import io.tracee.contextlogger.builder.TraceeContextLogger;
import io.tracee.contextlogger.javaee.TraceeEjbErrorContextLoggingInterceptor;
import io.tracee.contextlogger.javaee.TraceeJmsErrorMessageListener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.interceptor.InvocationContext;
import java.lang.reflect.Method;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Test class for {@link io.tracee.contextlogger.javaee.TraceeEjbErrorContextLoggingInterceptor}.
 * Created by Tobias Gindler on 19.06.14.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(TraceeContextLogger.class)
public class TraceeEjbErrorContextLoggingInterceptorTest {


    private final TraceeEjbErrorContextLoggingInterceptor unit = mock(TraceeEjbErrorContextLoggingInterceptor.class);

    private final ContextLogger contextLogger = mock(ContextLogger.class);

    @Before
    public void setupMocks() throws Exception {

        // Let's return true for every Message. The intercept-method must call teh real method. otherwise
        // we would test the mocking framework!
        // java.lang.reflect.Method is not suitable for mocks. Also Powermock is unable to create a mock for it!
        when(unit.intercept(Mockito.any(InvocationContext.class))).thenCallRealMethod();
        mockStatic(TraceeContextLogger.class);
        when(TraceeContextLogger.createDefault()).thenReturn(contextLogger);
    }

    @Test
    public void shouldBeInitializable() {
        assertThat(new TraceeEjbErrorContextLoggingInterceptor(), is(not(nullValue())));
    }

    @Test
    public void noInteractionWhenNoExceptionOccurs() throws Exception {
        final InvocationContext invocationContext = mock(InvocationContext.class);

        unit.intercept(invocationContext);
        verify(invocationContext).proceed();
        verify(contextLogger, never()).logJsonWithPrefixedMessage(anyString(), any(), any(), any(), any());
    }

    @Test(expected = RuntimeException.class)
    public void logJsonWithPrefixedMessageIfAnExceptionOccurs() throws Exception {
        final InvocationContext invocationContext = mock(InvocationContext.class);
        final RuntimeException exception = new RuntimeException();
        when(invocationContext.proceed()).thenThrow(exception);

        try {
            unit.intercept(invocationContext);
        } catch (Exception e) {
            verify(invocationContext).proceed();
            verify(contextLogger).logJsonWithPrefixedMessage(TraceeEjbErrorContextLoggingInterceptor.JSON_PREFIXED_MESSAGE,
                    ImplicitContext.COMMON, ImplicitContext.TRACEE, invocationContext, exception);
            throw e;
        }
    }
}
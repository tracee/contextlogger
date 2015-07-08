package io.tracee.contextlogger.contextprovider.javaee;

import io.tracee.contextlogger.TraceeContextLogger;
import io.tracee.contextlogger.api.ConfigBuilder;
import io.tracee.contextlogger.api.ContextLogger;
import io.tracee.contextlogger.contextprovider.core.CoreImplicitContextProviders;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TraceeContextLogger.class)
public class TraceeJmsErrorMessageListenerTest {

    private final TraceeJmsErrorMessageListener unit = mock(TraceeJmsErrorMessageListener.class);

    private final ContextLogger contextLogger = mock(ContextLogger.class);

    private final ConfigBuilder<ContextLogger> configBuilder = mock(ConfigBuilder.class);

    @Before
    public void setupMocks() throws Exception {

        // Let's return true for every Message. The intercept-method must call teh real method. otherwise
        // we would test the mocking framework!
        // java.lang.reflect.Method is not suitable for mocks. Also Powermock is unable to create a mock for it!
        when(unit.intercept(Mockito.any(InvocationContext.class))).thenCallRealMethod();
        when(unit.isMessageListenerOnMessageMethod(Mockito.any(Method.class))).thenReturn(true);

        mockStatic(TraceeContextLogger.class);
        when(TraceeContextLogger.create()).thenReturn(configBuilder);
        when(configBuilder.enforceOrder()).thenReturn(configBuilder);
        when(configBuilder.apply()).thenReturn(contextLogger);
    }

    @Test
    public void shouldBeInitializable() {
        assertThat(new TraceeJmsErrorMessageListener(), is(not(nullValue())));
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

        try {
            unit.intercept(invocationContext);
        }
        catch (Exception e) {
            verify(invocationContext).proceed();
            verify(contextLogger).logWithPrefixedMessage(TraceeJmsErrorMessageListener.JSON_PREFIXED_MESSAGE, CoreImplicitContextProviders.COMMON,
					CoreImplicitContextProviders.TRACEE, invocationContext, exception);
            throw e;
        }
    }
}

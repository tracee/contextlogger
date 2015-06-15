package io.tracee.contextlogger.contextprovider.jaxws;

import static org.mockito.Mockito.never;

import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import io.tracee.contextlogger.TraceeContextLogger;

/**
 * Test class for {@link TraceeClientErrorLoggingHandler}.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ TraceeContextLogger.class })
public class TraceeClientErrorLoggingHandlerTest {

    private TraceeContextLogger contextLogger;
    private TraceeClientErrorLoggingHandler unit;

    private SOAPMessageContext contextMock;
    private TraceeClientErrorLoggingHandler handlerSpy;

    @Before
    public void setup() {

        AbstractTraceeErrorLoggingHandler.THREAD_LOCAL_SOAP_MESSAGE_STR.remove();

        unit = new TraceeClientErrorLoggingHandler();
        contextMock = Mockito.mock(SOAPMessageContext.class);
        handlerSpy = Mockito.spy(unit);

    }

    @Test
    public void shouldCallStoreMessageInThreadLocalForOutgoing() {

        handlerSpy.handleOutgoing(contextMock);
        Mockito.verify(handlerSpy).storeMessageInThreadLocal(contextMock);
    }

    @Test
    public void shouldDoNothingForIncoming() {

        handlerSpy.handleIncoming(contextMock);
        Mockito.verify(handlerSpy, never()).storeMessageInThreadLocal(contextMock);
    }

}

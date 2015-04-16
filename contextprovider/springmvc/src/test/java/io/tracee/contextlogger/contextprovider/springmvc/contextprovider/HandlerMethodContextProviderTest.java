package io.tracee.contextlogger.contextprovider.springmvc.contextprovider;

import java.lang.reflect.Method;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;

/**
 * Test class for {@link HandlerMethodContextProvider}.
 */
public class HandlerMethodContextProviderTest {

    private HandlerMethod handlerMethod;

    @Before
    public void init() {
        handlerMethod = Mockito.mock(HandlerMethod.class);
    }

    @Test
    public void should_handle_null_valued_handler_method_correctly() {

        HandlerMethodContextProvider handlerMethodContextProvider = HandlerMethodContextProvider.wrap(null);

        MatcherAssert.assertThat(handlerMethodContextProvider.getType(), Matchers.nullValue());
        MatcherAssert.assertThat(handlerMethodContextProvider.getMethod(), Matchers.nullValue());
        MatcherAssert.assertThat(handlerMethodContextProvider.getParameters(), Matchers.nullValue());
        MatcherAssert.assertThat(handlerMethodContextProvider.getSerializedTargetInstance(), Matchers.nullValue());

    }

    @Test
    public void should_output_type_correctly() {

        Mockito.when(handlerMethod.getBeanType()).thenReturn(null);
        HandlerMethodContextProvider handlerMethodContextProvider = HandlerMethodContextProvider.wrap(handlerMethod);
        MatcherAssert.assertThat(handlerMethodContextProvider.getType(), Matchers.nullValue());

        Mockito.when(handlerMethod.getBeanType()).thenReturn((Class)HandlerMethodContextProviderTest.class);
        MatcherAssert.assertThat(handlerMethodContextProvider.getType(), Matchers.is(HandlerMethodContextProviderTest.class.getCanonicalName()));

    }

    @Test
    public void should_output_method_correctly() throws NoSuchMethodException {
        final String methodName = "should_output_method_correctly";
        Method method = this.getClass().getMethod(methodName);

        Mockito.when(handlerMethod.getMethod()).thenReturn(null);
        HandlerMethodContextProvider handlerMethodContextProvider = HandlerMethodContextProvider.wrap(handlerMethod);
        MatcherAssert.assertThat(handlerMethodContextProvider.getMethod(), Matchers.nullValue());

        Mockito.when(handlerMethod.getMethod()).thenReturn(method);
        MatcherAssert.assertThat(handlerMethodContextProvider.getMethod(), Matchers.is(methodName));

    }

    @Test
    public void should_output_parameters_correctly() {
        final MethodParameter methodParameter = Mockito.mock(MethodParameter.class);
        MethodParameter[] methodParameters = { methodParameter };

        Mockito.when(handlerMethod.getMethodParameters()).thenReturn(null);
        HandlerMethodContextProvider handlerMethodContextProvider = HandlerMethodContextProvider.wrap(handlerMethod);
        MatcherAssert.assertThat(handlerMethodContextProvider.getParameters(), Matchers.nullValue());

        Mockito.when(handlerMethod.getMethodParameters()).thenReturn(methodParameters);
        List<MethodParameter> result = handlerMethodContextProvider.getParameters();
        MatcherAssert.assertThat(result.size(), Matchers.is(1));
        MatcherAssert.assertThat(result.get(0), Matchers.is(methodParameter));

    }

    @Test
    public void should_output_target_instance_correctly() {
        final String bean = "ABC";

        Mockito.when(handlerMethod.getBean()).thenReturn(null);
        HandlerMethodContextProvider handlerMethodContextProvider = HandlerMethodContextProvider.wrap(handlerMethod);
        MatcherAssert.assertThat(handlerMethodContextProvider.getSerializedTargetInstance(), Matchers.nullValue());

        Mockito.when(handlerMethod.getBean()).thenReturn(bean);
        Object result = handlerMethodContextProvider.getSerializedTargetInstance();
        MatcherAssert.assertThat(result, Matchers.is((Object)bean));

    }

}

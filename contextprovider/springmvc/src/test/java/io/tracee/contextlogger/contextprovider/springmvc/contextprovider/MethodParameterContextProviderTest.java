package io.tracee.contextlogger.contextprovider.springmvc.contextprovider;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.core.MethodParameter;

/**
 * Test class for {@link MethodParameterContextProvider}.
 */
public class MethodParameterContextProviderTest {

    @Test
    public void should_output_type_correctly() {

        MatcherAssert.assertThat(MethodParameterContextProvider.wrap(null).getType(), Matchers.nullValue());

        MethodParameter methodParameter = Mockito.mock(MethodParameter.class);
        Mockito.when(methodParameter.getParameterType()).thenReturn((Class)String.class);

        MatcherAssert.assertThat(MethodParameterContextProvider.wrap(methodParameter).getType(), Matchers.is(String.class));

    }

}

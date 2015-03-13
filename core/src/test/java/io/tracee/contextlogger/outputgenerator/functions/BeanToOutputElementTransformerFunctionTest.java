package io.tracee.contextlogger.outputgenerator.functions;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.outputgenerator.RecursiveOutputElementTreeBuilderState;
import io.tracee.contextlogger.outputgenerator.outputelements.ComplexOutputElement;
import io.tracee.contextlogger.output.internal.testclasses.BeanTestClass;

/**
 * Unit test for {@link io.tracee.contextlogger.outputgenerator.functions.BeanToOutputElementTransformerFunction}.
 */
public class BeanToOutputElementTransformerFunctionTest {

    @Test
    public void should_create_complex_output_element_correctly() {

        ComplexOutputElement complexOutputElement = (ComplexOutputElement)BeanToOutputElementTransformerFunction.getInstance().apply(
                new ToStringRecursiveContext(), new RecursiveOutputElementTreeBuilderState(), new BeanTestClass());

        MatcherAssert.assertThat(complexOutputElement.getOutputElements().size(), Matchers.is(1));
        MatcherAssert.assertThat(complexOutputElement.getOutputElementsBaseType(), Matchers.<Class> is(BeanTestClass.class));

    }

}

package io.tracee.contextlogger.outputgenerator.functions;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.outputgenerator.RecursiveOutputElementTreeBuilderState;
import io.tracee.contextlogger.outputgenerator.outputelements.CollectionOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.OutputElementType;

/**
 * Unit test for {@link io.tracee.contextlogger.outputgenerator.functions.ArrayToOutputElementTransformerFunction}
 */
public class ArrayToOutputElementTransformerFunctionTest {

    @Test
    public void should_transform_array_correctly() {

        Object[] array = { "ABC", "DEF", "GHI" };

        CollectionOutputElement collectionOutputElement = (CollectionOutputElement)ArrayToOutputElementTransformerFunction.getInstance().apply(
                new ToStringRecursiveContext(), new RecursiveOutputElementTreeBuilderState(), array);

        MatcherAssert.assertThat(collectionOutputElement.getOutputElementsBaseType(), Matchers.<Class> is(Object[].class));
        MatcherAssert.assertThat(collectionOutputElement.getOutputElements().size(), Matchers.is(3));

    }

    @Test
    public void should_return_NullValueOutputElement_for_null_valued_instance() {

        Object[] array = null;

        OutputElement outputElement = ArrayToOutputElementTransformerFunction.getInstance().apply(new ToStringRecursiveContext(),
                new RecursiveOutputElementTreeBuilderState(), array);

        MatcherAssert.assertThat(outputElement, Matchers.notNullValue());
        MatcherAssert.assertThat(outputElement.getOutputElementType(), Matchers.is(OutputElementType.NULL));

    }
}

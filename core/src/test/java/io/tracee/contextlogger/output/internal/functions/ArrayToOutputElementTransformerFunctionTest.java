package io.tracee.contextlogger.output.internal.functions;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.output.internal.CollectionOutputElement;

/**
 * Unit test for {@link io.tracee.contextlogger.output.internal.functions.ArrayToOutputElementTransformerFunction}
 */
public class ArrayToOutputElementTransformerFunctionTest {

    @Test
    public void should_transform_array_correctly() {

        Object[] array = { "ABC", "DEF", "GHI" };

        CollectionOutputElement collectionOutputElement = (CollectionOutputElement)ArrayToOutputElementTransformerFunction.getInstance().apply(
                new ToStringRecursiveContext(), array);

        MatcherAssert.assertThat(collectionOutputElement.getOutputElementsBaseType(), Matchers.<Class> is(Object[].class));
        MatcherAssert.assertThat(collectionOutputElement.getOutputElements().size(), Matchers.is(3));

    }

    @Test
    public void should_return_null_for_null_valued_instance() {

        Object[] array = null;

        CollectionOutputElement collectionOutputElement = (CollectionOutputElement)ArrayToOutputElementTransformerFunction.getInstance().apply(
                new ToStringRecursiveContext(), array);

        MatcherAssert.assertThat(collectionOutputElement, Matchers.nullValue());

    }
}

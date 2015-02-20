package io.tracee.contextlogger.output.internal.functions;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.output.internal.outputelements.CollectionOutputElement;

/**
 * Unit test for {@link io.tracee.contextlogger.output.internal.functions.CollectionToOutputElementTransformerFunction}.
 */
public class CollectionToOutputElementTransformerFunctionTest {

    @Test
    public void should_transform_collection_correctly() {

        List<String> list = new ArrayList<String>();
        list.add("ABC");
        list.add("DEF");
        list.add("GHI");

        CollectionOutputElement collectionOutputElement = (CollectionOutputElement)CollectionToOutputElementTransformerFunction.getInstance().apply(
                new ToStringRecursiveContext(), list);

        MatcherAssert.assertThat(collectionOutputElement.getOutputElementsBaseType(), Matchers.<Class> is(ArrayList.class));
        MatcherAssert.assertThat(collectionOutputElement.getOutputElements().size(), Matchers.is(3));

    }

    @Test
    public void should_return_null_for_null_valued_instance() {

        List<String> list = null;

        CollectionOutputElement collectionOutputElement = (CollectionOutputElement)CollectionToOutputElementTransformerFunction.getInstance().apply(
                new ToStringRecursiveContext(), list);

        MatcherAssert.assertThat(collectionOutputElement, Matchers.nullValue());

    }

}

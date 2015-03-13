package io.tracee.contextlogger.outputgenerator.functions;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.outputgenerator.RecursiveOutputElementTreeBuilderState;
import io.tracee.contextlogger.outputgenerator.outputelements.CollectionOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.OutputElementType;

/**
 * Unit test for {@link io.tracee.contextlogger.outputgenerator.functions.CollectionToOutputElementTransformerFunction}.
 */
public class CollectionToOutputElementTransformerFunctionTest {

    @Test
    public void should_transform_collection_correctly() {

        List<String> list = new ArrayList<String>();
        list.add("ABC");
        list.add("DEF");
        list.add("GHI");

        CollectionOutputElement collectionOutputElement = (CollectionOutputElement)CollectionToOutputElementTransformerFunction.getInstance().apply(
                new ToStringRecursiveContext(), new RecursiveOutputElementTreeBuilderState(), list);

        MatcherAssert.assertThat(collectionOutputElement.getOutputElementsBaseType(), Matchers.<Class> is(ArrayList.class));
        MatcherAssert.assertThat(collectionOutputElement.getOutputElements().size(), Matchers.is(3));

    }

    @Test
    public void should_return_null_valued_output_NullValueOutputElement_for_null_valued_instance() {

        List<String> list = null;

        OutputElement outputElement = CollectionToOutputElementTransformerFunction.getInstance().apply(new ToStringRecursiveContext(),
                new RecursiveOutputElementTreeBuilderState(), list);

        MatcherAssert.assertThat(outputElement, Matchers.notNullValue());
        MatcherAssert.assertThat(outputElement.getOutputElementType(), Matchers.is(OutputElementType.NULL));
    }

}

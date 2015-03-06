package io.tracee.contextlogger.output.internal.functions;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.output.internal.RecursiveOutputElementTreeBuilderState;
import io.tracee.contextlogger.output.internal.outputelements.ComplexOutputElement;
import io.tracee.contextlogger.output.internal.outputelements.OutputElement;
import io.tracee.contextlogger.output.internal.outputelements.OutputElementType;

/**
 * Unit test for {@link io.tracee.contextlogger.output.internal.functions.MapToOutputElementTransformerFunction}.
 */
public class MapToOutputElementTransformerFunctionTest {

    @Test
    public void should_transform_collection_correctly() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("ABC", "abc");
        map.put("DEF", "def");
        map.put("GHI", "ghi");

        ComplexOutputElement complexOutputElement = (ComplexOutputElement)MapToOutputElementTransformerFunction.getInstance().apply(
                new ToStringRecursiveContext(), new RecursiveOutputElementTreeBuilderState(), map);

        MatcherAssert.assertThat(complexOutputElement.getOutputElementsBaseType(), Matchers.<Class> is(HashMap.class));
        MatcherAssert.assertThat(complexOutputElement.getOutputElements().size(), Matchers.is(3));

    }

    @Test
    public void should_return_null_for_null_valued_instance() {

        Map<String, String> map = null;

        OutputElement outputElement = MapToOutputElementTransformerFunction.getInstance().apply(new ToStringRecursiveContext(),
                new RecursiveOutputElementTreeBuilderState(), map);

        MatcherAssert.assertThat(outputElement, Matchers.notNullValue());
        MatcherAssert.assertThat(outputElement.getOutputElementType(), Matchers.is(OutputElementType.NULL));

    }
}

package io.tracee.contextlogger.contextprovider.core.java.arrays;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test class for {@link LongArrayContextProvider}.
 */
public class LongArrayContextProviderTest {

    public static final long[] EMPTY_ARRAY = {};
    public static final long[] FILLED_ARRAY_WITH_SINGLE_ELEMENT = {1L};
    public static final long[] FILLED_ARRAY_WITH_MULTIPLE_ELEMENTS = {1L, 2L};

    private LongArrayContextProvider unit = new LongArrayContextProvider();


    @Test
    public void should_return_null_for_null_valued_contextdata() {

        unit.setContextData(null);

        MatcherAssert.assertThat(unit.getPrimitiveTypeValue(), Matchers.nullValue());

    }

    @Test
    public void should_process_empty_array_correctly() {

        unit.setContextData(EMPTY_ARRAY);

        String result = unit.getPrimitiveTypeValue();

        MatcherAssert.assertThat(result, Matchers.is("[]"));

    }

    @Test
    public void should_process_array_with_single_element_correctly() {

        unit.setContextData(FILLED_ARRAY_WITH_SINGLE_ELEMENT);

        String result = unit.getPrimitiveTypeValue();

        MatcherAssert.assertThat(result, Matchers.is("[1]"));

    }

    @Test
    public void should_process_array_with_multiple_elements_correctly() {

        unit.setContextData(FILLED_ARRAY_WITH_MULTIPLE_ELEMENTS);

        String result = unit.getPrimitiveTypeValue();

        MatcherAssert.assertThat(result, Matchers.is("[1" + ArrayContextProviderConstants.ELEMENT_SEPARATOR + "2]"));

    }


}

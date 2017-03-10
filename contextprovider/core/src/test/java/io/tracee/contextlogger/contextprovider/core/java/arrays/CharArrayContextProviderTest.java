package io.tracee.contextlogger.contextprovider.core.java.arrays;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test class for {@link CharArrayContextProvider}.
 */
public class CharArrayContextProviderTest {

    public static final char[] EMPTY_ARRAY = {};
    public static final char[] FILLED_ARRAY_WITH_SINGLE_ELEMENT = {'A'};
    public static final char[] FILLED_ARRAY_WITH_MULTIPLE_ELEMENTS = {'A', 'B'};

    private CharArrayContextProvider unit = new CharArrayContextProvider();


    @Test
    public void should_return_null_for_null_valued_contextdata() {

        unit.setContextData(null);

        MatcherAssert.assertThat(unit.getArrayStringRepresentation(), Matchers.nullValue());

    }

    @Test
    public void should_process_empty_array_correctly() {

        unit.setContextData(EMPTY_ARRAY);

        String result = unit.getArrayStringRepresentation();

        MatcherAssert.assertThat(result, Matchers.is("[]"));

    }

    @Test
    public void should_process_array_with_single_element_correctly() {

        unit.setContextData(FILLED_ARRAY_WITH_SINGLE_ELEMENT);

        String result = unit.getArrayStringRepresentation();

        MatcherAssert.assertThat(result, Matchers.is("['A']"));

    }

    @Test
    public void should_process_array_with_multiple_elements_correctly() {

        unit.setContextData(FILLED_ARRAY_WITH_MULTIPLE_ELEMENTS);

        String result = unit.getArrayStringRepresentation();

        MatcherAssert.assertThat(result, Matchers.is("['A'" + ArrayContextProviderConstants.ELEMENT_SEPARATOR + "'B']"));

    }


}

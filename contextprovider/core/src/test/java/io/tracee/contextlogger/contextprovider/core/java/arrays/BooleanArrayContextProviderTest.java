package io.tracee.contextlogger.contextprovider.core.java.arrays;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test class for {@link BooleanArrayContextProvider}.
 */
public class BooleanArrayContextProviderTest {

    public static final boolean[] EMPTY_ARRAY = {};
    public static final boolean[] FILLED_ARRAY_WITH_SINGLE_ELEMENT = {true};
    public static final boolean[] FILLED_ARRAY_WITH_MULTIPLE_ELEMENTS = {true, false};

    private BooleanArrayContextProvider unit = new BooleanArrayContextProvider();


    @Test
    public void should_return_null_for_null_valued_contextdata() {

        BooleanArrayContextProvider givenContextProvider = new BooleanArrayContextProvider();
        givenContextProvider.setContextData(null);

        MatcherAssert.assertThat(givenContextProvider.getArrayStringRepresentation(), Matchers.nullValue());

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

        MatcherAssert.assertThat(result, Matchers.is("[true]"));

    }

    @Test
    public void should_process_array_with_multiple_elements_correctly() {

        unit.setContextData(FILLED_ARRAY_WITH_MULTIPLE_ELEMENTS);

        String result = unit.getArrayStringRepresentation();

        MatcherAssert.assertThat(result, Matchers.is("[true" + ArrayContextProviderConstants.ELEMENT_SEPARATOR + "false]"));

    }


}

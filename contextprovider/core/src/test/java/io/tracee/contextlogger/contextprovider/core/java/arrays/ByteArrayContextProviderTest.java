package io.tracee.contextlogger.contextprovider.core.java.arrays;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Base64;

/**
 * Test class for {@link ByteArrayContextProvider}.
 */
public class ByteArrayContextProviderTest {

    public static final byte[] EMPTY_ARRAY = {};
    public static final byte[] FILLED_ARRAY = "abc".getBytes();


    private ByteArrayContextProvider unit = new ByteArrayContextProvider();


    @Test
    public void should_return_null_for_null_valued_contextdata() {

        BooleanArrayContextProvider givenContextProvider = new BooleanArrayContextProvider();
        givenContextProvider.setContextData(null);

        MatcherAssert.assertThat(givenContextProvider.getArrayStringRepresentation(), Matchers.nullValue());

    }

    @Test
    public void should_process_array_correctly() {

        unit.setContextData(EMPTY_ARRAY);

        String result = unit.getArrayStringRepresentation();

        MatcherAssert.assertThat(result, Matchers.is(""));

    }


    @Test
    public void should_process_filled_array_correctly() {

        unit.setContextData(FILLED_ARRAY);

        String result = unit.getArrayStringRepresentation();

        MatcherAssert.assertThat(result, Matchers.is(Base64.getEncoder().encodeToString(FILLED_ARRAY)));

    }
}

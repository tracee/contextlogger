package io.tracee.contextlogger.output.internal;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test class for {@link io.tracee.contextlogger.output.internal.DeserializationState}.
 */
public class DeserializationStateTest {

    @Test
    public void should_add_nonexisting_instance() {

        String test = "ABC";

        DeserializationState deserializationState = new DeserializationState();

        boolean check = deserializationState.instanceAlreadyExists(test);

        MatcherAssert.assertThat(check, Matchers.is(false));

        deserializationState.add(test);
        check = deserializationState.instanceAlreadyExists(test);

        MatcherAssert.assertThat(check, Matchers.is(true));

    }

}

package io.tracee.contextlogger.output.internal;

import io.tracee.contextlogger.output.internal.outputelements.AtomicOutputElement;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test class for {@link InstanceToOutputElementPool}.
 */
public class InstanceToOutputElementPoolTest {

    @Test
    public void should_add_nonexisting_instance() {

        String test = "ABC";

        InstanceToOutputElementPool instanceToOutputElementPool = new InstanceToOutputElementPool();

        boolean check = instanceToOutputElementPool.isInstanceMarkedAsProcessed(test);

        MatcherAssert.assertThat(check, Matchers.is(false));

        instanceToOutputElementPool.add(test, new AtomicOutputElement(String.class, test));
        check = instanceToOutputElementPool.isInstanceMarkedAsProcessed(test);

        MatcherAssert.assertThat(check, Matchers.is(true));

    }

}

package io.tracee.contextlogger.output.internal;

import io.tracee.contextlogger.outputgenerator.InstanceToOutputElementPool;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.outputgenerator.outputelements.AtomicOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;

/**
 * Test class for {@link io.tracee.contextlogger.outputgenerator.InstanceToOutputElementPool}.
 */
public class InstanceToOutputElementPoolTest {

    @Test
    public void should_add_nonexisting_instance() {

        String test = "ABC";
        OutputElement outputElement = new AtomicOutputElement(test.getClass(), test);

        InstanceToOutputElementPool instanceToOutputElementPool = new InstanceToOutputElementPool();

        boolean check = instanceToOutputElementPool.isInstanceMarkedAsProcessed(outputElement);

        MatcherAssert.assertThat(check, Matchers.is(false));

        instanceToOutputElementPool.add(outputElement);
        check = instanceToOutputElementPool.isInstanceMarkedAsProcessed(outputElement);

        MatcherAssert.assertThat(check, Matchers.is(true));

    }

}

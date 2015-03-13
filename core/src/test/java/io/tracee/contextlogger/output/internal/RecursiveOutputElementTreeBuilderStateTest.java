package io.tracee.contextlogger.output.internal;

import io.tracee.contextlogger.outputgenerator.RecursiveOutputElementTreeBuilderState;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit test for {@link io.tracee.contextlogger.outputgenerator.RecursiveOutputElementTreeBuilderState};
 */
public class RecursiveOutputElementTreeBuilderStateTest {

    public static final int MAX_DEPTH = 3;

    @Test
    public void should_create_next_instance_correctly() {
        RecursiveOutputElementTreeBuilderState baseState = new RecursiveOutputElementTreeBuilderState(MAX_DEPTH);

        RecursiveOutputElementTreeBuilderState childState = baseState.next();

        MatcherAssert.assertThat(childState.getCurrentDepth(), Matchers.is(baseState.getCurrentDepth() + 1));

    }

    @Test
    public void should_detect_max_depth_boundary_correctly() {
        RecursiveOutputElementTreeBuilderState baseState = new RecursiveOutputElementTreeBuilderState(MAX_DEPTH);

        for (int i = 1; i <= MAX_DEPTH; i++) {
            MatcherAssert.assertThat(baseState.maxDepthReached(), Matchers.is(false));

            baseState = baseState.next();

        }
        MatcherAssert.assertThat(baseState.maxDepthReached(), Matchers.is(true));

    }

}

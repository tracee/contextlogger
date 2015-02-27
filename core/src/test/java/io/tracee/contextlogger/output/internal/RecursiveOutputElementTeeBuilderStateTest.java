package io.tracee.contextlogger.output.internal;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit test for {@link io.tracee.contextlogger.output.internal.RecursiveOutputElementTeeBuilderState};
 */
public class RecursiveOutputElementTeeBuilderStateTest {

    public static final int MAX_DEPTH = 3;

    @Test
    public void should_create_next_instance_correctly() {
        RecursiveOutputElementTeeBuilderState baseState = new RecursiveOutputElementTeeBuilderState(MAX_DEPTH);

        RecursiveOutputElementTeeBuilderState childState = baseState.next();

        MatcherAssert.assertThat(childState.getCurrentDepth(), Matchers.is(baseState.getCurrentDepth() + 1));

    }

    @Test
    public void should_detect_max_depth_boundary_correctly() {
        RecursiveOutputElementTeeBuilderState baseState = new RecursiveOutputElementTeeBuilderState(MAX_DEPTH);

        for (int i = 1; i < MAX_DEPTH; i++) {
            MatcherAssert.assertThat(baseState.maxDepthNotReached(), Matchers.is(true));

            baseState = baseState.next();

        }
        MatcherAssert.assertThat(baseState.maxDepthNotReached(), Matchers.is(false));

    }

}

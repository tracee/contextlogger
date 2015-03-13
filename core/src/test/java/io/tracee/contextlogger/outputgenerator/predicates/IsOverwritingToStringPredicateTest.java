package io.tracee.contextlogger.outputgenerator.predicates;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.outputgenerator.writer.TestOutputElementWriter;

/**
 * Test class for {@link IsOverwritingToStringPredicate}.
 */
public class IsOverwritingToStringPredicateTest {

    @Test
    public void should_detect_overwritten_tostring_method_correctly() {
        MatcherAssert.assertThat(IsOverwritingToStringPredicate.getInstance().apply(this), Matchers.is(true));
    }

    @Test
    public void should_not_detect_non_overwritten_tostring_method() {
        MatcherAssert.assertThat(IsOverwritingToStringPredicate.getInstance().apply(new TestOutputElementWriter()), Matchers.is(false));
    }

    public String toString() {
        return "MURX";
    }

}

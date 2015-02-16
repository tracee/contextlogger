package io.tracee.contextlogger.output.internal.predicates;

import java.util.HashSet;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.output.internal.testclasses.TestClassA;

/**
 * Unit test for {@link io.tracee.contextlogger.output.internal.predicates.IsBeanTypePredicate}.
 */
public class IsBeanTypePredicateTest {

    @Test
    public void should_detect_bean_correctly() {
        boolean result = IsBeanTypePredicate.getInstance().apply(new TestClassA());
        MatcherAssert.assertThat(result, Matchers.is(true));
    }

    @Test
    public void should_detect_non_bean_type_correctly() {

        boolean result = IsBeanTypePredicate.getInstance().apply("ABC");
        MatcherAssert.assertThat(result, Matchers.is(false));

        result = IsBeanTypePredicate.getInstance().apply(new HashSet<String>());
        MatcherAssert.assertThat(result, Matchers.is(false));

    }

}

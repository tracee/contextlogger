package io.tracee.contextlogger.output.internal.predicates;

import java.util.ArrayList;
import java.util.HashSet;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.output.internal.testclasses.TestClassA;

/**
 * Unit test for {@link io.tracee.contextlogger.output.internal.predicates.IsCollectionTypePredicate}.
 */
public class IsCollectionTypePredicateTest {

    public static final String[] STRING_ARRAY = { "A", "B", "C" };

    @Test
    public void should_detect_list_correctly() {
        boolean result = IsCollectionTypePredicate.getInstance().apply(new ArrayList<String>());
        MatcherAssert.assertThat(result, Matchers.is(true));
    }

    @Test
    public void should_detect_set_correctly() {
        boolean result = IsCollectionTypePredicate.getInstance().apply(new HashSet<String>());
        MatcherAssert.assertThat(result, Matchers.is(true));
    }

    @Test
    public void should_detect_array_correctly() {
        boolean result = IsCollectionTypePredicate.getInstance().apply(STRING_ARRAY);
        MatcherAssert.assertThat(result, Matchers.is(true));
    }

    @Test
    public void should_detect_non_collection_type_correctly() {
        boolean result = IsCollectionTypePredicate.getInstance().apply(new TestClassA());
        MatcherAssert.assertThat(result, Matchers.is(false));
    }

}

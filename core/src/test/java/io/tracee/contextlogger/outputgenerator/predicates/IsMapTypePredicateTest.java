package io.tracee.contextlogger.outputgenerator.predicates;

import java.util.HashMap;
import java.util.Hashtable;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.output.internal.testclasses.TestClassA;

/**
 * Unit test for {@link io.tracee.contextlogger.outputgenerator.predicates.IsMapTypePredicate}.
 */
public class IsMapTypePredicateTest {

    @Test
    public void should_detect_hashmap_correctly() {
        boolean result = IsMapTypePredicate.getInstance().apply(new HashMap<String, String>());
        MatcherAssert.assertThat(result, Matchers.is(true));
    }

    @Test
    public void should_detect_hashtable_correctly() {
        boolean result = IsMapTypePredicate.getInstance().apply(new Hashtable<String, String>());
        MatcherAssert.assertThat(result, Matchers.is(true));
    }

    @Test
    public void should_detect_non_map_type_correctly() {
        boolean result = IsMapTypePredicate.getInstance().apply(new TestClassA());
        MatcherAssert.assertThat(result, Matchers.is(false));
    }

}

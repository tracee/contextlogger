package io.tracee.contextlogger.outputgenerator.predicates;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.contextprovider.java.JavaThrowableContextProvider;
import io.tracee.contextlogger.output.internal.testclasses.TestClassA;

/**
 * Unit test for {@link io.tracee.contextlogger.outputgenerator.predicates.IsTraceeContextProviderPredicate}.
 */
public class IsTraceeContextProviderPredicateTest {

    @Test
    public void should_detect_tracee_context_provider_type_correctly() {
        boolean result = IsTraceeContextProviderPredicate.getInstance().apply(new JavaThrowableContextProvider(null));
        MatcherAssert.assertThat(result, Matchers.is(true));
    }

    @Test
    public void should_detect_non_tracee_context_provider_type_correctly() {
        boolean result = IsTraceeContextProviderPredicate.getInstance().apply(new TestClassA());
        MatcherAssert.assertThat(result, Matchers.is(false));
    }

}

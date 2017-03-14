package io.tracee.contextlogger.outputgenerator.predicates;

import io.tracee.contextlogger.contextprovider.core.java.arrays.ByteArrayContextProvider;
import io.tracee.contextlogger.output.internal.testclasses.TestClassA;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit test for {@link io.tracee.contextlogger.outputgenerator.predicates.IsTraceeContextProviderPrimitiveTypePredicate}.
 */
public class IsTraceeContextProviderPrimitiveTypePredicateTest {

    @Test
    public void should_detect_tracee_context_provider_type_correctly() {
        boolean result = IsTraceeContextProviderPrimitiveTypePredicate.getInstance().apply(new ByteArrayContextProvider(null));
        MatcherAssert.assertThat(result, Matchers.is(true));
    }

    @Test
    public void should_detect_non_tracee_context_provider_type_correctly() {
        boolean result = IsTraceeContextProviderPrimitiveTypePredicate.getInstance().apply(new TestClassA());
        MatcherAssert.assertThat(result, Matchers.is(false));
    }

}

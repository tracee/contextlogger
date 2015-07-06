package io.tracee.contextlogger.outputgenerator.predicates;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.contextprovider.api.ImplicitContext;

/**
 * Unit test for {@link io.tracee.contextlogger.outputgenerator.predicates.IsImplicitContextEnumValuePredicate}.
 */
public class IsImplicitContextEnumValuePredicateTest {

    @Test
    public void should_detect_implicit_context_enum_value_correctly() {
        MatcherAssert.assertThat(IsImplicitContextEnumValuePredicate.getInstance().apply(ImplicitContext.COMMON), Matchers.is(true));
        MatcherAssert.assertThat(IsImplicitContextEnumValuePredicate.getInstance().apply(ImplicitContext.TRACEE), Matchers.is(true));
    }

    @Test
    public void should_detect_non_implicit_context_enum_value_correctly() {
        MatcherAssert.assertThat(IsImplicitContextEnumValuePredicate.getInstance().apply(this), Matchers.is(false));
    }

    @Test
    public void should_handle_null_value_correctly() {
        MatcherAssert.assertThat(IsImplicitContextEnumValuePredicate.getInstance().apply(null), Matchers.is(false));
    }

}

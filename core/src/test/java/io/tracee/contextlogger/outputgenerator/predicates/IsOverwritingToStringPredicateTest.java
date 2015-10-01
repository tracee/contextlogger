package io.tracee.contextlogger.outputgenerator.predicates;

import io.tracee.contextlogger.outputgenerator.writer.TestOutputElementWriter;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test class for {@link IsOverwritingToStringPredicate}.
 */
public class IsOverwritingToStringPredicateTest {

	public static class TestClassWithToStringInSuperclass extends IsOverwritingToStringPredicateTest {

	}

	public static class TestClassWithDefaultToString {

	}

	@Test
	public void should_detect_overwritten_tostring_method_correctly() {
		MatcherAssert.assertThat(IsOverwritingToStringPredicate.getInstance().apply(this), Matchers.is(true));
	}

	@Test
	public void should_not_detect_non_overwritten_tostring_method1() {
		MatcherAssert.assertThat(IsOverwritingToStringPredicate.getInstance().apply(new TestOutputElementWriter()), Matchers.is(false));
	}

	@Test
	public void should_not_detect_non_overwritten_tostring_method2() {
		MatcherAssert.assertThat(IsOverwritingToStringPredicate.getInstance().apply(new TestClassWithDefaultToString()), Matchers.is(false));
	}

	@Test
	public void should_detect_non_overwritten_tostring_method_correctly() {
		MatcherAssert.assertThat(IsOverwritingToStringPredicate.getInstance().apply(new TestClassWithToStringInSuperclass()), Matchers.is(true));
	}

	public String toString() {
		return "MURX";
	}

}

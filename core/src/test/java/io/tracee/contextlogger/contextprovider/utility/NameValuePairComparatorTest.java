package io.tracee.contextlogger.contextprovider.utility;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test class for {@link io.tracee.contextlogger.contextprovider.utility.NameValuePairComparator}.
 */
public class NameValuePairComparatorTest {

    private NameValuePairComparator unit = new NameValuePairComparator();

    @Test
    public void should_compare_name_value_pairs_correctly() {

        NameValuePair<String> nvPair1 = new NameValuePair<String>("AAA", "AAA");
        NameValuePair<String> nvPair2 = new NameValuePair<String>("BBB", "BBB");

        MatcherAssert.assertThat(unit.compare(nvPair1, nvPair2), Matchers.is(-1));
        MatcherAssert.assertThat(unit.compare(nvPair2, nvPair1), Matchers.is(1));
        MatcherAssert.assertThat(unit.compare(nvPair1, nvPair1), Matchers.is(0));
        MatcherAssert.assertThat(unit.compare(nvPair1, null), Matchers.is(-1));
        MatcherAssert.assertThat(unit.compare(null, nvPair1), Matchers.is(1));

        MatcherAssert.assertThat(unit.compareNames(null, null), Matchers.is(0));

    }

    @Test
    public void should_compare_names_correctly() {

        MatcherAssert.assertThat(unit.compareNames("AAA", "BBB"), Matchers.is(-1));
        MatcherAssert.assertThat(unit.compareNames("BBB", "AAA"), Matchers.is(1));
        MatcherAssert.assertThat(unit.compareNames("AAA", "AAA"), Matchers.is(0));
        MatcherAssert.assertThat(unit.compareNames("AAA", null), Matchers.is(-1));
        MatcherAssert.assertThat(unit.compareNames(null, "AAA"), Matchers.is(1));

        MatcherAssert.assertThat(unit.compareNames(null, null), Matchers.is(0));

    }

}

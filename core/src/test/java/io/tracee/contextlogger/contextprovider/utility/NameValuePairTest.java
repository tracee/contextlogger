package io.tracee.contextlogger.contextprovider.utility;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test class for {@link io.tracee.contextlogger.contextprovider.utility.NameValuePair}.
 */
public class NameValuePairTest {

    private static final String NAME = "name";
    private static final String VALUE = "value";

    @Test
    public void should_provide_name_value_pairs_correctly() {

        NameValuePair<String> nvpair1 = new NameValuePair<String>(NAME, VALUE);
        MatcherAssert.assertThat(nvpair1.getName(), Matchers.is(NAME));
        MatcherAssert.assertThat(nvpair1.getValue(), Matchers.is(VALUE));

        NameValuePair<String> nvpair2 = new NameValuePair<String>(null, VALUE);
        MatcherAssert.assertThat(nvpair2.getName(), Matchers.is(NameValuePair.DEFAULT_NAME));
        MatcherAssert.assertThat(nvpair2.getValue(), Matchers.is(VALUE));

    }

}

package io.tracee.contextlogger.contextprovider.tracee;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit test for {@link TraceeMessage}.
 */
public class TraceeMessageTest {

    @Test
    public void should_wrap_instance_correctly() {

        MatcherAssert.assertThat(TraceeMessage.wrap(null).getMessage(), Matchers.nullValue());

        MatcherAssert.assertThat(TraceeMessage.wrap(this).getMessage(), Matchers.is((Object)this));

    }
}

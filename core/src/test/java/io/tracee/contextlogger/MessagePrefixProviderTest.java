package io.tracee.contextlogger;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.api.internal.MessageLogLevel;

/**
 * Test class for {@link io.tracee.contextlogger.MessagePrefixProvider}.
 */
public class MessagePrefixProviderTest {

    @Test
    public void provide_prefix_with_info_log_level() {
        MessageLogLevel givenLogLevel = MessageLogLevel.INFO;

        String prefix = MessagePrefixProvider.provideLogMessagePrefix(givenLogLevel, MessagePrefixProviderTest.class);
        MatcherAssert.assertThat(prefix, Matchers.is("TRACEE_CL_" + givenLogLevel.getLevel() + "[MessagePrefixProviderTest]  : "));

    }

    @Test
    public void provide_prefix_with_default_log_level() {
        MessageLogLevel givenLogLevel = null;

        String prefix = MessagePrefixProvider.provideLogMessagePrefix(givenLogLevel, MessagePrefixProviderTest.class);
        MatcherAssert.assertThat(prefix, Matchers.is("TRACEE_CL_" + MessagePrefixProvider.DEFAULT_LEVEL.getLevel() + "[MessagePrefixProviderTest]  : "));

    }

    @Test
    public void provide_prefix_with_default_log_level_and_no_type() {
        MessageLogLevel givenLogLevel = null;

        String prefix = MessagePrefixProvider.provideLogMessagePrefix(givenLogLevel, (Class)null);
        MatcherAssert.assertThat(prefix, Matchers.is("TRACEE_CL_" + MessagePrefixProvider.DEFAULT_LEVEL.getLevel() + "[]  : "));

    }

}

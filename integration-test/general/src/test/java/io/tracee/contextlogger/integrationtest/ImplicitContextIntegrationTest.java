package io.tracee.contextlogger.integrationtest;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.TraceeContextLogger;
import io.tracee.contextlogger.api.ImplicitContext;
import io.tracee.contextlogger.contextprovider.api.Profile;
import io.tracee.contextlogger.outputgenerator.writer.BasicOutputWriterConfiguration;
import io.tracee.contextlogger.util.test.RegexMatcher;

/**
 * Integration test for {@link io.tracee.contextlogger.api.ImplicitContext} wrapping.
 */
public class ImplicitContextIntegrationTest {

    @Test
    public void shouldOutputImplicitContextCorrectly() {
        String result = TraceeContextLogger.create().enforceOutputWriterConfiguration(BasicOutputWriterConfiguration.JSON_INLINE)
                .enforceProfile(Profile.BASIC).apply().toString(ImplicitContext.COMMON, ImplicitContext.TRACEE);

        MatcherAssert.assertThat(result, RegexMatcher.matches("\\[\"TYPE:Object\\[]\",\\{\"TYPE\":\"common\".*"));
    }

    @Test
    public void shouldOutputSingleEmptyImplicitContextCorrectly() {

        String result = TraceeContextLogger.create().enforceOutputWriterConfiguration(BasicOutputWriterConfiguration.JSON_INLINE)
                .enforceProfile(Profile.BASIC).apply().toString(ImplicitContext.TRACEE);

        MatcherAssert.assertThat(result, Matchers.is("{\"TYPE\":\"tracee\",\"DYNAMIC\":null}"));
    }

    @Test
    public void should_write_instance_for_multiple_referenced_instances() {
        String result = TraceeContextLogger.create().enforceProfile(Profile.BASIC).apply().toString("ABC");

        MatcherAssert.assertThat(result, Matchers.is("\"String['ABC']\""));
    }

    @Test
    public void should_write_this_instance_without_tostring_overwrite_correctly() {
        String result = TraceeContextLogger.create().enforceProfile(Profile.BASIC).apply().toString(this);

        MatcherAssert.assertThat(result, Matchers.is("\"ImplicitContextIntegrationTest[]\""));
    }

}

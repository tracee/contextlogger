package io.tracee.contextlogger.integrationtest;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.TraceeContextLogger;
import io.tracee.contextlogger.api.ImplicitContext;
import io.tracee.contextlogger.profile.Profile;

/**
 * Integration test for {@link io.tracee.contextlogger.api.ImplicitContext} wrapping.
 */
public class ImplicitContextIntegrationTest {

    @Test
    public void shouldOutputImplicitContextCorrectly() {
        String result = TraceeContextLogger.create().config().enforceProfile(Profile.BASIC).apply().build()
                .createJson(ImplicitContext.COMMON, ImplicitContext.TRACEE);

        MatcherAssert.assertThat(result, Matchers.is("{\"testImplicitContextData\":{}}"));
    }

    @Test
    public void shouldOutputSingleEmptyImplicitContextCorrectly() {
        String result = TraceeContextLogger.create().config().enforceProfile(Profile.BASIC).apply().build().createJson(ImplicitContext.TRACEE);

        MatcherAssert.assertThat(result, Matchers.is("null"));
    }

    @Test
    public void should_write_instance_for_multiple_referenced_instances() {
        String result = TraceeContextLogger.create().config().enforceProfile(Profile.BASIC).apply().build().createJson("ABC");

        MatcherAssert.assertThat(result, Matchers.is("null"));
    }

    @Test
    public void should_write_this_instance_without_tostring_overwrite_correctly() {
        String result = TraceeContextLogger.create().config().enforceProfile(Profile.BASIC).apply().build().createJson(this);

        MatcherAssert.assertThat(result, Matchers.is("null"));
    }

}

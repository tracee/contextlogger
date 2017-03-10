package io.tracee.contextlogger.integrationtest;

import io.tracee.contextlogger.TraceeContextLogger;
import io.tracee.contextlogger.contextprovider.api.ImplicitContext;
import io.tracee.contextlogger.contextprovider.api.Profile;
import io.tracee.contextlogger.contextprovider.core.CoreImplicitContextProviders;
import io.tracee.contextlogger.integrationtest.testcontextprovider.TestImplicitContextProviders;
import io.tracee.contextlogger.outputgenerator.writer.BasicOutputWriterConfiguration;
import io.tracee.contextlogger.outputgenerator.writer.function.TypeProviderFunction;
import io.tracee.contextlogger.util.test.RegexMatcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.MDC;

/**
 * Integration test for {@link ImplicitContext} wrapping.
 */
public class ImplicitContextIntegrationTest {

    @Before
    public void init() {
        // clean MDC
        MDC.clear();
    }

    @Test
    public void shouldOutputImplicitContextCorrectly() {
        String result = TraceeContextLogger.create().enforceOutputWriterConfiguration(BasicOutputWriterConfiguration.JSON_INLINE)
                .enforceProfile(Profile.BASIC).apply().toString(CoreImplicitContextProviders.COMMON, CoreImplicitContextProviders.TRACEE);

        MatcherAssert.assertThat(result, RegexMatcher.matches("\\[\"" + TypeProviderFunction.OUTPUT_STRING_TYPE + ":Object\\[]\",\\{\"" + TypeProviderFunction.OUTPUT_STRING_TYPE + "\":\"common\".*"));
    }

    @Test
    public void shouldOutputSingleEmptyImplicitContextCorrectly() {


        String result = TraceeContextLogger.create().enforceOutputWriterConfiguration(BasicOutputWriterConfiguration.JSON_INLINE)
                .enforceProfile(Profile.BASIC).apply().toString(CoreImplicitContextProviders.TRACEE);

        MatcherAssert.assertThat(result, Matchers.is("{\"" + TypeProviderFunction.OUTPUT_STRING_TYPE + "\":\"tracee\",\"DYNAMIC\":null}"));
    }

    @Test
    public void should_write_instance_for_multiple_referenced_instances() {
        String result = TraceeContextLogger.create().enforceProfile(Profile.BASIC).apply().toString("ABC");

        MatcherAssert.assertThat(result, Matchers.is("\"String<'ABC'>\""));
    }

    @Test
    public void should_write_this_instance_without_tostring_overwrite_correctly() {
        String result = TraceeContextLogger.create().enforceProfile(Profile.BASIC).apply().toString(this);

        MatcherAssert.assertThat(result, Matchers.is("\"ImplicitContextIntegrationTest<>\""));
    }

    @Test
    public void should_write_test_and_core_implicit_context_providers() {

        String result = TraceeContextLogger.create().enforceProfile(Profile.FULL).apply().toString(CoreImplicitContextProviders.TRACEE, TestImplicitContextProviders.TEST);

        MatcherAssert.assertThat(result, Matchers.containsString("tracee"));
        MatcherAssert.assertThat(result, Matchers.containsString("testImplicitContextData"));
    }

}

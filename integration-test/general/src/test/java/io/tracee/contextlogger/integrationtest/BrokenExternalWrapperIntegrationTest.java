package io.tracee.contextlogger.integrationtest;

import io.tracee.contextlogger.TraceeContextLogger;
import io.tracee.contextlogger.contextprovider.api.Profile;
import io.tracee.contextlogger.integrationtest.testcontextprovider.BrokenImplicitContextProviderThatThrowsNullPointerException;
import io.tracee.contextlogger.integrationtest.testcontextprovider.WrappedBrokenTestContextData;
import io.tracee.contextlogger.outputgenerator.writer.BasicOutputWriterConfiguration;
import io.tracee.contextlogger.outputgenerator.writer.function.TypeProviderFunction;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

public class BrokenExternalWrapperIntegrationTest {

    @Test
    public void should_ignore_properties_for_wrapper_that_throw_an_exception() {

        // should not break because of the thrown NPE - Exception is handled internally ==> getter with exception should be ignored
        // instances with no valid output field will be ignored, so this test has no instances to render and returns "null"
        String result = TraceeContextLogger.create().enforceProfile(Profile.ENHANCED).apply().toString(new WrappedBrokenTestContextData());
        MatcherAssert.assertThat(result, Matchers.is("null"));

    }

    @Test
    public void should_ignore_properties_for_custom_implicit_context_data_provider_that_throw_an_exception() {

        // should not break because of the thrown NPE, Exception is handled internally ==> getter with exception should be ignored
        String result = TraceeContextLogger.create().enforceOutputWriterConfiguration(BasicOutputWriterConfiguration.JSON_INLINE)
                .enforceProfile(Profile.ENHANCED).apply().toString(new BrokenImplicitContextProviderThatThrowsNullPointerException());
        MatcherAssert.assertThat(result, Matchers.is("{\"" + TypeProviderFunction.OUTPUT_STRING_TYPE + "\":\"testBrokenImplicitContextData\",\"workingOutput\":\"String<'IT_WORKS'>\"}"));

    }

}

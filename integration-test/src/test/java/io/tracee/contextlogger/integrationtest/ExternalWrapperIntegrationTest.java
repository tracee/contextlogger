package io.tracee.contextlogger.integrationtest;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.TraceeContextLogger;
import io.tracee.contextlogger.outputgenerator.writer.BasicOutputWriterConfiguration;
import io.tracee.contextlogger.profile.Profile;

public class ExternalWrapperIntegrationTest {

    @Test
    public void should_wrap_with_external_wrappers_correctly_using_basic_profile() {

        String result = TraceeContextLogger.create().enforceProfile(Profile.BASIC).apply().toString(new WrappedTestContextData());

        MatcherAssert.assertThat(result, Matchers.is("null"));

    }

    @Test
    public void should_wrap_with_external_wrappers_correctly_using_enhanced_profile() {

        String result = TraceeContextLogger.create().enforceOutputWriterConfiguration(BasicOutputWriterConfiguration.JSON_INLINE)
                .enforceProfile(Profile.ENHANCED).apply().toString(new WrappedTestContextData());

        MatcherAssert.assertThat(result, Matchers.is("{\"TYPE\":\"testdata\",\"testoutput\":\"String['IT WORKS !!!']\"}"));

    }

    @Test
    public void should_wrap_with_external_wrappers_correctly_using_enhanced_profile_using_manual_override_to_disable_output() {

        String result = TraceeContextLogger.create().enforceProfile(Profile.ENHANCED).disable(TestContextDataWrapper.PROPERTY_NAME).apply()
                .toString(new WrappedTestContextData());

        MatcherAssert.assertThat(result, Matchers.is("null"));

    }

    @Test
    public void should_wrap_with_implicit_context_provider_correctly_using_basic_profile() {

        String result = TraceeContextLogger.create().enforceProfile(Profile.BASIC).apply().toString(new TestImplicitContextDataProvider());

        MatcherAssert.assertThat(result, Matchers.is("null"));

    }

    @Test
    public void should_wrap_with_implicit_context_provider_correctly_using_enhanced_profile() {

        String result = TraceeContextLogger.create().enforceOutputWriterConfiguration(BasicOutputWriterConfiguration.JSON_INLINE)
                .enforceProfile(Profile.ENHANCED).apply().toString(new TestImplicitContextDataProvider());

        MatcherAssert.assertThat(result, Matchers.is("{\"TYPE\":\"testImplicitContextData\",\"output\":\"String['IT WORKS TOO!!!']\"}"));

    }

    @Test
    public void should_wrap_with_implicit_context_provider_correctly_using_enhanced_profile_using_manual_override_to_disable_output() {

        String result = TraceeContextLogger.create().enforceProfile(Profile.ENHANCED).disable(TestImplicitContextDataProvider.PROPERTY_NAME).apply()
                .toString(new TestImplicitContextDataProvider());

        MatcherAssert.assertThat(result, Matchers.is("null"));

    }

}

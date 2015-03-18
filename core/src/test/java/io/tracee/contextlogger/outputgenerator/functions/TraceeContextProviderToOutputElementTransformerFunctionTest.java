package io.tracee.contextlogger.outputgenerator.functions;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.contextprovider.utility.NameValuePair;

/**
 * Unit test for {@link io.tracee.contextlogger.outputgenerator.functions.TraceeContextProviderToOutputElementTransformerFunction}.
 */
public class TraceeContextProviderToOutputElementTransformerFunctionTest {

    @Test
    public void apply_should_return_null_for_instance_without_TraceeContextProvider_annotation() {

    }

    @Test
    public void isNameValuePair_should_detect_NameValuePairCorrectly() {

        MatcherAssert.assertThat(TraceeContextProviderToOutputElementTransformerFunction.isNameValuePair(null), Matchers.is(false));
        MatcherAssert.assertThat(TraceeContextProviderToOutputElementTransformerFunction.isNameValuePair(this), Matchers.is(false));
        MatcherAssert.assertThat(TraceeContextProviderToOutputElementTransformerFunction.isNameValuePair(new NameValuePair<String>("ABC", "DEF")),
                Matchers.is(true));

    }

}

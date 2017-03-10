package io.tracee.contextlogger.outputgenerator.writer.functions;

import io.tracee.contextlogger.contextprovider.core.java.JavaThrowableContextProvider;
import io.tracee.contextlogger.contextprovider.core.utility.NameValuePair;
import io.tracee.contextlogger.outputgenerator.outputelements.CollectionOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.ComplexOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.TraceeContextProviderOutputElement;
import io.tracee.contextlogger.outputgenerator.writer.function.TypeProviderFunction;
import io.tracee.contextlogger.outputgenerator.writer.styles.json.SimpleJsonOutputStyle;
import io.tracee.contextlogger.util.test.RegexMatcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Unit test for {@link io.tracee.contextlogger.outputgenerator.writer.function.TypeProviderFunction}.
 */
public class TypeProviderFunctionTest {

    @Test
    public void should_output_collection_without_id() {

        CollectionOutputElement outputElement = new CollectionOutputElement(ArrayList.class, new ArrayList<String>());

        StringBuilder stringBuilder = new StringBuilder();

        boolean result = TypeProviderFunction.getInstance().apply(stringBuilder, new SimpleJsonOutputStyle(), outputElement);

        MatcherAssert.assertThat(stringBuilder.toString(), Matchers.is("\"" + TypeProviderFunction.OUTPUT_STRING_TYPE + ":ArrayList\""));
        MatcherAssert.assertThat(result, Matchers.is(true));
    }

    @Test
    public void should_output_collection_with_id() {

        CollectionOutputElement outputElement = new CollectionOutputElement(ArrayList.class, new ArrayList<String>());
        outputElement.setIsMarkedAsMultipleReferenced();

        StringBuilder stringBuilder = new StringBuilder();

        boolean result = TypeProviderFunction.getInstance().apply(stringBuilder, new SimpleJsonOutputStyle(), outputElement);

        MatcherAssert.assertThat(stringBuilder.toString(), RegexMatcher.matches("\"" + TypeProviderFunction.OUTPUT_STRING_TYPE + ":ArrayList@\\d+\""));
        MatcherAssert.assertThat(result, Matchers.is(true));
    }

    @Test
    public void should_output_complex_without_id() {

        ComplexOutputElement outputElement = new ComplexOutputElement(HashMap.class, new HashMap<String, String>());

        StringBuilder stringBuilder = new StringBuilder();

        boolean result = TypeProviderFunction.getInstance().apply(stringBuilder, new SimpleJsonOutputStyle(), outputElement);

        MatcherAssert.assertThat(stringBuilder.toString(), Matchers.is("\"" + TypeProviderFunction.OUTPUT_STRING_TYPE + "\":\"HashMap\""));
        MatcherAssert.assertThat(result, Matchers.is(true));
    }

    @Test
    public void should_output_complex_with_id() {

        ComplexOutputElement outputElement = new ComplexOutputElement(HashMap.class, new HashMap<String, String>());
        outputElement.setIsMarkedAsMultipleReferenced();

        StringBuilder stringBuilder = new StringBuilder();

        boolean result = TypeProviderFunction.getInstance().apply(stringBuilder, new SimpleJsonOutputStyle(), outputElement);

        MatcherAssert.assertThat(stringBuilder.toString(), RegexMatcher.matches("\"" + TypeProviderFunction.OUTPUT_STRING_TYPE + "\":\"HashMap@\\d+\""));
        MatcherAssert.assertThat(result, Matchers.is(true));
    }

    @Test
    public void should_output_tracee_without_id() {

        TraceeContextProviderOutputElement outputElement = new TraceeContextProviderOutputElement(JavaThrowableContextProvider.class,
                new JavaThrowableContextProvider(new NullPointerException()));

        StringBuilder stringBuilder = new StringBuilder();

        boolean result = TypeProviderFunction.getInstance().apply(stringBuilder, new SimpleJsonOutputStyle(), outputElement);

        MatcherAssert.assertThat(stringBuilder.toString(), Matchers.is("\"" + TypeProviderFunction.OUTPUT_STRING_TYPE + "\":\"throwable\""));
        MatcherAssert.assertThat(result, Matchers.is(true));
    }

    @Test
    public void should_output_tracee_with_id() {

        TraceeContextProviderOutputElement outputElement = new TraceeContextProviderOutputElement(JavaThrowableContextProvider.class,
                new JavaThrowableContextProvider(new NullPointerException()));

        outputElement.setIsMarkedAsMultipleReferenced();

        StringBuilder stringBuilder = new StringBuilder();

        boolean result = TypeProviderFunction.getInstance().apply(stringBuilder, new SimpleJsonOutputStyle(), outputElement);

        MatcherAssert.assertThat(stringBuilder.toString(), RegexMatcher.matches("\"" + TypeProviderFunction.OUTPUT_STRING_TYPE + "\":\"throwable@\\d+\""));
        MatcherAssert.assertThat(result, Matchers.is(true));
    }

    @Test
    public void should_suppress_annotated_tracee() {

        TraceeContextProviderOutputElement outputElement = new TraceeContextProviderOutputElement(JavaThrowableContextProvider.class,
                new NameValuePair<String>("ABC", "DEF"));

        outputElement.setIsMarkedAsMultipleReferenced();

        StringBuilder stringBuilder = new StringBuilder();

        boolean result = TypeProviderFunction.getInstance().apply(stringBuilder, new SimpleJsonOutputStyle(), outputElement);

        MatcherAssert.assertThat(stringBuilder.toString(), Matchers.is(""));
        MatcherAssert.assertThat(result, Matchers.is(false));

    }

}

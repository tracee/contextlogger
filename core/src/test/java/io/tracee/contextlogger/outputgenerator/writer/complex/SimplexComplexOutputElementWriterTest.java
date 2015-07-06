package io.tracee.contextlogger.outputgenerator.writer.complex;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import io.tracee.contextlogger.contextprovider.core.java.JavaThrowableContextProvider;
import io.tracee.contextlogger.contextprovider.core.utility.NameValuePair;
import io.tracee.contextlogger.outputgenerator.outputelements.AtomicOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.ComplexOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.TraceeContextProviderOutputElement;
import io.tracee.contextlogger.outputgenerator.writer.TestOutputElementWriter;
import io.tracee.contextlogger.outputgenerator.writer.styles.json.SimpleJsonOutputStyle;
import io.tracee.contextlogger.util.test.RegexMatcher;

/**
 * Unit test for {@link io.tracee.contextlogger.outputgenerator.writer.complex.SimpleComplexOutputElementWriter}
 */
public class SimplexComplexOutputElementWriterTest {

    private TestOutputElementWriter outputWriter;
    private SimpleComplexOutputElementWriter unit;

    private String field1 = "ABC";
    private String field2 = "DEF";

    public String getField1() {
        return field1;
    }

    public String getField2() {
        return field2;
    }

    @Before
    public void init() {
        outputWriter = new TestOutputElementWriter();
        unit = new SimpleComplexOutputElementWriter();
    }

    @Test
    public void should_create_output_for_bean_correctly() {

        ComplexOutputElement givenComplexOutputElement = new ComplexOutputElement(this.getClass(), this);
        givenComplexOutputElement.addOutputElement("field1", new AtomicOutputElement(String.class, field1));

        StringBuilder stringBuilder = new StringBuilder();

        unit.produceOutput(outputWriter, stringBuilder, new SimpleJsonOutputStyle(), givenComplexOutputElement);

        MatcherAssert.assertThat(stringBuilder.toString(),
                RegexMatcher.matches("\\{\"TYPE\":\"SimplexComplexOutputElementWriterTest\",\"field1\":(ABC)\\}"));

    }

    @Test
    public void should_create_output_for_bean_with_id_correctly() {

        ComplexOutputElement givenComplexOutputElement = new ComplexOutputElement(this.getClass(), this);
        givenComplexOutputElement.setIsMarkedAsMultipleReferenced();
        givenComplexOutputElement.addOutputElement("field1", new AtomicOutputElement(String.class, field1));

        StringBuilder stringBuilder = new StringBuilder();

        unit.produceOutput(outputWriter, stringBuilder, new SimpleJsonOutputStyle(), givenComplexOutputElement);

        MatcherAssert.assertThat(stringBuilder.toString(),
                RegexMatcher.matches("\\{\"TYPE\":\"SimplexComplexOutputElementWriterTest@\\d+\",\"field1\":(ABC)\\}"));

    }

    @Test
    public void should_create_output_for_tracee_context_provider_correctly() {

        TraceeContextProviderOutputElement givenComplexOutputElement = new TraceeContextProviderOutputElement(this.getClass(),
                new JavaThrowableContextProvider(new NullPointerException()));
        givenComplexOutputElement.addOutputElement("field1", new AtomicOutputElement(String.class, field1));

        StringBuilder stringBuilder = new StringBuilder();

        unit.produceOutput(outputWriter, stringBuilder, new SimpleJsonOutputStyle(), givenComplexOutputElement);

        MatcherAssert.assertThat(stringBuilder.toString(), RegexMatcher.matches("\\{\"TYPE\":\"throwable\",\"field1\":(ABC)\\}"));

    }

    @Test
    public void should_create_output_for_tracee_context_provider_with_id_correctly() {

        TraceeContextProviderOutputElement givenComplexOutputElement = new TraceeContextProviderOutputElement(this.getClass(),
                new JavaThrowableContextProvider(new NullPointerException()));
        givenComplexOutputElement.setIsMarkedAsMultipleReferenced();
        givenComplexOutputElement.addOutputElement("field1", new AtomicOutputElement(String.class, field1));

        StringBuilder stringBuilder = new StringBuilder();

        unit.produceOutput(outputWriter, stringBuilder, new SimpleJsonOutputStyle(), givenComplexOutputElement);

        MatcherAssert.assertThat(stringBuilder.toString(), RegexMatcher.matches("\\{\"TYPE\":\"throwable@\\d+\",\"field1\":(ABC)\\}"));

    }

    @Test
    public void should_create_output_for_tracee_context_provider_with_suppressed_type() {

        TraceeContextProviderOutputElement givenComplexOutputElement = new TraceeContextProviderOutputElement(this.getClass(), new NameValuePair<String>(
                "ABC", "DEF"));
        givenComplexOutputElement.setIsMarkedAsMultipleReferenced();
        givenComplexOutputElement.addOutputElement("field1", new AtomicOutputElement(String.class, field1));

        StringBuilder stringBuilder = new StringBuilder();

        unit.produceOutput(outputWriter, stringBuilder, new SimpleJsonOutputStyle(), givenComplexOutputElement);

        MatcherAssert.assertThat(stringBuilder.toString(), RegexMatcher.matches("\\{\"field1\":(ABC)\\}"));

    }
}

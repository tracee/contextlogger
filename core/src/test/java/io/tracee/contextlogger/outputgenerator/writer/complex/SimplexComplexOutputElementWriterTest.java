package io.tracee.contextlogger.outputgenerator.writer.complex;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import io.tracee.contextlogger.outputgenerator.outputelements.AtomicOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.ComplexOutputElement;
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
        givenComplexOutputElement.addOutputElement("field2", new AtomicOutputElement(String.class, field2));

        StringBuilder stringBuilder = new StringBuilder();

        unit.produceOutput(outputWriter, stringBuilder, new SimpleJsonOutputStyle(), givenComplexOutputElement);

        MatcherAssert.assertThat(stringBuilder.toString(), RegexMatcher.matches("\\{\"field(1|2)\":(ABC|DEF),\"field(1|2)\":(ABC|DEF)\\}"));

    }

}

package io.tracee.contextlogger.outputgenerator.writer.alreadyprocessed;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.outputgenerator.outputelements.AtomicOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;

/**
 * Test class for {@link io.tracee.contextlogger.outputgenerator.writer.alreadyprocessed.SimpleAlreadyProcessedReferenceOutputElementWriter}.
 */
public class SimpleAlreadyProcessedReferenceOutputElementWriterTest {

    private SimpleAlreadyProcessedReferenceOutputElementWriter unit = new SimpleAlreadyProcessedReferenceOutputElementWriter();

    @Test
    public void should_handle_null_valued_output_element_correctly() {
        MatcherAssert.assertThat(unit.produceOutput(null), Matchers.is("<NULL>"));
    }

    @Test
    public void should_handle_output_element_with_null_valued_basetype_correctly() {
        OutputElement outputElement = new AtomicOutputElement(null, null);

        MatcherAssert.assertThat(unit.produceOutput(outputElement), Matchers.is("<NULL>"));
    }

    @Test
    public void should_handle_output_element_with_null_valued_encapsulated_instance_correctly() {
        OutputElement outputElement = new AtomicOutputElement(String.class, null);

        MatcherAssert.assertThat(unit.produceOutput(outputElement), Matchers.is("==> String"));
    }

    @Test
    public void should_handle_output_element_correctly() {
        OutputElement outputElement = new AtomicOutputElement(String.class, "AAA");

        MatcherAssert.assertThat(unit.produceOutput(outputElement), Matchers.is("==> String@" + System.identityHashCode("AAA")));
    }

}

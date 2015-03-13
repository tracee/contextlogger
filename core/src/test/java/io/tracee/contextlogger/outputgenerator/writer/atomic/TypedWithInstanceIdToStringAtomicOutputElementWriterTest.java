package io.tracee.contextlogger.outputgenerator.writer.atomic;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.outputgenerator.outputelements.AtomicOutputElement;

/**
 * Unit test for {@link io.tracee.contextlogger.outputgenerator.writer.atomic.TypedWithInstanceIdToStringAtomicOutputElementWriter}.
 */
public class TypedWithInstanceIdToStringAtomicOutputElementWriterTest {

    @Test
    public void should_produce_output_correctly() {

        final String value = "ABC";

        AtomicOutputElement atomicOutputElement = new AtomicOutputElement(String.class, value);

        String result = new TypedWithInstanceIdToStringAtomicOutputElementWriter().produceOutput(atomicOutputElement);

        MatcherAssert.assertThat(result, Matchers.is("String@" + System.identityHashCode(value) + "['" + value + "']"));

    }

    @Test
    public void should_produce_output_for_null_value_correctly() {

        final String value = null;

        AtomicOutputElement atomicOutputElement = new AtomicOutputElement(String.class, value);

        String result = new TypedWithInstanceIdToStringAtomicOutputElementWriter().produceOutput(atomicOutputElement);

        MatcherAssert.assertThat(result, Matchers.is("String['<NULL>']"));

    }

    @Test
    public void should_produce_output_for_null_valued_type_correctly() {

        final String value = null;

        AtomicOutputElement atomicOutputElement = new AtomicOutputElement(null, value);

        String result = new TypedWithInstanceIdToStringAtomicOutputElementWriter().produceOutput(atomicOutputElement);

        MatcherAssert.assertThat(result, Matchers.is("<NULL>"));

    }

}

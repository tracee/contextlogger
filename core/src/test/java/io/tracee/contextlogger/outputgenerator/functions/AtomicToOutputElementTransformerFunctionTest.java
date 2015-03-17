package io.tracee.contextlogger.outputgenerator.functions;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.outputgenerator.RecursiveOutputElementTreeBuilder;
import io.tracee.contextlogger.outputgenerator.RecursiveOutputElementTreeBuilderState;
import io.tracee.contextlogger.outputgenerator.outputelements.AtomicOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.NullValueOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;

/**
 * Unit test for {@link io.tracee.contextlogger.outputgenerator.functions.AtomicToOutputElementTransformerFunction}.
 */
public class AtomicToOutputElementTransformerFunctionTest {

    @Test
    public void should_return_NullValueOutputElement_for_null_vallued_instance_parameter() {

        OutputElement outputElement = AtomicToOutputElementTransformerFunction.getInstance().apply(new ToStringRecursiveContext(),
                new RecursiveOutputElementTreeBuilderState(), null);

        MatcherAssert.assertThat(outputElement, Matchers.is((OutputElement)NullValueOutputElement.INSTANCE));

    }

    @Test
    public void should_register_and_return_AtomicOutputElement_for_unregistered_instamce() {

        // get the output element for the first time. OutputElement must not be marked as multiple referenced
        RecursiveOutputElementTreeBuilder recursiveOutputElementTreeBuilder = new ToStringRecursiveContext();
        AtomicOutputElement outputElement = (AtomicOutputElement)AtomicToOutputElementTransformerFunction.getInstance().apply(
                recursiveOutputElementTreeBuilder, new RecursiveOutputElementTreeBuilderState(), "ABC");

        MatcherAssert.assertThat(outputElement, Matchers.instanceOf(AtomicOutputElement.class));
        MatcherAssert.assertThat(recursiveOutputElementTreeBuilder.checkIfInstanceIsAlreadyRegistered(new AtomicOutputElement(String.class, "ABC")),
                Matchers.is(true));
        MatcherAssert.assertThat(outputElement.getIsAsMarkedAsMultipleReferenced(), Matchers.is(false));

        // now add the output element for a second time - OutputElement must be marked as multiple referenced
        OutputElement markedAsRegisteredOutputElement = (AtomicOutputElement)AtomicToOutputElementTransformerFunction.getInstance().apply(
                recursiveOutputElementTreeBuilder, new RecursiveOutputElementTreeBuilderState(), "ABC");
        MatcherAssert.assertThat(markedAsRegisteredOutputElement.getIsAsMarkedAsMultipleReferenced(), Matchers.is(true));

    }
}

package io.tracee.contextlogger.outputgenerator;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.tracee.contextlogger.api.TraceeContextStringRepresentationBuilder;
import io.tracee.contextlogger.outputgenerator.outputelements.AtomicOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.CollectionOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.NullValueOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;
import io.tracee.contextlogger.profile.ProfileSettings;

/**
 * Test class for {@link io.tracee.contextlogger.outputgenerator.RootOutputElementTreeBuilder}.
 */
public class RootOutputElementTreeBuilderTest {

    private RootOutputElementTreeBuilder unit;
    private RecursiveOutputElementTreeBuilder recursiveOutputElementTreeBuilder;

    @Before
    public void init() {
        recursiveOutputElementTreeBuilder = Mockito.mock(RecursiveOutputElementTreeBuilder.class);
        unit = new RootOutputElementTreeBuilder(recursiveOutputElementTreeBuilder);
    }

    @Test
    public void should_handle_zero_instances_correctly() {

        OutputElement outputElement = unit.buildOutputElementTreeMain();
        MatcherAssert.assertThat(outputElement, Matchers.is((OutputElement)NullValueOutputElement.INSTANCE));

        outputElement = unit.buildOutputElementTreeMain(new Object[0]);
        MatcherAssert.assertThat(outputElement, Matchers.is((OutputElement)NullValueOutputElement.INSTANCE));
        Mockito.verify(recursiveOutputElementTreeBuilder, Mockito.never()).convertInstanceRecursively(
                Mockito.any(RecursiveOutputElementTreeBuilderState.class), Mockito.any(Object.class));

    }

    @Test
    public void should_handle_single_instance_correctly() {
        AtomicOutputElement atomicOutputElement = new AtomicOutputElement(String.class, "ABC");
        Mockito.when(
                recursiveOutputElementTreeBuilder.convertInstanceRecursively(Mockito.any(RecursiveOutputElementTreeBuilderState.class), Mockito.eq("ABC")))
                .thenReturn(atomicOutputElement);

        OutputElement outputElement = unit.buildOutputElementTreeMain("ABC");
        MatcherAssert.assertThat(outputElement, Matchers.instanceOf(AtomicOutputElement.class));
        MatcherAssert.assertThat(outputElement, Matchers.is((OutputElement)atomicOutputElement));

        Mockito.verify(recursiveOutputElementTreeBuilder, Mockito.times(1)).convertInstanceRecursively(
                Mockito.any(RecursiveOutputElementTreeBuilderState.class), Mockito.eq("ABC"));
    }

    @Test
    public void should_handle_single_instance_correctly_if_recursive_output_tree_builder_returns_null() {
        AtomicOutputElement atomicOutputElement = null;
        Mockito.when(
                recursiveOutputElementTreeBuilder.convertInstanceRecursively(Mockito.any(RecursiveOutputElementTreeBuilderState.class), Mockito.eq("ABC")))
                .thenReturn(atomicOutputElement);

        OutputElement outputElement = unit.buildOutputElementTreeMain("ABC");
        MatcherAssert.assertThat(outputElement, Matchers.is((OutputElement)NullValueOutputElement.INSTANCE));

        Mockito.verify(recursiveOutputElementTreeBuilder, Mockito.times(1)).convertInstanceRecursively(
                Mockito.any(RecursiveOutputElementTreeBuilderState.class), Mockito.eq("ABC"));
    }

    @Test
    public void should_handle_multiple_instances_correctly() {
        AtomicOutputElement atomicOutputElement1 = new AtomicOutputElement(String.class, "ABC");
        ProfileSettings profileSettings = Mockito.mock(ProfileSettings.class);
        TraceeContextStringRepresentationBuilder traceeContextStringRepresentationBuilder = Mockito.mock(TraceeContextStringRepresentationBuilder.class);

        AtomicOutputElement atomicOutputElement2 = null;
        Mockito.when(
                recursiveOutputElementTreeBuilder.convertInstanceRecursively(Mockito.any(RecursiveOutputElementTreeBuilderState.class), Mockito.eq("ABC")))
                .thenReturn(atomicOutputElement1);
        Mockito.when(
                recursiveOutputElementTreeBuilder.convertInstanceRecursively(Mockito.any(RecursiveOutputElementTreeBuilderState.class), Mockito.eq("DEF")))
                .thenReturn(atomicOutputElement2);
        Mockito.when(recursiveOutputElementTreeBuilder.getProfileSettings()).thenReturn(profileSettings);
        Mockito.when(profileSettings.getToTraceeContextStringRepresentationBuilder()).thenReturn(traceeContextStringRepresentationBuilder);
        Mockito.when(traceeContextStringRepresentationBuilder.getEnforceOrder()).thenReturn(false);

        CollectionOutputElement outputElement = (CollectionOutputElement)unit.buildOutputElementTreeMain("ABC", "DEF");
        MatcherAssert.assertThat(outputElement.getOutputElements(), Matchers.hasSize(1));
        MatcherAssert.assertThat(outputElement.getOutputElements().get(0), Matchers.is((OutputElement)atomicOutputElement1));

        Mockito.verify(recursiveOutputElementTreeBuilder, Mockito.times(1)).convertInstanceRecursively(
                Mockito.any(RecursiveOutputElementTreeBuilderState.class), Mockito.eq("ABC"));
        Mockito.verify(recursiveOutputElementTreeBuilder, Mockito.times(1)).convertInstanceRecursively(
                Mockito.any(RecursiveOutputElementTreeBuilderState.class), Mockito.eq("DEF"));
    }
}

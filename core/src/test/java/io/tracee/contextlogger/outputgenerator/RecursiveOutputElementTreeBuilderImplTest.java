package io.tracee.contextlogger.outputgenerator;

import java.util.ArrayList;
import java.util.HashMap;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.tracee.contextlogger.contextprovider.core.java.JavaThrowableContextProvider;
import io.tracee.contextlogger.impl.ContextLoggerConfiguration;
import io.tracee.contextlogger.outputgenerator.outputelements.*;
import io.tracee.contextlogger.profile.ProfileSettings;
import io.tracee.contextlogger.testdata.TestBeanClass;

/**
 * Test class for {@link io.tracee.contextlogger.outputgenerator.RecursiveOutputElementTreeBuilder}.
 */
public class RecursiveOutputElementTreeBuilderImplTest {

    private InstanceToOutputElementPool instanceToOutputElementPool;
    private ProfileSettings profileSettings;
    private ContextLoggerConfiguration contextLoggerConfiguration;
    private RecursiveOutputElementTreeBuilderState recursiveOutputElementTreeBuilderState;
    private RecursiveOutputElementTreeBuilderImpl unit;

    @Before
    public void init() {
        instanceToOutputElementPool = Mockito.mock(InstanceToOutputElementPool.class);
        profileSettings = Mockito.mock(ProfileSettings.class);
        contextLoggerConfiguration = Mockito.mock(ContextLoggerConfiguration.class);
        recursiveOutputElementTreeBuilderState = Mockito.mock(RecursiveOutputElementTreeBuilderState.class);

        unit = new RecursiveOutputElementTreeBuilderImpl(profileSettings, instanceToOutputElementPool, contextLoggerConfiguration);

        Mockito.when(recursiveOutputElementTreeBuilderState.maxDepthReached()).thenReturn(false);
        Mockito.when(recursiveOutputElementTreeBuilderState.next()).thenReturn(recursiveOutputElementTreeBuilderState);
        Mockito.when(profileSettings.getPropertyValue(Mockito.anyString())).thenReturn(Boolean.TRUE);
    }

    @Test
    public void should_return_null_value_output_element_for_null_valued_instance_parameter() {

        OutputElement outputElement = unit.convertInstanceRecursively(recursiveOutputElementTreeBuilderState, null);

        MatcherAssert.assertThat(outputElement, Matchers.is((OutputElement)NullValueOutputElement.INSTANCE));
        MatcherAssert.assertThat(outputElement.getOutputElementType(), Matchers.is(OutputElementType.NULL));

    }

    @Test
    public void should_return_atomic_output_element_if_max_depth_is_reached() {

        Mockito.when(recursiveOutputElementTreeBuilderState.maxDepthReached()).thenReturn(true);

        OutputElement outputElement = unit.convertInstanceRecursively(recursiveOutputElementTreeBuilderState, this);

        MatcherAssert.assertThat(outputElement, Matchers.instanceOf(AtomicOutputElement.class));
        MatcherAssert.assertThat(outputElement.getEncapsulatedInstance(), Matchers.is((Object)this));
        MatcherAssert.assertThat(outputElement.getOutputElementType(), Matchers.is(OutputElementType.ATOMIC));

    }

    @Test
    public void should_return_atomic_output_element() {

        OutputElement outputElement = unit.convertInstanceRecursively(recursiveOutputElementTreeBuilderState, this);

        MatcherAssert.assertThat(outputElement, Matchers.instanceOf(AtomicOutputElement.class));
        MatcherAssert.assertThat(outputElement.getEncapsulatedInstance(), Matchers.is((Object)this));

        MatcherAssert.assertThat(outputElement.getOutputElementType(), Matchers.is(OutputElementType.ATOMIC));

    }

    @Test
    public void should_return_collection_output_element_for_list() {

        Object givenInstance = new ArrayList<String>();

        OutputElement outputElement = unit.convertInstanceRecursively(recursiveOutputElementTreeBuilderState, givenInstance);

        MatcherAssert.assertThat(outputElement, Matchers.instanceOf(CollectionOutputElement.class));
        MatcherAssert.assertThat(outputElement.getEncapsulatedInstance(), Matchers.is(givenInstance));
        MatcherAssert.assertThat(outputElement.getOutputElementType(), Matchers.is(OutputElementType.COLLECTION));

    }

    @Test
    public void should_return_collection_output_element_for_array() {

        Object givenInstance = new String[5];

        OutputElement outputElement = unit.convertInstanceRecursively(recursiveOutputElementTreeBuilderState, givenInstance);

        MatcherAssert.assertThat(outputElement, Matchers.instanceOf(CollectionOutputElement.class));
        MatcherAssert.assertThat(outputElement.getEncapsulatedInstance(), Matchers.is(givenInstance));

        MatcherAssert.assertThat(outputElement.getOutputElementType(), Matchers.is(OutputElementType.COLLECTION));

    }

    @Test
    public void should_return_collection_output_element_for_map() {

        Object givenInstance = new HashMap<String, String>();

        OutputElement outputElement = unit.convertInstanceRecursively(recursiveOutputElementTreeBuilderState, givenInstance);

        MatcherAssert.assertThat(outputElement, Matchers.instanceOf(ComplexOutputElement.class));
        MatcherAssert.assertThat(outputElement.getEncapsulatedInstance(), Matchers.is(givenInstance));

        MatcherAssert.assertThat(outputElement.getOutputElementType(), Matchers.is(OutputElementType.COMPLEX));

    }

    @Test
    public void should_return_complex_output_element_for_bean() {

        Object givenInstance = new TestBeanClass();

        OutputElement outputElement = unit.convertInstanceRecursively(recursiveOutputElementTreeBuilderState, givenInstance);

        MatcherAssert.assertThat(outputElement, Matchers.instanceOf(ComplexOutputElement.class));
        MatcherAssert.assertThat(outputElement.getEncapsulatedInstance(), Matchers.is(givenInstance));

        MatcherAssert.assertThat(outputElement.getOutputElementType(), Matchers.is(OutputElementType.COMPLEX));

    }

    @Test
    public void should_return_complex_output_element_for_tracee_context_provider() {

        Object givenInstance = new JavaThrowableContextProvider(new NullPointerException());

        OutputElement outputElement = new RecursiveOutputElementTreeBuilderImpl(profileSettings, instanceToOutputElementPool,
                ContextLoggerConfiguration.getOrCreateContextLoggerConfiguration()).convertInstanceRecursively(recursiveOutputElementTreeBuilderState,
                givenInstance);

        MatcherAssert.assertThat(outputElement, Matchers.instanceOf(ComplexOutputElement.class));
        MatcherAssert.assertThat(outputElement.getEncapsulatedInstance(), Matchers.is(givenInstance));

        MatcherAssert.assertThat(outputElement.getOutputElementType(), Matchers.is(OutputElementType.COMPLEX));

    }

}

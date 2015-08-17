package io.tracee.contextlogger.outputgenerator.functions;

import io.tracee.contextlogger.contextprovider.api.Flatten;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderMethod;
import io.tracee.contextlogger.contextprovider.core.utility.NameValuePair;
import io.tracee.contextlogger.outputgenerator.RecursiveOutputElementTreeBuilder;
import io.tracee.contextlogger.outputgenerator.RecursiveOutputElementTreeBuilderState;
import io.tracee.contextlogger.outputgenerator.outputelements.NullValueOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.TraceeContextProviderOutputElement;
import io.tracee.contextlogger.profile.ProfileSettings;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link io.tracee.contextlogger.outputgenerator.functions.TraceeContextProviderToOutputElementTransformerFunction}.
 */
public class TraceeContextProviderToOutputElementTransformerFunctionTest {

	private RecursiveOutputElementTreeBuilderState recursiveOutputElementTreeBuilderState;

	private RecursiveOutputElementTreeBuilder recursiveOutputElementTreeBuilder;

	@TraceeContextProvider(displayName = "TEST")
	public static class TestClass {

		@Flatten
		@TraceeContextProviderMethod(displayName = "TM_1")
		public NameValuePair<String> testMethod1() {
			return new NameValuePair<String>("NAME_1", "VALUE_1");
		}

		@Flatten
		@TraceeContextProviderMethod(displayName = "TM_2")
		public List<NameValuePair<String>> testMethod2() {

			List<NameValuePair<String>> result = new ArrayList<NameValuePair<String>>();
			result.add(new NameValuePair<String>("NAME_2", "VALUE_2"));

			return result;

		}

		@TraceeContextProviderMethod(displayName = "TM_3")
		public String testMethod3() {
			return "TM_3";
		}

		@TraceeContextProviderMethod(displayName = "TM_4")
		public String testMethod4() {
			return "TM_4";
		}
	}


	@Before
	public void init() {
		recursiveOutputElementTreeBuilderState = mock(RecursiveOutputElementTreeBuilderState.class);
		recursiveOutputElementTreeBuilder = mock(RecursiveOutputElementTreeBuilder.class);
	}


	@Test
	public void apply_should_return_null_for_instance_without_TraceeContextProvider_annotation() {

		OutputElement outputElement = TraceeContextProviderToOutputElementTransformerFunction.getInstance().apply(recursiveOutputElementTreeBuilder, recursiveOutputElementTreeBuilderState, this);

		assertThat(outputElement.getClass(), Matchers.typeCompatibleWith(NullValueOutputElement.class));

	}

	@Test
	public void apply_should_process_passed_instance_correctly() {

		TestClass testInstance = new TestClass();
		ProfileSettings profileSettings = mock(ProfileSettings.class);

		when(recursiveOutputElementTreeBuilder.getProfileSettings()).thenReturn(profileSettings);
		when(profileSettings.getPropertyValue(eq(TraceeContextProviderToOutputElementTransformerFunctionTest.class.getCanonicalName() + ".TestClass.testMethod1"))).thenReturn(true);
		when(profileSettings.getPropertyValue(eq(TraceeContextProviderToOutputElementTransformerFunctionTest.class.getCanonicalName() + ".TestClass.testMethod2"))).thenReturn(true);
		when(profileSettings.getPropertyValue(eq(TraceeContextProviderToOutputElementTransformerFunctionTest.class.getCanonicalName() + ".TestClass.testMethod3"))).thenReturn(true);
		when(profileSettings.getPropertyValue(eq(TraceeContextProviderToOutputElementTransformerFunctionTest.class.getCanonicalName() + ".TestClass.testMethod4"))).thenReturn(false);

		TraceeContextProviderToOutputElementTransformerFunction unit = spy(new TraceeContextProviderToOutputElementTransformerFunction());
		doNothing().when(unit).addChildToComplexOutputElement(eq(recursiveOutputElementTreeBuilder), eq(recursiveOutputElementTreeBuilderState), any(TraceeContextProviderOutputElement.class), anyString(), anyString());

		OutputElement outputElement = unit.apply(recursiveOutputElementTreeBuilder, recursiveOutputElementTreeBuilderState, testInstance);

		verify(unit).addChildToComplexOutputElement(eq(recursiveOutputElementTreeBuilder), eq(recursiveOutputElementTreeBuilderState), any(TraceeContextProviderOutputElement.class), eq("NAME_1"), eq("VALUE_1"));
		verify(unit).addChildToComplexOutputElement(eq(recursiveOutputElementTreeBuilder), eq(recursiveOutputElementTreeBuilderState), any(TraceeContextProviderOutputElement.class), eq("NAME_2"), eq("VALUE_2"));
		verify(unit).addChildToComplexOutputElement(eq(recursiveOutputElementTreeBuilder), eq(recursiveOutputElementTreeBuilderState), any(TraceeContextProviderOutputElement.class), eq("TM_3"), eq("TM_3"));
		verify(unit, never()).addChildToComplexOutputElement(eq(recursiveOutputElementTreeBuilder), eq(recursiveOutputElementTreeBuilderState), any(TraceeContextProviderOutputElement.class), eq("TM_4"), eq("TM_4"));


	}


	@Test
	public void isNameValuePair_should_detect_NameValuePairCorrectly() {

		assertThat(TraceeContextProviderToOutputElementTransformerFunction.isNameValuePair(null), Matchers.is(false));
		assertThat(TraceeContextProviderToOutputElementTransformerFunction.isNameValuePair(this), Matchers.is(false));
		assertThat(TraceeContextProviderToOutputElementTransformerFunction.isNameValuePair(new NameValuePair<String>("ABC", "DEF")),
				Matchers.is(true));

	}


}

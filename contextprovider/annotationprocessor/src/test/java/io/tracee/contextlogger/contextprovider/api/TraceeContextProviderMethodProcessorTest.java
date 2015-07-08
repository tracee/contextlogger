package io.tracee.contextlogger.contextprovider.api;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link TraceeContextProviderMethodProcessor}.
 */
public class TraceeContextProviderMethodProcessorTest {

	private TraceeContextProviderMethodProcessor unit = new TraceeContextProviderMethodProcessor();

	private RoundEnvironment roundEnvironment;

	private ProcessingEnvironment processingEnvironment;

	private Messager messager;

	private Set<? extends TypeElement> annotationSet;


	@Before
	public void init() {
		roundEnvironment = mock(RoundEnvironment.class);
		processingEnvironment = mock(ProcessingEnvironment.class);
		messager = mock(Messager.class);

		when(processingEnvironment.getMessager()).thenReturn(messager);

		unit.init(processingEnvironment);
	}


	@Test
	public void process_validAnnotatedMethod() throws IOException {

		TraceeContextProviderMethodProcessor spy = Mockito.spy(unit);
		ExecutableElement element = mock(ExecutableElement.class);

		// add parent to mock
		TypeElement parentElement = mock(TypeElement.class);
		when(element.getEnclosingElement()).thenReturn(parentElement);

		// prepare profile settings
		ProfileSettings profileSettings = mock(ProfileSettings.class);
		when(profileSettings.basic()).thenReturn(true);
		when(profileSettings.enhanced()).thenReturn(true);
		when(profileSettings.full()).thenReturn(true);
		when(element.getAnnotation(ProfileSettings.class)).thenReturn(profileSettings);

		// provide elements annotated with Flatten annotation
		Set<Element> set = new HashSet<Element>();
		set.add(element);
		when(roundEnvironment.getElementsAnnotatedWith(TraceeContextProviderMethod.class)).thenReturn((Set) set);

		// mock internal functions
		doReturn(true).when(spy).isValidMethod(element);
		doReturn(true).when(spy).isGetterMethod(element);
		doReturn(true).when(spy).isParentAnnotatedWithTraceeContextProvider(element);

		// prevent writing to configuration file
		Mockito.doNothing().when(spy).writeToPropertyFile(any(Profile.class), any(TypeElement.class), any(Element.class), any(Boolean.class));

		// execute, assert and validate
		assertThat(spy.process(annotationSet, roundEnvironment), Matchers.is(false));

		verify(spy).isValidMethod(element);
		verify(spy).isGetterMethod(element);
		verify(spy).isParentAnnotatedWithTraceeContextProvider(element);

		verify(spy).writeToPropertyFile(Profile.BASIC, parentElement, element, true);
		verify(spy).writeToPropertyFile(Profile.ENHANCED, parentElement, element, true);
		verify(spy).writeToPropertyFile(Profile.FULL, parentElement, element, true);

		verify(spy, never()).error(eq(element), anyString(), anyString(), anyString(), anyString());


	}

	@Test
	public void process_missingProfileSettingsAnnotationCorrectly() throws IOException {

		TraceeContextProviderMethodProcessor spy = Mockito.spy(unit);
		ExecutableElement element = mock(ExecutableElement.class);

		// add parent to mock
		TypeElement parentElement = mock(TypeElement.class);
		when(element.getEnclosingElement()).thenReturn(parentElement);

		// prepare profile settings
		ProfileSettings profileSettings = null;
		when(element.getAnnotation(ProfileSettings.class)).thenReturn(profileSettings);

		// provide elements annotated with Flatten annotation
		Set<Element> set = new HashSet<Element>();
		set.add(element);
		when(roundEnvironment.getElementsAnnotatedWith(TraceeContextProviderMethod.class)).thenReturn((Set) set);

		// mock internal functions
		doReturn(true).when(spy).isValidMethod(element);
		doReturn(true).when(spy).isGetterMethod(element);
		doReturn(true).when(spy).isParentAnnotatedWithTraceeContextProvider(element);

		// prevent writing to configuration file
		Mockito.doNothing().when(spy).writeToPropertyFile(any(Profile.class), any(TypeElement.class), any(Element.class), any(Boolean.class));

		// execute, assert and validate
		assertThat(spy.process(annotationSet, roundEnvironment), Matchers.is(false));

		verify(spy).isValidMethod(element);
		verify(spy).isGetterMethod(element);
		verify(spy).isParentAnnotatedWithTraceeContextProvider(element);

		verify(spy).writeToPropertyFile(Profile.BASIC, parentElement, element, false);
		verify(spy).writeToPropertyFile(Profile.ENHANCED, parentElement, element, false);
		verify(spy).writeToPropertyFile(Profile.FULL, parentElement, element, true);

		verify(spy, never()).error(eq(element), anyString(), anyString(), anyString(), anyString());


	}


	@Test
	public void process_skipBecauseOfMissingTraceeContextProviderAnnotation() throws IOException {

		TraceeContextProviderMethodProcessor spy = Mockito.spy(unit);
		ExecutableElement element = mock(ExecutableElement.class);

		// add parent to mock
		TypeElement parentElement = mock(TypeElement.class);
		when(element.getEnclosingElement()).thenReturn(parentElement);

		// prepare profile settings
		ProfileSettings profileSettings = mock(ProfileSettings.class);
		when(profileSettings.basic()).thenReturn(true);
		when(profileSettings.enhanced()).thenReturn(true);
		when(profileSettings.full()).thenReturn(true);
		when(element.getAnnotation(ProfileSettings.class)).thenReturn(profileSettings);

		// provide elements annotated with Flatten annotation
		Set<Element> set = new HashSet<Element>();
		set.add(element);
		when(roundEnvironment.getElementsAnnotatedWith(TraceeContextProviderMethod.class)).thenReturn((Set) set);

		// mock internal functions
		doReturn(true).when(spy).isValidMethod(element);
		doReturn(true).when(spy).isGetterMethod(element);
		doReturn(false).when(spy).isParentAnnotatedWithTraceeContextProvider(element);

		// prevent writing to configuration file
		Mockito.doNothing().when(spy).writeToPropertyFile(any(Profile.class), any(TypeElement.class), any(Element.class), any(Boolean.class));

		// execute, assert and validate
		assertThat(spy.process(annotationSet, roundEnvironment), Matchers.is(false));

		verify(spy).isValidMethod(element);
		verify(spy).isGetterMethod(element);
		verify(spy).isParentAnnotatedWithTraceeContextProvider(element);

		verify(spy, never()).writeToPropertyFile(Profile.BASIC, parentElement, element, true);
		verify(spy, never()).writeToPropertyFile(Profile.ENHANCED, parentElement, element, true);
		verify(spy, never()).writeToPropertyFile(Profile.FULL, parentElement, element, true);

		verify(spy, never()).error(eq(element), anyString(), anyString(), anyString(), anyString());


	}

	@Test
	public void process_skipBecauseOfIsNoGetter() throws IOException {

		TraceeContextProviderMethodProcessor spy = Mockito.spy(unit);
		ExecutableElement element = mock(ExecutableElement.class);

		// add parent to mock
		TypeElement parentElement = mock(TypeElement.class);
		when(element.getEnclosingElement()).thenReturn(parentElement);

		// prepare profile settings
		ProfileSettings profileSettings = mock(ProfileSettings.class);
		when(profileSettings.basic()).thenReturn(true);
		when(profileSettings.enhanced()).thenReturn(true);
		when(profileSettings.full()).thenReturn(true);
		when(element.getAnnotation(ProfileSettings.class)).thenReturn(profileSettings);

		// provide elements annotated with Flatten annotation
		Set<Element> set = new HashSet<Element>();
		set.add(element);
		when(roundEnvironment.getElementsAnnotatedWith(TraceeContextProviderMethod.class)).thenReturn((Set) set);

		// mock internal functions
		doReturn(true).when(spy).isValidMethod(element);
		doReturn(false).when(spy).isGetterMethod(element);
		doReturn(true).when(spy).isParentAnnotatedWithTraceeContextProvider(element);

		// prevent writing to configuration file
		Mockito.doNothing().when(spy).writeToPropertyFile(any(Profile.class), any(TypeElement.class), any(Element.class), any(Boolean.class));

		// execute, assert and validate
		assertThat(spy.process(annotationSet, roundEnvironment), Matchers.is(false));

		verify(spy).isValidMethod(element);
		verify(spy).isGetterMethod(element);
		verify(spy, never()).isParentAnnotatedWithTraceeContextProvider(element);

		verify(spy, never()).writeToPropertyFile(Profile.BASIC, parentElement, element, true);
		verify(spy, never()).writeToPropertyFile(Profile.ENHANCED, parentElement, element, true);
		verify(spy, never()).writeToPropertyFile(Profile.FULL, parentElement, element, true);

		verify(spy, never()).error(eq(element), anyString(), anyString(), anyString(), anyString());


	}

	@Test
	public void process_skipBecauseOfInvalidMethod() throws IOException {

		TraceeContextProviderMethodProcessor spy = Mockito.spy(unit);
		ExecutableElement element = mock(ExecutableElement.class);

		// add parent to mock
		TypeElement parentElement = mock(TypeElement.class);
		when(element.getEnclosingElement()).thenReturn(parentElement);

		// prepare profile settings
		ProfileSettings profileSettings = mock(ProfileSettings.class);
		when(profileSettings.basic()).thenReturn(true);
		when(profileSettings.enhanced()).thenReturn(true);
		when(profileSettings.full()).thenReturn(true);
		when(element.getAnnotation(ProfileSettings.class)).thenReturn(profileSettings);

		// provide elements annotated with Flatten annotation
		Set<Element> set = new HashSet<Element>();
		set.add(element);
		when(roundEnvironment.getElementsAnnotatedWith(TraceeContextProviderMethod.class)).thenReturn((Set) set);

		// mock internal functions
		doReturn(false).when(spy).isValidMethod(element);
		doReturn(true).when(spy).isGetterMethod(element);
		doReturn(true).when(spy).isParentAnnotatedWithTraceeContextProvider(element);

		// prevent writing to configuration file
		Mockito.doNothing().when(spy).writeToPropertyFile(any(Profile.class), any(TypeElement.class), any(Element.class), any(Boolean.class));

		// execute, assert and validate
		assertThat(spy.process(annotationSet, roundEnvironment), Matchers.is(false));

		verify(spy).isValidMethod(element);
		verify(spy, never()).isGetterMethod(element);
		verify(spy, never()).isParentAnnotatedWithTraceeContextProvider(element);

		verify(spy, never()).writeToPropertyFile(Profile.BASIC, parentElement, element, true);
		verify(spy, never()).writeToPropertyFile(Profile.ENHANCED, parentElement, element, true);
		verify(spy, never()).writeToPropertyFile(Profile.FULL, parentElement, element, true);

		verify(spy, never()).error(eq(element), anyString(), anyString(), anyString(), anyString());


	}


}

package io.tracee.contextlogger.contextprovider.api;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link TraceeContextProviderProcessor}
 */
public class TraceeContextProviderProcessorTest {

	private TraceeContextProviderProcessor unit = new TraceeContextProviderProcessor();

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
	public void checkIfClassHasNoargsConstructor_WithNoConstructor() {

		TypeElement typeElement = mock(TypeElement.class);

		List<Element> childElements = new ArrayList<Element>();

		TypeElement child1 = mock(TypeElement.class);
		when(child1.getKind()).thenReturn(ElementKind.METHOD);
		childElements.add(child1);

		when(typeElement.getEnclosedElements()).thenReturn((List) childElements);

		assertThat(unit.checkIfClassHasNoargsConstructor(typeElement), is(true));


	}

	@Test
	public void checkIfClassHasNoargsConstructor_WithNoArgConstructor() {

		TypeElement typeElement = mock(TypeElement.class);

		List<Element> childElements = new ArrayList<Element>();

		ExecutableElement child1 = mock(ExecutableElement.class);
		when(child1.getKind()).thenReturn(ElementKind.CONSTRUCTOR);
		when(child1.getParameters()).thenReturn(new ArrayList());
		childElements.add(child1);

		when(typeElement.getEnclosedElements()).thenReturn((List) childElements);

		assertThat(unit.checkIfClassHasNoargsConstructor(typeElement), is(true));


	}

	@Test
	public void checkIfClassHasNoargsConstructor_WithMultipleConstructors() {

		TypeElement typeElement = mock(TypeElement.class);

		List<Element> childElements = new ArrayList<Element>();

		ExecutableElement child1 = mock(ExecutableElement.class);
		when(child1.getKind()).thenReturn(ElementKind.CONSTRUCTOR);
		when(child1.getParameters()).thenReturn(new ArrayList());
		childElements.add(child1);

		ExecutableElement child2 = mock(ExecutableElement.class);
		when(child2.getKind()).thenReturn(ElementKind.CONSTRUCTOR);
		List parameters = new ArrayList();
		parameters.add(mock(VariableElement.class));
		when(child2.getParameters()).thenReturn(parameters);
		childElements.add(child2);

		when(typeElement.getEnclosedElements()).thenReturn((List) childElements);

		assertThat(unit.checkIfClassHasNoargsConstructor(typeElement), is(true));


	}

	@Test
	public void checkIfClassHasNoargsConstructor_withNoNoargConstructor() {

		TypeElement typeElement = mock(TypeElement.class);

		List<Element> childElements = new ArrayList<Element>();

		ExecutableElement child2 = mock(ExecutableElement.class);
		when(child2.getKind()).thenReturn(ElementKind.CONSTRUCTOR);
		List parameters = new ArrayList();
		parameters.add(mock(VariableElement.class));
		when(child2.getParameters()).thenReturn(parameters);
		childElements.add(child2);

		when(typeElement.getEnclosedElements()).thenReturn((List) childElements);


	}

	@Test
	public void process_validClass() throws IOException {

		TraceeContextProviderProcessor spy = Mockito.spy(unit);

		ProfileSettings profileSettings = mock(ProfileSettings.class);
		when(profileSettings.basic()).thenReturn(true);
		when(profileSettings.enhanced()).thenReturn(true);
		when(profileSettings.full()).thenReturn(true);

		TypeElement typeElement = mock(TypeElement.class);
		when(typeElement.getAnnotation(ProfileSettings.class)).thenReturn(profileSettings);

		Set<Element> set = new HashSet<Element>();
		set.add(typeElement);

		when(roundEnvironment.getElementsAnnotatedWith(TraceeContextProvider.class)).thenReturn((Set) set);

		doReturn(true).when(spy).isValidClass(typeElement);

		doReturn(true).when(spy).checkIfTypeElementIsAssignableToType(typeElement, ImplicitContextData.class);
		doReturn(false).when(spy).checkIfTypeElementIsAssignableToType(typeElement, WrappedContextData.class);
		doReturn(true).when(spy).checkIfClassHasNoargsConstructor(typeElement);


		Mockito.doNothing().when(spy).writeToPropertyFile(Mockito.any(Profile.class), Mockito.any(TypeElement.class), Mockito.any(Boolean.class));

		assertThat(spy.process(annotationSet, roundEnvironment), Matchers.is(false));

		verify(spy).writeToPropertyFile(Profile.BASIC, typeElement, true);
		verify(spy).writeToPropertyFile(Profile.ENHANCED, typeElement, true);
		verify(spy).writeToPropertyFile(Profile.FULL, typeElement, true);


		verify(spy, never()).error(eq(typeElement), anyString(), anyString(), anyString(), anyString());

	}

	@Test
	public void process_validClassWithMissingProfileSettingsAnnotation() throws IOException {

		TraceeContextProviderProcessor spy = Mockito.spy(unit);

		ProfileSettings profileSettings = null;

		TypeElement typeElement = mock(TypeElement.class);
		when(typeElement.getAnnotation(ProfileSettings.class)).thenReturn(profileSettings);

		Set<Element> set = new HashSet<Element>();
		set.add(typeElement);

		when(roundEnvironment.getElementsAnnotatedWith(TraceeContextProvider.class)).thenReturn((Set) set);

		doReturn(true).when(spy).isValidClass(typeElement);

		doReturn(true).when(spy).checkIfTypeElementIsAssignableToType(typeElement, ImplicitContextData.class);
		doReturn(false).when(spy).checkIfTypeElementIsAssignableToType(typeElement, WrappedContextData.class);
		doReturn(true).when(spy).checkIfClassHasNoargsConstructor(typeElement);


		Mockito.doNothing().when(spy).writeToPropertyFile(Mockito.any(Profile.class), Mockito.any(TypeElement.class), Mockito.any(Boolean.class));

		assertThat(spy.process(annotationSet, roundEnvironment), Matchers.is(false));

		verify(spy).writeToPropertyFile(Profile.BASIC, typeElement, false);
		verify(spy).writeToPropertyFile(Profile.ENHANCED, typeElement, false);
		verify(spy).writeToPropertyFile(Profile.FULL, typeElement, true);


		verify(spy, never()).error(eq(typeElement), anyString(), anyString(), anyString(), anyString());

	}


	@Test
	public void process_classWithMissingNoargsConstructor() throws IOException {

		TraceeContextProviderProcessor spy = Mockito.spy(unit);

		ProfileSettings profileSettings = mock(ProfileSettings.class);
		when(profileSettings.basic()).thenReturn(true);
		when(profileSettings.enhanced()).thenReturn(true);
		when(profileSettings.full()).thenReturn(true);

		TypeElement typeElement = mock(TypeElement.class);
		when(typeElement.getAnnotation(ProfileSettings.class)).thenReturn(profileSettings);

		Set<Element> set = new HashSet<Element>();
		set.add(typeElement);

		when(roundEnvironment.getElementsAnnotatedWith(TraceeContextProvider.class)).thenReturn((Set) set);

		doReturn(true).when(spy).isValidClass(typeElement);

		doReturn(true).when(spy).checkIfTypeElementIsAssignableToType(typeElement, ImplicitContextData.class);
		doReturn(false).when(spy).checkIfTypeElementIsAssignableToType(typeElement, WrappedContextData.class);
		doReturn(false).when(spy).checkIfClassHasNoargsConstructor(typeElement);


		Mockito.doNothing().when(spy).writeToPropertyFile(Mockito.any(Profile.class), Mockito.any(TypeElement.class), Mockito.any(Boolean.class));

		assertThat(spy.process(annotationSet, roundEnvironment), Matchers.is(false));

		verify(spy, never()).writeToPropertyFile(Profile.BASIC, typeElement, true);
		verify(spy, never()).writeToPropertyFile(Profile.ENHANCED, typeElement, true);
		verify(spy, never()).writeToPropertyFile(Profile.FULL, typeElement, true);


		verify(spy, times(1)).error(eq(typeElement), anyString(), anyString(), anyString());

	}

	@Test
	public void process_classWithMissingNoargsConstructor2() throws IOException {

		TraceeContextProviderProcessor spy = Mockito.spy(unit);

		ProfileSettings profileSettings = mock(ProfileSettings.class);
		when(profileSettings.basic()).thenReturn(true);
		when(profileSettings.enhanced()).thenReturn(true);
		when(profileSettings.full()).thenReturn(true);

		TypeElement typeElement = mock(TypeElement.class);
		when(typeElement.getAnnotation(ProfileSettings.class)).thenReturn(profileSettings);

		Set<Element> set = new HashSet<Element>();
		set.add(typeElement);

		when(roundEnvironment.getElementsAnnotatedWith(TraceeContextProvider.class)).thenReturn((Set) set);

		doReturn(true).when(spy).isValidClass(typeElement);

		doReturn(false).when(spy).checkIfTypeElementIsAssignableToType(typeElement, ImplicitContextData.class);
		doReturn(true).when(spy).checkIfTypeElementIsAssignableToType(typeElement, WrappedContextData.class);
		doReturn(false).when(spy).checkIfClassHasNoargsConstructor(typeElement);


		Mockito.doNothing().when(spy).writeToPropertyFile(Mockito.any(Profile.class), Mockito.any(TypeElement.class), Mockito.any(Boolean.class));

		assertThat(spy.process(annotationSet, roundEnvironment), Matchers.is(false));

		verify(spy, never()).writeToPropertyFile(Profile.BASIC, typeElement, true);
		verify(spy, never()).writeToPropertyFile(Profile.ENHANCED, typeElement, true);
		verify(spy, never()).writeToPropertyFile(Profile.FULL, typeElement, true);


		verify(spy, times(1)).error(eq(typeElement), anyString(), anyString(), anyString());

	}

	@Test
	public void process_invalidClass() throws IOException {

		TraceeContextProviderProcessor spy = Mockito.spy(unit);

		ProfileSettings profileSettings = mock(ProfileSettings.class);
		when(profileSettings.basic()).thenReturn(true);
		when(profileSettings.enhanced()).thenReturn(true);
		when(profileSettings.full()).thenReturn(true);

		TypeElement typeElement = mock(TypeElement.class);
		when(typeElement.getAnnotation(ProfileSettings.class)).thenReturn(profileSettings);

		Set<Element> set = new HashSet<Element>();
		set.add(typeElement);

		when(roundEnvironment.getElementsAnnotatedWith(TraceeContextProvider.class)).thenReturn((Set) set);

		doReturn(false).when(spy).isValidClass(typeElement);

		doReturn(true).when(spy).checkIfTypeElementIsAssignableToType(typeElement, ImplicitContextData.class);
		doReturn(false).when(spy).checkIfTypeElementIsAssignableToType(typeElement, WrappedContextData.class);
		doReturn(true).when(spy).checkIfClassHasNoargsConstructor(typeElement);

		Mockito.doNothing().when(spy).writeToPropertyFile(Mockito.any(Profile.class), Mockito.any(TypeElement.class), Mockito.any(Boolean.class));

		assertThat(spy.process(annotationSet, roundEnvironment), Matchers.is(false));

		verify(spy, never()).writeToPropertyFile(Profile.BASIC, typeElement, true);
		verify(spy, never()).writeToPropertyFile(Profile.ENHANCED, typeElement, true);
		verify(spy, never()).writeToPropertyFile(Profile.FULL, typeElement, true);


		verify(spy, never()).error(eq(typeElement), anyString(), anyString(), anyString(), anyString());

	}


}

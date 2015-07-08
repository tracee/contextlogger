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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link FlattenProcessor}.
 */
public class FlattenProcessorTest {

	private FlattenProcessor unit = new FlattenProcessor();

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
	public void isAnnotatedAsTraceeContextProviderMethod_shouldDetectExistingAnnotation() {

		ExecutableElement executableElement = mock(ExecutableElement.class);
		TraceeContextProviderMethod annotation = mock(TraceeContextProviderMethod.class);
		when(executableElement.getAnnotation(TraceeContextProviderMethod.class)).thenReturn(annotation);


		assertThat(unit.isAnnotatedAsTraceeContextProviderMethod(executableElement), is(true));
	}

	@Test
	public void isAnnotatedAsTraceeContextProviderMethod_shouldDetectNotExistingAnnotation() {

		ExecutableElement executableElement = mock(ExecutableElement.class);
		TraceeContextProviderMethod annotation = null;
		when(executableElement.getAnnotation(TraceeContextProviderMethod.class)).thenReturn(annotation);


		assertThat(unit.isAnnotatedAsTraceeContextProviderMethod(executableElement), is(false));
	}


	@Test
	public void process_validClass() throws IOException {

		FlattenProcessor spy = Mockito.spy(unit);
		ExecutableElement element = mock(ExecutableElement.class);

		// provide elements annotated with Flatten annotation
		Set<Element> set = new HashSet<Element>();
		set.add(element);
		when(roundEnvironment.getElementsAnnotatedWith(Flatten.class)).thenReturn((Set) set);

		// mock internal functions
		doReturn(true).when(spy).isValidMethod(element);
		doReturn(true).when(spy).isGetterMethod(element);
		doReturn(true).when(spy).isAnnotatedAsTraceeContextProviderMethod(element);
		doReturn(true).when(spy).isParentAnnotatedWithTraceeContextProvider(element);


		// execute, assert and validate
		assertThat(spy.process(annotationSet, roundEnvironment), Matchers.is(false));

		verify(spy).isValidMethod(element);
		verify(spy).isGetterMethod(element);
		verify(spy).isAnnotatedAsTraceeContextProviderMethod(element);
		verify(spy).isParentAnnotatedWithTraceeContextProvider(element);

		verify(spy, never()).error(eq(element), anyString(), anyString(), anyString(), anyString());


	}

	@Test
	public void process_missingTraceeContextProviderAnnotation() throws IOException {

		FlattenProcessor spy = Mockito.spy(unit);
		ExecutableElement element = mock(ExecutableElement.class);

		// provide elements annotated with Flatten annotation
		Set<Element> set = new HashSet<Element>();
		set.add(element);
		when(roundEnvironment.getElementsAnnotatedWith(Flatten.class)).thenReturn((Set) set);

		// mock internal functions
		doReturn(true).when(spy).isValidMethod(element);
		doReturn(true).when(spy).isGetterMethod(element);
		doReturn(true).when(spy).isAnnotatedAsTraceeContextProviderMethod(element);
		doReturn(false).when(spy).isParentAnnotatedWithTraceeContextProvider(element);


		// execute, assert and validate
		assertThat(spy.process(annotationSet, roundEnvironment), Matchers.is(false));

		verify(spy).isValidMethod(element);
		verify(spy).isGetterMethod(element);
		verify(spy).isAnnotatedAsTraceeContextProviderMethod(element);
		verify(spy).isParentAnnotatedWithTraceeContextProvider(element);
		verify(spy, times(1)).error(eq(element), anyString(), anyString(), anyString(), anyString());

	}

	@Test
	public void process_missingTraceeContextProviderMethodAnnotation() throws IOException {

		FlattenProcessor spy = Mockito.spy(unit);
		ExecutableElement element = mock(ExecutableElement.class);

		// provide elements annotated with Flatten annotation
		Set<Element> set = new HashSet<Element>();
		set.add(element);
		when(roundEnvironment.getElementsAnnotatedWith(Flatten.class)).thenReturn((Set) set);

		// mock internal functions
		doReturn(true).when(spy).isValidMethod(element);
		doReturn(true).when(spy).isGetterMethod(element);
		doReturn(false).when(spy).isAnnotatedAsTraceeContextProviderMethod(element);
		doReturn(true).when(spy).isParentAnnotatedWithTraceeContextProvider(element);


		// execute, assert and validate
		assertThat(spy.process(annotationSet, roundEnvironment), Matchers.is(false));

		verify(spy).isValidMethod(element);
		verify(spy).isGetterMethod(element);
		verify(spy).isAnnotatedAsTraceeContextProviderMethod(element);
		verify(spy, never()).isParentAnnotatedWithTraceeContextProvider(element);

		verify(spy, never()).error(eq(element), anyString(), anyString(), anyString(), anyString());


	}

	@Test
	public void process_isNoGetterMethod() throws IOException {

		FlattenProcessor spy = Mockito.spy(unit);
		ExecutableElement element = mock(ExecutableElement.class);

		// provide elements annotated with Flatten annotation
		Set<Element> set = new HashSet<Element>();
		set.add(element);
		when(roundEnvironment.getElementsAnnotatedWith(Flatten.class)).thenReturn((Set) set);

		// mock internal functions
		doReturn(true).when(spy).isValidMethod(element);
		doReturn(false).when(spy).isGetterMethod(element);
		doReturn(true).when(spy).isAnnotatedAsTraceeContextProviderMethod(element);
		doReturn(true).when(spy).isParentAnnotatedWithTraceeContextProvider(element);


		// execute, assert and validate
		assertThat(spy.process(annotationSet, roundEnvironment), Matchers.is(false));

		verify(spy).isValidMethod(element);
		verify(spy).isGetterMethod(element);
		verify(spy, never()).isAnnotatedAsTraceeContextProviderMethod(element);
		verify(spy, never()).isParentAnnotatedWithTraceeContextProvider(element);

		verify(spy, never()).error(eq(element), anyString(), anyString(), anyString(), anyString());


	}

	@Test
	public void process_isInvalidMethod() throws IOException {

		FlattenProcessor spy = Mockito.spy(unit);
		ExecutableElement element = mock(ExecutableElement.class);

		// provide elements annotated with Flatten annotation
		Set<Element> set = new HashSet<Element>();
		set.add(element);
		when(roundEnvironment.getElementsAnnotatedWith(Flatten.class)).thenReturn((Set) set);

		// mock internal functions
		doReturn(false).when(spy).isValidMethod(element);
		doReturn(true).when(spy).isGetterMethod(element);
		doReturn(true).when(spy).isAnnotatedAsTraceeContextProviderMethod(element);
		doReturn(true).when(spy).isParentAnnotatedWithTraceeContextProvider(element);


		// execute, assert and validate
		assertThat(spy.process(annotationSet, roundEnvironment), Matchers.is(false));

		verify(spy).isValidMethod(element);
		verify(spy, never()).isGetterMethod(element);
		verify(spy, never()).isAnnotatedAsTraceeContextProviderMethod(element);
		verify(spy, never()).isParentAnnotatedWithTraceeContextProvider(element);

		verify(spy, never()).error(eq(element), anyString(), anyString(), anyString(), anyString());


	}

}

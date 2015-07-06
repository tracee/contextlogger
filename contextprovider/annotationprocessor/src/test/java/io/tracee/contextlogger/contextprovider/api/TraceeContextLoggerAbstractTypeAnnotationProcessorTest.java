package io.tracee.contextlogger.contextprovider.api;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link TraceeContextLoggerAbstractTypeAnnotationProcessor}.
 */
public class TraceeContextLoggerAbstractTypeAnnotationProcessorTest {


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
	public void isValidClass_shouldDetectValidClass() {
		Element element = mock(Element.class);

		Set<Modifier> modifiers = new HashSet<Modifier>();
		modifiers.add(Modifier.PUBLIC);

		when(element.getKind()).thenReturn(ElementKind.CLASS);
		when(element.getModifiers()).thenReturn(modifiers);


		assertThat(unit.isValidClass(element), Matchers.is(true));

		verify(messager, never()).printMessage(eq(Diagnostic.Kind.ERROR), anyString(), eq(element));
	}


	@Test
	public void isValidMethod_shouldDetectPassedMethod() {
		Element element = mock(Element.class);

		Set<Modifier> modifiers = new HashSet<Modifier>();
		modifiers.add(Modifier.PUBLIC);

		when(element.getKind()).thenReturn(ElementKind.METHOD);
		when(element.getModifiers()).thenReturn(modifiers);


		assertThat(unit.isValidClass(element), Matchers.is(false));

		verify(messager, times(1)).printMessage(eq(Diagnostic.Kind.ERROR), anyString(), eq(element));
	}

	@Test
	public void isValidMethod_shouldDetectPrivateModifier() {
		Element element = mock(Element.class);

		Set<Modifier> modifiers = new HashSet<Modifier>();
		modifiers.add(Modifier.PRIVATE);

		when(element.getKind()).thenReturn(ElementKind.CLASS);
		when(element.getModifiers()).thenReturn(modifiers);


		assertThat(unit.isValidClass(element), Matchers.is(false));

		verify(messager, times(1)).printMessage(eq(Diagnostic.Kind.ERROR), anyString(), eq(element));
	}


	@Test
	public void isValidMethod_shouldDetectAbstractModifier() {
		Element element = mock(Element.class);

		Set<Modifier> modifiers = new HashSet<Modifier>();
		modifiers.add(Modifier.PUBLIC);
		modifiers.add(Modifier.ABSTRACT);

		when(element.getKind()).thenReturn(ElementKind.CLASS);
		when(element.getModifiers()).thenReturn(modifiers);


		assertThat(unit.isValidClass(element), Matchers.is(false));

		verify(messager, times(1)).printMessage(eq(Diagnostic.Kind.ERROR), anyString(), eq(element));
	}

}

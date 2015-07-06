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
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
 * Unit test for {@link TraceeContextLoggerAbstractMethodAnnotationProcessor}.
 */
public class TraceeContextLoggerAbstractMethodAnnotationProcessorTest {

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
	public void isValidMethod_shouldDetectValidMethod() {
		Element element = mock(Element.class);

		Set<Modifier> modifiers = new HashSet<Modifier>();
		modifiers.add(Modifier.PUBLIC);

		when(element.getKind()).thenReturn(ElementKind.METHOD);
		when(element.getModifiers()).thenReturn(modifiers);


		assertThat(unit.isValidMethod(element), Matchers.is(true));

		verify(messager, never()).printMessage(eq(Diagnostic.Kind.ERROR), anyString(), eq(element));
	}


	@Test
	public void isValidMethod_shouldDetectPassedType() {
		Element element = mock(Element.class);

		Set<Modifier> modifiers = new HashSet<Modifier>();
		modifiers.add(Modifier.PUBLIC);

		when(element.getKind()).thenReturn(ElementKind.CLASS);
		when(element.getModifiers()).thenReturn(modifiers);


		assertThat(unit.isValidMethod(element), Matchers.is(false));
		verify(messager, times(1)).printMessage(eq(Diagnostic.Kind.ERROR), anyString(), eq(element));
	}

	@Test
	public void isValidMethod_shouldDetectPrivateModifier() {
		Element element = mock(Element.class);

		Set<Modifier> modifiers = new HashSet<Modifier>();
		modifiers.add(Modifier.PRIVATE);

		when(element.getKind()).thenReturn(ElementKind.METHOD);
		when(element.getModifiers()).thenReturn(modifiers);


		assertThat(unit.isValidMethod(element), Matchers.is(false));
		verify(messager, times(1)).printMessage(eq(Diagnostic.Kind.ERROR), anyString(), eq(element));
	}

	@Test
	public void isValidMethod_shouldDetectStaticModifier() {
		Element element = mock(Element.class);

		Set<Modifier> modifiers = new HashSet<Modifier>();
		modifiers.add(Modifier.PUBLIC);
		modifiers.add(Modifier.STATIC);

		when(element.getKind()).thenReturn(ElementKind.METHOD);
		when(element.getModifiers()).thenReturn(modifiers);


		assertThat(unit.isValidMethod(element), Matchers.is(false));
		verify(messager, times(1)).printMessage(eq(Diagnostic.Kind.ERROR), anyString(), eq(element));
	}

	@Test
	public void isValidMethod_shouldDetectAbstractModifier() {
		Element element = mock(Element.class);

		Set<Modifier> modifiers = new HashSet<Modifier>();
		modifiers.add(Modifier.PUBLIC);
		modifiers.add(Modifier.ABSTRACT);

		when(element.getKind()).thenReturn(ElementKind.METHOD);
		when(element.getModifiers()).thenReturn(modifiers);


		assertThat(unit.isValidMethod(element), Matchers.is(false));
		verify(messager, times(1)).printMessage(eq(Diagnostic.Kind.ERROR), anyString(), eq(element));
	}

	@Test
	public void isGetterMethod_shouldDetectValidGetter() {

		ExecutableElement element = mock(ExecutableElement.class);

		TypeMirror returnTypeMirror = mock(TypeMirror.class);
		when(returnTypeMirror.getKind()).thenReturn(TypeKind.LONG);

		when(element.getReturnType()).thenReturn(returnTypeMirror);

		Name name = Mockito.mock(Name.class);
		when(element.getSimpleName()).thenReturn(name);

		List parameters = new ArrayList();
		when(element.getParameters()).thenReturn(parameters);

		assertThat(unit.isGetterMethod(element), Matchers.is(true));
		verify(messager, never()).printMessage(eq(Diagnostic.Kind.ERROR), anyString(), eq(element));

	}


	@Test
	public void isGetterMethod_shouldDetectVoidReturnType() {

		ExecutableElement element = mock(ExecutableElement.class);

		TypeMirror returnTypeMirror = mock(TypeMirror.class);
		when(returnTypeMirror.getKind()).thenReturn(TypeKind.VOID);

		when(element.getReturnType()).thenReturn(returnTypeMirror);

		Name name = Mockito.mock(Name.class);
		when(element.getSimpleName()).thenReturn(name);

		List parameters = new ArrayList();
		when(element.getParameters()).thenReturn(parameters);

		assertThat(unit.isGetterMethod(element), Matchers.is(false));
		verify(messager, times(1)).printMessage(eq(Diagnostic.Kind.ERROR), anyString(), eq(element));

	}


	@Test
	public void isGetterMethod_shouldDetectParameters() {

		ExecutableElement element = mock(ExecutableElement.class);

		TypeMirror returnTypeMirror = mock(TypeMirror.class);
		when(returnTypeMirror.getKind()).thenReturn(TypeKind.LONG);

		when(element.getReturnType()).thenReturn(returnTypeMirror);

		Name name = Mockito.mock(Name.class);
		when(element.getSimpleName()).thenReturn(name);

		List parameters = new ArrayList();
		parameters.add("ABC");
		when(element.getParameters()).thenReturn(parameters);

		assertThat(unit.isGetterMethod(element), Matchers.is(false));
		verify(messager, times(1)).printMessage(eq(Diagnostic.Kind.ERROR), anyString(), eq(element));

	}

	@Test
	public void isParentAnnotatedWithTraceeContextProvider_shouldDetectValidParent() {

		ExecutableElement element = mock(ExecutableElement.class);

		TypeElement parent = mock(TypeElement.class);
		when(parent.getKind()).thenReturn(ElementKind.CLASS);

		when(element.getEnclosingElement()).thenReturn(parent);

		TraceeContextProvider annotation = mock(TraceeContextProvider.class);
		when(parent.getAnnotation(TraceeContextProvider.class)).thenReturn(annotation);


		assertThat(unit.isParentAnnotatedWithTraceeContextProvider(element), Matchers.is(true));
		verify(messager, never()).printMessage(eq(Diagnostic.Kind.ERROR), anyString(), eq(element));

	}

	@Test
	public void isParentAnnotatedWithTraceeContextProvider_shouldDetectNonClassParent() {

		ExecutableElement element = mock(ExecutableElement.class);

		TypeElement parent = mock(TypeElement.class);
		when(parent.getKind()).thenReturn(ElementKind.ENUM);

		when(element.getEnclosingElement()).thenReturn(parent);

		TraceeContextProvider annotation = mock(TraceeContextProvider.class);
		when(parent.getAnnotation(TraceeContextProvider.class)).thenReturn(annotation);


		assertThat(unit.isParentAnnotatedWithTraceeContextProvider(element), Matchers.is(false));
		verify(messager, times(1)).printMessage(eq(Diagnostic.Kind.ERROR), anyString(), eq(element));

	}

	@Test
	public void isParentAnnotatedWithTraceeContextProvider_shouldDetectNonExistingAnnotation() {

		ExecutableElement element = mock(ExecutableElement.class);

		TypeElement parent = mock(TypeElement.class);
		when(parent.getKind()).thenReturn(ElementKind.CLASS);

		when(element.getEnclosingElement()).thenReturn(parent);

		TraceeContextProvider annotation = null;
		when(parent.getAnnotation(TraceeContextProvider.class)).thenReturn(annotation);


		assertThat(unit.isParentAnnotatedWithTraceeContextProvider(element), Matchers.is(false));
		verify(messager, times(1)).printMessage(eq(Diagnostic.Kind.ERROR), anyString(), eq(element));

	}

}

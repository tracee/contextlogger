package io.tracee.contextlogger.contextprovider.api;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.HashSet;
import java.util.Set;

/**
 * Annotation Processor that checks if the {@link Flatten} Annotation is used correctly.
 */
@SupportedAnnotationTypes({"io.tracee.contextlogger.contextprovider.api.Flatten"})
public class FlattenProcessor extends TraceeContextLoggerAbstractMethodAnnotationProcessor {

	private final static Set<String> SUPPORTED_ANNOTATION_TYPES = new HashSet<String>();

	static {
		SUPPORTED_ANNOTATION_TYPES.add(Flatten.class.getCanonicalName());
	}

	@Override
	public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
		for (Element element : roundEnv.getElementsAnnotatedWith(Flatten.class)) {

			if (!isValidMethod(element)) {
				continue;
			}

			ExecutableElement executableElement = (ExecutableElement) element;

			if (!isGetterMethod(executableElement)) {
				continue;
			}

			if (!isAnnotatedAsTraceeContextProviderMethod(executableElement)) {
				continue;
			}

			if (!isParentAnnotatedWithTraceeContextProvider(element)) {
				error(element, "Element %s annotated with annotation %s is only allowed in classes annotated with annotation %s", element.getSimpleName(), Flatten.class.getSimpleName(),
						TraceeContextProvider.class.getSimpleName());
				continue;
			}


		}

		return false;
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return SUPPORTED_ANNOTATION_TYPES;
	}

	protected boolean isAnnotatedAsTraceeContextProviderMethod(ExecutableElement executableElement) {
		return executableElement.getAnnotation(TraceeContextProviderMethod.class) != null;
	}

}

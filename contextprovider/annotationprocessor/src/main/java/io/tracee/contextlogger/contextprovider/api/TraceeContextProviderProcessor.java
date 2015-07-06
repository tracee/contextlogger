package io.tracee.contextlogger.contextprovider.api;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Annotation Processor that checks if the {@link Flatten} Annotation is used correctly.
 */
@SupportedAnnotationTypes({"io.tracee.contextlogger.contextprovider.api.TraceeContextProvider"})
public class TraceeContextProviderProcessor extends TraceeContextLoggerAbstractTypeAnnotationProcessor {

	private final static Set<String> SUPPORTED_ANNOTATION_TYPES = new HashSet<String>();

	static {
		SUPPORTED_ANNOTATION_TYPES.add(TraceeContextProvider.class.getCanonicalName());
	}

	@Override
	public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
		for (Element element : roundEnv.getElementsAnnotatedWith(TraceeContextProvider.class)) {

			// check if annotation is used correctly
			if (!isValidClass(element)) {
				continue;
			}

			TypeElement typeElement = (TypeElement) element;

			// check if class has noarg constructor
			if ((checkIfTypeElementIsAssignableToType(typeElement, WrappedContextData.class)
					|| checkIfTypeElementIsAssignableToType(typeElement, ImplicitContextData.class))
					&& !checkIfClassHasNoargsConstructor(typeElement)) {
				error(typeElement, "Class %s annotated with annotation %s must have a noargs constructor", typeElement.getSimpleName(),
						TraceeContextProvider.class.getSimpleName());
				continue;
			}


			// Write class profile properties
			try {

				ProfileSettings annotation = element.getAnnotation(ProfileSettings.class);

				boolean basicProfileSettings = annotation != null && annotation.basic();
				boolean enhancedProfileSettings = annotation != null && annotation.enhanced();
				boolean fullProfileSettings = annotation == null || annotation.full();

				writeToPropertyFile(Profile.BASIC, (TypeElement) element, basicProfileSettings);
				writeToPropertyFile(Profile.ENHANCED, (TypeElement) element, enhancedProfileSettings);
				writeToPropertyFile(Profile.FULL, (TypeElement) element, fullProfileSettings);


			} catch (Throwable e) {
				error(element, "Could not write profile property file entry for class %s annotated with %s (error message: '%s') ", element.getSimpleName(),
						TraceeContextProviderMethod.class.getSimpleName(), e.toString());
			}


		}


		return false;
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return SUPPORTED_ANNOTATION_TYPES;
	}

	protected void writeToPropertyFile(Profile profile, TypeElement element, boolean value) throws IOException {

		FileObjectWrapper fileObject = getOrcreateProfileProperties(filer, profile.getFilename());
		fileObject.append(element.getQualifiedName() + "=" + value + "\n");

	}

	/**
	 * Checks if class has noargs constructor or default noargs constructor.
	 *
	 * @param typeElement the type element to check.
	 * @return true if element has noarg constructor or no constructor at all
	 */
	protected boolean checkIfClassHasNoargsConstructor(TypeElement typeElement) {

		// check if annotated class has noargs constructor
		boolean foundConstructor = false;
		boolean foundNoargsConstructor = false;

		for (Element child : typeElement.getEnclosedElements()) {
			if (ElementKind.CONSTRUCTOR.equals(child.getKind())) {
				foundConstructor = true;
				ExecutableElement constructor = (ExecutableElement) child;
				if (constructor.getParameters().size() == 0) {
					foundNoargsConstructor = true;
					break;
				}
			}
		}
		return !(foundConstructor && !foundNoargsConstructor);

	}

}

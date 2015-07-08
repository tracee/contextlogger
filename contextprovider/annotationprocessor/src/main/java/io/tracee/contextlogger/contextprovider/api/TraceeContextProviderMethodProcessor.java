package io.tracee.contextlogger.contextprovider.api;

import io.tracee.contextlogger.utility.GetterUtilities;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Annotation Processor that checks if the {@link TraceeContextProviderMethod} Annotation is used correctly.
 */
@SupportedAnnotationTypes({"io.tracee.contextlogger.contextprovider.api.TraceeContextProviderMethod"})
public class TraceeContextProviderMethodProcessor extends TraceeContextLoggerAbstractMethodAnnotationProcessor {

	private final static Set<String> SUPPORTED_ANNOTATION_TYPES = new HashSet<String>();

	static {
		SUPPORTED_ANNOTATION_TYPES.add(TraceeContextProviderMethod.class.getCanonicalName());
	}


	@Override
	public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
		for (Element element : roundEnv.getElementsAnnotatedWith(TraceeContextProviderMethod.class)) {

			if (!isValidMethod(element)) {
				continue;
			}

			ExecutableElement executableElement = (ExecutableElement) element;

			if (!isGetterMethod(executableElement)) {
				continue;
			}

			if (!isParentAnnotatedWithTraceeContextProvider(element)) {
				continue;
			}
			Element parentElement = element.getEnclosingElement();


			// Write to profile files
			try {

				// write entry for fields
				ProfileConfig annotation = element.getAnnotation(ProfileConfig.class);

				boolean basicProfileSettings = annotation != null && annotation.basic();
				boolean enhancedProfileSettings = annotation != null && annotation.enhanced();
				boolean fullProfileSettings = annotation == null || annotation.full();

				writeToPropertyFile(Profile.BASIC, (TypeElement) parentElement, element, basicProfileSettings);
				writeToPropertyFile(Profile.ENHANCED, (TypeElement) parentElement, element, enhancedProfileSettings);
				writeToPropertyFile(Profile.FULL, (TypeElement) parentElement, element, fullProfileSettings);

			} catch (Throwable e) {
				error(element, "Could not write profile property file entry for method %s annotated with %s (error message: '%s') ", element.getSimpleName(),
						TraceeContextProviderMethod.class.getSimpleName(), e.toString());
			}


		}

		return false;
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return SUPPORTED_ANNOTATION_TYPES;
	}

	protected void writeToPropertyFile(Profile profile, TypeElement parentElement, Element element, boolean value) throws IOException {

		FileObjectWrapper fileObject = getOrCreateProfileProperties(filer, profile.getFilename());

		String fieldName = GetterUtilities.getFieldName(element.getSimpleName().toString());
		if (fieldName == null) {
			fieldName = element.getSimpleName().toString();
		}

		fileObject.append(((TypeElement) parentElement).getQualifiedName() + "." + fieldName + "=" + value + "\n");


	}

}

package io.tracee.contextlogger.contextprovider.api;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Abstract base class for type based annotations like {@link TraceeContextProvider}.
 */
public abstract class TraceeContextLoggerAbstractTypeAnnotationProcessor extends TraceeContextLoggerAbstractProcessor {

	protected boolean isValidClass(Element element) {

		// must be of type class
		if (element.getKind() != ElementKind.CLASS) {
			error(element, "Element %s annotated with annotation %s must be a class", element.getSimpleName(),
					TraceeContextProviderMethod.class.getSimpleName());
			return false;
		}

		// must be public
		if (!element.getModifiers().contains(Modifier.PUBLIC)) {
			error(element, "Element %s annotated with annotation %s must be declared as public", element.getSimpleName(),
					TraceeContextProviderMethod.class.getSimpleName());
			return false;
		}

		// must not abstract
		if (element.getModifiers().contains(Modifier.ABSTRACT)) {
			error(element, "Element %s annotated with annotation %s must not be a abstract", element.getSimpleName(),
					TraceeContextProviderMethod.class.getSimpleName());
			return false;
		}

		return true;
	}

	protected boolean checkIfTypeElementIsAssignableToType(TypeElement typeElement, Class type) {

		return typeUtils.isAssignable(typeElement.asType(), elementUtils.getTypeElement(type.getCanonicalName()).asType());

	}
}

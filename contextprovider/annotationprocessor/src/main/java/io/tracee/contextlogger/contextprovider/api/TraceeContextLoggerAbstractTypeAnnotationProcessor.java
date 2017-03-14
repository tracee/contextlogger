package io.tracee.contextlogger.contextprovider.api;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
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

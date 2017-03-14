package io.tracee.contextlogger.contextprovider.api;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.List;

/**
 * Abstract base class for method based annotations like {@link Flatten} or  {@link TraceeContextProviderMethod}.
 */
public abstract class TraceeContextLoggerAbstractMethodAnnotationProcessor extends TraceeContextLoggerAbstractProcessor {


    /**
     * Checks if passed element is a method declared as public, not abstract and not static
     *
     * @param element the element to check
     * @return true if passed element is public, not abstract and not static method
     */
    protected boolean isValidMethod(Element element) {

        // must be of type class
        if (element.getKind() != ElementKind.METHOD) {
            error(element, "Element %s annotated with annotation %s must be a method", element.getSimpleName(),
                    TraceeContextProviderMethod.class.getSimpleName());
            return false;
        }

        // must be public
        if (!element.getModifiers().contains(Modifier.PUBLIC)) {
            error(element, "Method %s annotated with annotation %s must be public", element.getSimpleName(),
                    TraceeContextProviderMethod.class.getSimpleName());
            return false;
        }

        // must not abstract
        if (element.getModifiers().contains(Modifier.ABSTRACT)) {
            error(element, "Method %s annotated with annotation %s must not be abstract", element.getSimpleName(),
                    TraceeContextProviderMethod.class.getSimpleName());
            return false;
        }

        // must not be static
        if (element.getModifiers().contains(Modifier.STATIC)) {
            error(element, "Method %s annotated with annotation %s must not be static", element.getSimpleName(),
                    TraceeContextProviderMethod.class.getSimpleName());
            return false;
        }

        return true;
    }


    /**
     * Checks if passed element has a non void return type and takes no parameters
     *
     * @param executableElement
     * @return true if passed element has a non void return type and takes no parameters
     */
    protected boolean isGetterMethod(ExecutableElement executableElement) {

        // must have a return value
        TypeMirror returnTypeMirror = executableElement.getReturnType();

        if (returnTypeMirror.getKind().equals(TypeKind.VOID)) {
            error(executableElement, "method %s must have a non void return type", executableElement.getSimpleName().toString());
            return false;
        }

        // check if method takes no parameters
        List parameters = executableElement.getParameters();
        if (parameters != null && parameters.size() > 0) {
            error(executableElement, "method %s must have no parameters ", executableElement.getSimpleName().toString());
            return false;
        }

        return true;
    }

    /**
     * Checks if passed elements parent is type element and annotated with {@link TraceeContextProvider}.
     *
     * @param element
     * @return
     */
    protected boolean isParentAnnotatedWithTraceeContextProvider(final Element element) {

        Element parentElement = element.getEnclosingElement();
        if (parentElement == null || parentElement.getKind() != ElementKind.CLASS
                || !isTypeAnnotatedWithTraceeContextProvider((TypeElement) parentElement)) {

            error(element, "Enclosing type of method %s annotated with annotation %s is not annotated with annotation %s.", element.getSimpleName(),
                    Flatten.class.getSimpleName(), TraceeContextProvider.class.getSimpleName());
            return false;

        }

        return true;
    }

    protected boolean isTypeAnnotatedWithTraceeContextProvider(TypeElement element) {


        return isTypeAnnotatedWithAnnotation(element, TraceeContextProvider.class);

    }
}

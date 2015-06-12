package io.tracee.contextlogger.contextprovider.api;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

/**
 * Annotation Processor that checks if the {@link TraceeContextProviderMethod} Annotation is used correctly.
 */
public class TraceeContextProviderMethodProcessor extends TraceeContextLoggerAbstractProcessor {

    private final static Set<String> SUPPORTED_ANNOTATION_TYPES = new HashSet<String>();

    static {
        SUPPORTED_ANNOTATION_TYPES.add(TraceeContextProviderMethod.class.getCanonicalName());
    }

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(Flatten.class)) {

            if (!isValidMethod(element)) {
                error(element, "Method %s annotated with annotation %s must be public, not static and not abstract", element.getSimpleName(),
                        Flatten.class.getSimpleName());
                continue;
            }

            ExecutableElement executableElement = (ExecutableElement)element;

            if (!isGetterMethod(executableElement, true)) {
                error(element, "Method %s annotated with annotation %s must be have a return type of type Collection and must not have any parameters.",
                        element.getSimpleName(), Flatten.class.getSimpleName());
                continue;
            }

            Element parentElement = element.getEnclosingElement();
            if (parentElement == null || parentElement.getKind() != ElementKind.CLASS
                    || !isTypeAnnotatedWithTraceeContextProvider((TypeElement)parentElement)) {
                error(element, "Enclosing type of method %s annotated with annotation %s is not annotated with annotation %s.", element.getSimpleName(),
                        Flatten.class.getSimpleName(), TraceeContextProvider.class.getSimpleName());
                continue;
            }
        }

        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return SUPPORTED_ANNOTATION_TYPES;
    }
}

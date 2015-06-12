package io.tracee.contextlogger.contextprovider.api;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

/**
 * Annotation Processor that checks if the {@link Flatten} Annotation is used correctly.
 */
@SupportedAnnotationTypes({ "io.tracee.contextlogger.contextprovider.api.TraceeContextProvider" })
public class TraceeContextProviderProcessor extends TraceeContextLoggerAbstractProcessor {

    private final static Set<String> SUPPORTED_ANNOTATION_TYPES = new HashSet<String>();

    static {
        SUPPORTED_ANNOTATION_TYPES.add(TraceeContextProvider.class.getCanonicalName());
    }

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(TraceeContextProvider.class)) {

            if (!isValidClass(element)) {
                error(element, "Class %s annotated with annotation %s must be public and not abstract", element.getSimpleName(),
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

    private boolean isAnnotatedAsTraceeContextProviderMethod(ExecutableElement executableElement) {
        return executableElement.getAnnotation(TraceeContextProviderMethod.class) != null;
    }

}

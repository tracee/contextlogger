package io.tracee.contextlogger.contextprovider.api;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.HashSet;
import java.util.Set;

/**
 * Annotation Processor that checks if the {@link TraceeContextProviderPrimitiveType} Annotation is used correctly.
 */
@SupportedAnnotationTypes({"io.tracee.contextlogger.contextprovider.api.TraceeContextProviderPrimitiveType"})
public class PrimitiveTypeProcessor extends TraceeContextLoggerAbstractTypeAnnotationProcessor {

    private final static Set<String> SUPPORTED_ANNOTATION_TYPES = new HashSet<String>();

    static {
        SUPPORTED_ANNOTATION_TYPES.add(TraceeContextProviderPrimitiveType.class.getCanonicalName());
    }

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(TraceeContextProviderPrimitiveType.class)) {

            // check if annotation is used correctly
            if (!isValidClass(element)) {
                continue;
            }

            TypeElement typeElement = (TypeElement) element;


            // check if class implements WrappedPrimitiveTypeContextData interface
            if (!checkIfTypeElementIsAssignableToType(typeElement, WrappedPrimitiveTypeContextData.class)) {
                error(typeElement, "Class %s annotated with annotation %s must have either implement %s or %s interface", typeElement.getSimpleName(),
                        TraceeContextProviderPrimitiveType.class.getSimpleName(), WrappedPrimitiveTypeContextData.class.getSimpleName());
                continue;
            }


            // check if class has noarg constructor
            if (!checkIfClassHasNoargsConstructor(typeElement)) {
                error(typeElement, "Class %s annotated with annotation %s must have a noargs constructor", typeElement.getSimpleName(),
                        TraceeContextProvider.class.getSimpleName());
                continue;
            }

            // check if type is also annotated with TraceeContextProvider annotation
            if (isTypeAnnotatedWithAnnotation(typeElement, TraceeContextProvider.class)) {
                error(typeElement, "Class %s annotated with annotation %s must not be annotated with annotation %s", typeElement.getSimpleName(), TraceeContextProviderPrimitiveType.class.getSimpleName(),
                        TraceeContextProvider.class.getSimpleName());
            }


        }


        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return SUPPORTED_ANNOTATION_TYPES;
    }


}

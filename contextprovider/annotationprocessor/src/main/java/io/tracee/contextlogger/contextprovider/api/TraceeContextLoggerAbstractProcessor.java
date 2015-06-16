package io.tracee.contextlogger.contextprovider.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * Base class for all Processors.
 */
public abstract class TraceeContextLoggerAbstractProcessor extends AbstractProcessor {

    private static Map<String, DeclaredType> cachedParentTypes = new HashMap<String, DeclaredType>();

    protected Messager messager;
    protected Types typeUtils;
    protected Elements elementUtils;
    protected Filer filer;

    protected TypeElement COLLECTION;
    protected TypeElement MAP;
    protected TypeElement VOID;
    protected WildcardType WILDCARD_TYPE_NULL;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        // create local references
        messager = processingEnv.getMessager();
        typeUtils = processingEnv.getTypeUtils();
        filer = processingEnv.getFiler();
        elementUtils = processingEnv.getElementUtils();

        COLLECTION = elementUtils.getTypeElement("java.util.Collection");
        MAP = elementUtils.getTypeElement("java.util.Map");
        VOID = elementUtils.getTypeElement("java.lang.Void");
        WILDCARD_TYPE_NULL = typeUtils.getWildcardType(null, null);

    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    protected boolean isValidMethod(Element element) {

        // must not be abstract and must be public
        if (!isValidOfKind(element, ElementKind.METHOD)) {
            return false;
        }

        // must not be static
        if (element.getModifiers().contains(Modifier.STATIC)) {
            return false;
        }

        return true;
    }

    protected boolean isValidClass(Element element) {
        return isValidOfKind(element, ElementKind.CLASS);
    }

    private boolean isValidOfKind(Element element, ElementKind elementKind) {

        // must be of type class
        if (element.getKind() != elementKind) {
            return false;
        }

        // must be public
        if (!element.getModifiers().contains(Modifier.PUBLIC)) {
            return false;
        }

        // must not abstract
        if (element.getModifiers().contains(Modifier.ABSTRACT)) {
            return false;
        }

        return true;
    }

    protected boolean isMethod(Element element) {
        return element != null && element.getKind() == ElementKind.METHOD;
    }

    protected boolean isGetterMethod(ExecutableElement executableElement, final boolean returnTypeMustBeOfTypeCollection) {

        // must have a return value
        TypeMirror returnTypeMirror = executableElement.getReturnType();

        if (returnTypeMirror.getKind().equals(TypeKind.VOID)) {
            error(executableElement, "method %s has no return type", executableElement.getSimpleName().toString());
            return false;
        }

        // check if method takes no parameters
        List parameters = executableElement.getParameters();
        if (parameters != null && parameters.size() > 0) {
            error(executableElement, "method %s must have no parameters ", executableElement.getSimpleName().toString());
            return false;
        }

        /*
         * TypeElement collectionElement = elementUtils.getTypeElement(List.class.getCanonicalName());
         * if (!typeUtils.isAssignable(executableElement.getReturnType(), collectionElement.asType())) {
         * error(executableElement, "method %s has a return type %s that cannot be assiged to Collection ", executableElement.getSimpleName().toString(),
         * executableElement.getReturnType().toString());
         * return false;
         * }
         */
        return true;
    }

    protected boolean isTypeAnnotatedWithTraceeContextProvider(TypeElement element) {

        TraceeContextProvider contextProviderAnnotation = element.getAnnotation(TraceeContextProvider.class);
        return contextProviderAnnotation != null;

    }

    protected void error(Element e, String message, Object... args) {
        messager.printMessage(Diagnostic.Kind.ERROR, String.format(message, args), e);
    }

    protected boolean isA(TypeMirror type, TypeElement typeElement) {

        // Have we used this type before?
        DeclaredType parentType = cachedParentTypes.get(typeElement.getQualifiedName().toString());
        if (parentType == null) {
            // How many generic type parameters does this typeElement require?
            int genericsCount = typeElement.getTypeParameters().size();

            // Fill the right number of types with nulls
            TypeMirror[] types = new TypeMirror[genericsCount];
            for (int i = 0; i < genericsCount; i++) {
                types[i] = WILDCARD_TYPE_NULL;
            }

            // Locate the correct DeclaredType to match with the type
            parentType = typeUtils.getDeclaredType(typeElement, types);

            // Remember this DeclaredType
            cachedParentTypes.put(typeElement.getQualifiedName().toString(), parentType);
        }

        // Is the given type able to be assigned as the typeElement?
        return typeUtils.isAssignable(type, parentType);
    }

}

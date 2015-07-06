package io.tracee.contextlogger.contextprovider.api;

import io.tracee.contextlogger.utility.GetterUtilities;

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
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base class for all Processors.
 */
public abstract class TraceeContextLoggerAbstractProcessor extends AbstractProcessor {

	private static Map<String, DeclaredType> cachedParentTypes = new HashMap<String, DeclaredType>();
	private static Map<String, FileObjectWrapper> traceeProfileProperties = new HashMap<String, FileObjectWrapper>();

	protected Messager messager;
	protected Types typeUtils;
	protected Elements elementUtils;
	protected Filer filer;

	protected TypeElement COLLECTION;
	protected TypeElement MAP;
	protected TypeElement VOID;
	protected WildcardType WILDCARD_TYPE_NULL;

	public static class FileObjectWrapper {
		private final FileObject fileObject;
		private final Writer foWriter;

		public FileObjectWrapper(FileObject fileObject) throws IOException {
			this.fileObject = fileObject;
			this.foWriter = fileObject.openWriter();
		}

		public void append(String content) throws IOException {
			foWriter.append(content);
			foWriter.flush();
		}
	}

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


	protected void error(Element e, String message, Object... args) {
		messager.printMessage(Diagnostic.Kind.ERROR, String.format(message, args), e);
	}

	protected void info(Element e, String message, Object... args) {
		messager.printMessage(Diagnostic.Kind.NOTE, String.format(message, args), e);
	}

	/**
	 * Checks if types of passed TypeMirror and Typeelement are compatible.
	 * @param type
	 * @param typeElement
	 * @return
	 */
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



	/**
	 * Central method to get cached FileObjectWrapper. Creates new FileObjectWrapper if it can't be found
	 */
	protected static synchronized FileObjectWrapper getOrcreateProfileProperties(final Filer filer, String fileName) throws IOException {

		FileObjectWrapper fileObject = traceeProfileProperties.get(fileName);

		if (fileObject == null) {

			fileObject = new FileObjectWrapper(filer.createResource(StandardLocation.SOURCE_OUTPUT, "", fileName, null));
			traceeProfileProperties.put(fileName, fileObject);

		}
		return fileObject;

	}



}

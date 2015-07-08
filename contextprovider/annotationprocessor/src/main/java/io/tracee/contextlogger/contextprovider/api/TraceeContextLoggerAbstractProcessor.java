package io.tracee.contextlogger.contextprovider.api;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Base class for all Processors.
 */
public abstract class TraceeContextLoggerAbstractProcessor extends AbstractProcessor {

	private static Map<String, FileObjectWrapper> traceeProfileProperties = new HashMap<String, FileObjectWrapper>();

	protected Messager messager;
	protected Types typeUtils;
	protected Elements elementUtils;
	protected Filer filer;

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
	 * Central method to get cached FileObjectWrapper. Creates new FileObjectWrapper if it can't be found
	 */
	protected static synchronized FileObjectWrapper getOrCreateProfileProperties(final Filer filer, String fileName) throws IOException {

		FileObjectWrapper fileObject = traceeProfileProperties.get(fileName);

		if (fileObject == null) {

			fileObject = new FileObjectWrapper(filer.createResource(StandardLocation.SOURCE_OUTPUT, "", fileName, null));
			traceeProfileProperties.put(fileName, fileObject);

		}
		return fileObject;

	}

	protected static void clearCache() {
		traceeProfileProperties.clear();
	}

}

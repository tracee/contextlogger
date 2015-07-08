package io.tracee.contextlogger.contextprovider.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link TraceeContextLoggerAbstractProcessor}.
 */
@RunWith(MockitoJUnitRunner.class)
public class TraceeContextLoggerAbstractProcessorTest {

	static final String GIVEN_FILE_NAME = "XXX";

	@Mock
	Filer filer;

	@Before
	public void init() {
		TraceeContextLoggerAbstractProcessor.clearCache();
	}

	@Test
	public void FileObjectWrapper_instantiationAndAppendTest() throws IOException {

		// prepare test
		FileObject fileObject = mock(FileObject.class);
		Writer writer = mock(Writer.class);
		when(fileObject.openWriter()).thenReturn(writer);

		TraceeContextLoggerAbstractProcessor.FileObjectWrapper fowUnit = new TraceeContextLoggerAbstractProcessor.FileObjectWrapper(fileObject);

		fowUnit.append("TEST");

		verify(fileObject).openWriter();
		verify(writer).append("TEST");
		verify(writer).flush();

	}

	@Test
	public void getOrcreateProfileProperties_createNewFileObject() throws IOException {

		FileObject fileObject = mock(FileObject.class);
		Writer writer = mock(Writer.class);

		when(filer.createResource(eq(StandardLocation.SOURCE_OUTPUT), eq(""), eq(GIVEN_FILE_NAME), isNull(Element.class))).thenReturn(fileObject);

		assertThat(TraceeContextLoggerAbstractProcessor.getOrCreateProfileProperties(filer, GIVEN_FILE_NAME), notNullValue());
		verify(filer, times(1)).createResource(StandardLocation.SOURCE_OUTPUT, "", GIVEN_FILE_NAME, null);

	}

	@Test
	public void getOrcreateProfileProperties_getCachedFileObject() throws IOException {

		// fill cache
		getOrcreateProfileProperties_createNewFileObject();

		// get it for the second time
		assertThat(TraceeContextLoggerAbstractProcessor.getOrCreateProfileProperties(filer, GIVEN_FILE_NAME), notNullValue());
		verify(filer, times(1)).createResource(StandardLocation.SOURCE_OUTPUT, "", GIVEN_FILE_NAME, null);

	}


}

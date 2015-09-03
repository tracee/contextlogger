package io.tracee.contextlogger;

import io.tracee.contextlogger.contextprovider.core.java.JavaThrowableContextProvider;
import io.tracee.contextlogger.outputgenerator.writer.BasicOutputWriterConfiguration;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit test for {@link TraceeContextLogger}.
 */
public class TraceeContextLoggerTest {

	@Test
	public void should_detect_suppress_passed_instance_to_be_excluded() {

		TraceeContextLogger unit = (TraceeContextLogger) TraceeContextLogger.create()
				.enforceOutputWriterConfiguration(BasicOutputWriterConfiguration.JSON_INLINE).disableTypes(String.class).apply();

		String result = unit.toString("ABC", 1L);

		MatcherAssert.assertThat(result, Matchers.equalTo("[\"TYPE:Object[]\",\"Long<1>\"]"));

	}

	@Test
	public void should_create_string_representation() {

		TraceeContextLogger unit = (TraceeContextLogger) TraceeContextLogger.create()
				.enforceOutputWriterConfiguration(BasicOutputWriterConfiguration.JSON_INLINE).apply();

		String result = unit.toString("ABC", 1L);
		MatcherAssert.assertThat(result, Matchers.equalTo("[\"TYPE:Object[]\",\"String<'ABC'>\",\"Long<1>\"]"));

		Object[] objectsToBeProcessed = {"DEF", 2L};
		unit.setObjectsToProcess(objectsToBeProcessed);
		result = unit.provideOutput();
		MatcherAssert.assertThat(result, Matchers.equalTo("[\"TYPE:Object[]\",\"String<'DEF'>\",\"Long<2>\"]"));

	}

	@Test
	public void excludeContextProviders_should_exclude_contextProviders_correctly() {

		TraceeContextLogger unit = (TraceeContextLogger) ((TraceeContextLogger) TraceeContextLogger.create()
				.enforceOutputWriterConfiguration(BasicOutputWriterConfiguration.JSON_INLINE).apply())
				.excludeContextProviders(JavaThrowableContextProvider.class);

		String result = unit.toString(new NullPointerException());

		MatcherAssert.assertThat(result, Matchers.is("null"));

		result = unit.toString(new NullPointerException(), "ABC");

		MatcherAssert.assertThat(result, Matchers.equalTo("[\"TYPE:Object[]\",\"String<'ABC'>\"]"));

	}

}

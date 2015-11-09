package io.tracee.contextlogger.integrationtest;

import io.tracee.contextlogger.TraceeToStringBuilder;
import io.tracee.contextlogger.outputgenerator.writer.BasicOutputWriterConfiguration;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit test for checking if fields can be accessed correctly.
 */
public class AccessFieldTest {

	public static class TestParentClass {

		private String parentField = "PARENT";


		public String getParentField() {
			// throw Runtime exception to check if parent will be read directly from field
			throw new RuntimeException();
		}

		public void setParentField(final String parentField) {
			this.parentField = parentField;
		}
	}

	public static class TestLinkedClass {

		private String linkedField = "LINKED";


		public String getLinkedField() {
			// throw Runtime exception to check if parent will be read directly from field
			throw new RuntimeException();
		}

		public void setLinkedField(final String linkedField) {
			this.linkedField = linkedField;
		}
	}

	public static class TestBaseClass extends TestParentClass {

		private String baseField = "BASE";
		private TestLinkedClass linkedClass = new TestLinkedClass();

		public TestLinkedClass getLinkedClass() {
			// throw Runtime exception to check if parent will be read directly from field
			throw new RuntimeException();
		}

		public void setLinkedClass(final TestLinkedClass linkedClass) {
			this.linkedClass = linkedClass;
		}

		public String getBaseField() {
			// throw Runtime exception to check if parent will be read directly from field
			throw new RuntimeException();
		}

		public void setBaseField(final String baseField) {
			this.baseField = baseField;
		}
	}

	@Test
	public void testFieldAccess() {

		String stringRepresentation = TraceeToStringBuilder.create().enforceOutputWriterConfiguration(BasicOutputWriterConfiguration.JSON_INLINE).apply().toString(new TestBaseClass());

		MatcherAssert.assertThat(stringRepresentation, Matchers.containsString("String<'BASE'>"));
		MatcherAssert.assertThat(stringRepresentation, Matchers.containsString("String<'LINKED'>"));
		MatcherAssert.assertThat(stringRepresentation, Matchers.containsString("String<'PARENT'>"));

	}

	@Test
	public void testFieldAccessWithIgnoredClass() {

		String stringRepresentation = TraceeToStringBuilder.create().disableTypes(TestLinkedClass.class).enforceOutputWriterConfiguration(BasicOutputWriterConfiguration.JSON_INLINE).apply().toString(new TestBaseClass());

		MatcherAssert.assertThat(stringRepresentation, Matchers.containsString("String<'BASE'>"));
		MatcherAssert.assertThat(stringRepresentation, Matchers.not(Matchers.containsString("String<'LINKED'>")));
		MatcherAssert.assertThat(stringRepresentation, Matchers.containsString("String<'PARENT'>"));
	}

}

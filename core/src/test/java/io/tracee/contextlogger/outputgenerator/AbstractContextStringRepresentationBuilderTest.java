package io.tracee.contextlogger.outputgenerator;

import io.tracee.contextlogger.contextprovider.api.Profile;
import io.tracee.contextlogger.outputgenerator.api.TraceeContextStringRepresentationBuilder;
import io.tracee.contextlogger.outputgenerator.writer.BasicOutputWriterConfiguration;
import io.tracee.contextlogger.outputgenerator.writer.OutputWriterConfiguration;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for {@link AbstractContextStringRepresentationBuilder}.
 */
public class AbstractContextStringRepresentationBuilderTest {

	public static class TestContextStringRepresentationBuilder extends AbstractContextStringRepresentationBuilder {

		@Override
		public String createStringRepresentation(final Object... instancesToProcess) {
			return null;
		}

		@Override
		public TraceeContextStringRepresentationBuilder cloneStringRepresentationBuilder() {
			return null;
		}
	}

	TestContextStringRepresentationBuilder unit = new TestContextStringRepresentationBuilder();

	@Test
	public void getManualContextOverrides_should_handle_manual_override_map_correctly() {

		// test 1 : null valued contextual overrides
		unit.setManualContextOverrides(null);
		MatcherAssert.assertThat(unit.getManualContextOverrides(), Matchers.notNullValue());
		MatcherAssert.assertThat(unit.getManualContextOverrides().size(), Matchers.is(0));

		// test 2: non null valued contextual overrrides
		Map<String, Boolean> manualOverrideMap = new HashMap<String, Boolean>();

		unit.setManualContextOverrides(manualOverrideMap);
		MatcherAssert.assertThat(unit.getManualContextOverrides(), Matchers.notNullValue());
		MatcherAssert.assertThat(unit.getManualContextOverrides(), Matchers.is(manualOverrideMap));

	}

	@Test
	public void getOutputWriterConfiguration_should_handle_output_writer_configuration_correctly() {

		// test 1 : null valued output writer configuration
		unit.setOutputWriterConfiguration(null);
		MatcherAssert.assertThat(unit.getOutputWriterConfiguration(), Matchers.notNullValue());
		MatcherAssert.assertThat(unit.getOutputWriterConfiguration(), Matchers.is((OutputWriterConfiguration) BasicOutputWriterConfiguration.JSON_INTENDED));

		// test 2: non null valued output writer configuration
		unit.setOutputWriterConfiguration(BasicOutputWriterConfiguration.JSON_INLINE);
		MatcherAssert.assertThat(unit.getOutputWriterConfiguration(), Matchers.notNullValue());
		MatcherAssert.assertThat(unit.getOutputWriterConfiguration(), Matchers.is((OutputWriterConfiguration) BasicOutputWriterConfiguration.JSON_INLINE));

	}

	@Test
	public void cloneTo_should_clone_all_fields_correctly() {

		Map<String, Boolean> manualOverrideMap = new HashMap<String, Boolean>();
		manualOverrideMap.put("ABC", true);

		TestContextStringRepresentationBuilder source = new TestContextStringRepresentationBuilder();
		source.setOutputWriterConfiguration(BasicOutputWriterConfiguration.JSON_INLINE);
		source.setProfile(Profile.FULL);
		source.setManualContextOverrides(manualOverrideMap);
		source.setEnforceOrder(true);

		TestContextStringRepresentationBuilder cloneTarget = new TestContextStringRepresentationBuilder();

		source.cloneTo(cloneTarget);

		MatcherAssert.assertThat(cloneTarget.getProfile(), Matchers.is(source.getProfile()));
		MatcherAssert.assertThat(cloneTarget.getEnforceOrder(), Matchers.is(source.getEnforceOrder()));
		MatcherAssert.assertThat(cloneTarget.getOutputWriterConfiguration(), Matchers.is(source.getOutputWriterConfiguration()));

		MatcherAssert.assertThat(System.identityHashCode(cloneTarget.getManualContextOverrides()),
				Matchers.not(System.identityHashCode(source.getManualContextOverrides())));
		MatcherAssert.assertThat(cloneTarget.getManualContextOverrides().get("ABC"), Matchers.is(true));


	}
}

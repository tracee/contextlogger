package io.tracee.contextlogger;

import io.tracee.contextlogger.contextprovider.api.Profile;
import io.tracee.contextlogger.impl.ContextLoggerConfiguration;
import io.tracee.contextlogger.outputgenerator.api.TraceeContextStringRepresentationBuilder;
import io.tracee.contextlogger.outputgenerator.writer.OutputWriterConfiguration;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

/**
 * Unit test for {@link AbstractToStringBuilder}.
 */
public class AbstractToStringBuilderTest {

	public static class TestToStringBuilder extends AbstractToStringBuilder<TestToStringBuilder> {

		protected TestToStringBuilder() {
			super((ContextLoggerConfiguration) null);
		}

		protected TestToStringBuilder(final AbstractToStringBuilder instanceToClone) {
			super(instanceToClone);
		}

		@Override
		public void log(final Object... instancesToLog) {

		}

		@Override
		public void logWithPrefixedMessage(final String prefixedMessage, final Object... instancesToLog) {

		}
	}

	public TraceeContextStringRepresentationBuilder traceeContextLogBuilder = new TraceeContextStringRepresentationBuilder() {

		@Override
		public boolean getEnforceOrder() {
			return false;
		}

		@Override
		public void setEnforceOrder(final boolean keepOrder) {

		}

		@Override
		public String createStringRepresentation(final Object... instancesToProcess) {
			return null;
		}

		@Override
		public void setOutputWriterConfiguration(final OutputWriterConfiguration outputWriterConfiguration) {

		}

		@Override
		public void setManualContextOverrides(final Map<String, Boolean> manualContextOverrides) {

		}

		@Override
		public Map<String, Boolean> getManualContextOverrides() {
			return null;
		}

		@Override
		public TraceeContextStringRepresentationBuilder cloneStringRepresentationBuilder() {
			return null;
		}

		@Override
		public void setProfile(final Profile profile) {

		}

		@Override
		public Profile getProfile() {
			return null;
		}
	};

	@Test
	public void should_use_constructor_correctly() {

		TestToStringBuilder source = new TestToStringBuilder();
		source.setStringRepresentationBuilder(traceeContextLogBuilder);

		final Object[] objArray = {"ABC", "DEF"};
		source.setObjectsToProcess(objArray);

		TestToStringBuilder unit = new TestToStringBuilder(source);

		MatcherAssert.assertThat(unit.getStringRepresentationBuilder(), Matchers.equalTo(source.getStringRepresentationBuilder()));
		MatcherAssert.assertThat(unit.getObjectsToProcess(), Matchers.equalTo(objArray));
		MatcherAssert.assertThat(unit.getContextLoggerConfiguration(), Matchers.nullValue());

	}

	@Test
	public void toString_should_be_forwarded_to_tracee_context_log_builder_correctly() {

		TestToStringBuilder unit = new TestToStringBuilder();

		TraceeContextStringRepresentationBuilder spy = Mockito.spy(traceeContextLogBuilder);

		unit.setStringRepresentationBuilder(spy);

		unit.toString("ABC", "DEF");
		Mockito.verify(spy).createStringRepresentation("ABC", "DEF");

		unit.create("DEF", "GHI");
		Mockito.verify(spy).createStringRepresentation("DEF", "GHI");
	}
}

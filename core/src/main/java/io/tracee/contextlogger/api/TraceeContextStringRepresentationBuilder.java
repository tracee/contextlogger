package io.tracee.contextlogger.api;

import io.tracee.contextlogger.contextprovider.api.Profile;
import io.tracee.contextlogger.outputgenerator.writer.OutputWriterConfiguration;

import java.util.Map;
import java.util.Set;

/**
 * Annotation to mark class as to string representation builder implementations.
 */
public interface TraceeContextStringRepresentationBuilder {

	Set<Class> getWrapperClasses();

	void setWrapperClasses(final Set<Class> wrapperClasses);

	boolean getEnforceOrder();

	void setEnforceOrder(final boolean keepOrder);

	String createStringRepresentation(final Object... instancesToLog);

	void setOutputWriterConfiguration(final OutputWriterConfiguration outputWriterConfiguration);

	void setManualContextOverrides(final Map<String, Boolean> manualContextOverrides);

	Map<String, Boolean> getManualContextOverrides();

	TraceeContextStringRepresentationBuilder cloneStringRepresentationBuilder();

	void setProfile(final Profile profile);

	Profile getProfile();

}

package io.tracee.contextlogger.outputgenerator.api;

import io.tracee.contextlogger.contextprovider.api.Profile;
import io.tracee.contextlogger.outputgenerator.writer.OutputWriterConfiguration;

import java.util.Map;

/**
 * Annotation to mark class as to string representation builder implementations.
 */
public interface TraceeContextStringRepresentationBuilder {

	boolean getEnforceOrder();

	void setEnforceOrder(final boolean keepOrder);

	void setManualContextOverrides(final Map<String, Boolean> manualContextOverrides);

	Map<String, Boolean> getManualContextOverrides();

	void setProfile(final Profile profile);

	Profile getProfile();


	/**
	 * Sets the OutputWriterConfiguration to be used.
	 *
	 * @param outputWriterConfiguration the OutputWriterConfiguration to be used
	 */
	void setOutputWriterConfiguration(final OutputWriterConfiguration outputWriterConfiguration);

	/**
	 * Created a String representation for passed instances.
	 *
	 * @param instancesToProcess the instances to be processed
	 * @return the String representation
	 */
	String createStringRepresentation(final Object... instancesToProcess);

	/**
	 * Clones a TraceeContextStringRepresentationBuilder. Cloning is done by creating a deep copy.
	 *
	 * @return the clone of this instance
	 */
	TraceeContextStringRepresentationBuilder cloneStringRepresentationBuilder();

}

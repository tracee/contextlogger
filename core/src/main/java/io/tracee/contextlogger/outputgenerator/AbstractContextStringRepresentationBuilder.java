package io.tracee.contextlogger.outputgenerator;

import io.tracee.contextlogger.contextprovider.api.Profile;
import io.tracee.contextlogger.outputgenerator.api.TraceeContextStringRepresentationBuilder;
import io.tracee.contextlogger.outputgenerator.writer.BasicOutputWriterConfiguration;
import io.tracee.contextlogger.outputgenerator.writer.OutputWriterConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract base class for all context toJson builder implementations.
 * Created by Tobias Gindler, holisticon AG on 20.03.14.
 */
public abstract class AbstractContextStringRepresentationBuilder implements TraceeContextStringRepresentationBuilder {

	private Profile profile;
	private Map<String, Boolean> manualContextOverrides;
	private boolean keepOrder;
	private OutputWriterConfiguration outputWriterConfiguration;


	public final void setProfile(final Profile profile) {
		this.profile = profile;
	}

	public final Profile getProfile() {
		return this.profile;
	}

	public final void setManualContextOverrides(final Map<String, Boolean> manualContextOverrides) {
		this.manualContextOverrides = manualContextOverrides;
	}

	public final Map<String, Boolean> getManualContextOverrides() {
		return manualContextOverrides != null ? manualContextOverrides : new HashMap<String, Boolean>();
	}

	public final boolean getEnforceOrder() {
		return keepOrder;
	}

	public final void setEnforceOrder(final boolean keepOrder) {
		this.keepOrder = keepOrder;
	}

	public OutputWriterConfiguration getOutputWriterConfiguration() {
		return outputWriterConfiguration != null ? outputWriterConfiguration : BasicOutputWriterConfiguration.JSON_INTENDED;
	}

	public final void setOutputWriterConfiguration(final OutputWriterConfiguration outputWriterConfiguration) {
		this.outputWriterConfiguration = outputWriterConfiguration;
	}

	protected void cloneTo(AbstractContextStringRepresentationBuilder cloneTo) {

		cloneTo.setProfile(profile);
		cloneTo.setManualContextOverrides(new HashMap<String, Boolean>(manualContextOverrides));
		cloneTo.setEnforceOrder(keepOrder);
		cloneTo.setOutputWriterConfiguration(outputWriterConfiguration);

	}

}

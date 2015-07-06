package io.tracee.contextlogger.outputgenerator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.tracee.contextlogger.api.TraceeContextStringRepresentationBuilder;
import io.tracee.contextlogger.contextprovider.api.Profile;
import io.tracee.contextlogger.outputgenerator.writer.BasicOutputWriterConfiguration;
import io.tracee.contextlogger.outputgenerator.writer.OutputWriterConfiguration;

/**
 * Abstract base class for all context toJson builder implementations.
 * Created by Tobias Gindler, holisticon AG on 20.03.14.
 */
public abstract class AbstractContextStringRepresentationBuilder implements TraceeContextStringRepresentationBuilder {

    private Set<Class> wrapperClasses;
    private Profile profile;
    private Map<String, Boolean> manualContextOverrides;
    private boolean keepOrder;
    private OutputWriterConfiguration outputWriterConfiguration;

    @Override
    public final Set<Class> getWrapperClasses() {
        return wrapperClasses;
    }

    @Override
    public final void setWrapperClasses(Set<Class> wrapperClasses) {
        this.wrapperClasses = wrapperClasses;
    }

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

        cloneTo.setWrapperClasses(new HashSet<Class>(wrapperClasses));
        cloneTo.setProfile(profile);
        cloneTo.setManualContextOverrides(new HashMap<String, Boolean>(manualContextOverrides));
        cloneTo.setEnforceOrder(keepOrder);
        cloneTo.setOutputWriterConfiguration(outputWriterConfiguration);

    }

}

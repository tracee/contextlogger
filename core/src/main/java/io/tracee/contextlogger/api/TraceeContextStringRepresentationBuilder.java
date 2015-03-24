package io.tracee.contextlogger.api;

import java.util.Map;
import java.util.Set;

import io.tracee.contextlogger.outputgenerator.writer.OutputWriterConfiguration;
import io.tracee.contextlogger.profile.Profile;

/**
 * Annotation to mark class as toJson builder implementations.
 * Created by Tobias Gindler, holisticon AG on 20.03.14.
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

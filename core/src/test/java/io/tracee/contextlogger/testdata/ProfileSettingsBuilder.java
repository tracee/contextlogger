package io.tracee.contextlogger.testdata;

import java.util.Map;
import java.util.Set;

import io.tracee.contextlogger.api.TraceeContextStringRepresentationBuilder;
import io.tracee.contextlogger.contextprovider.api.Profile;
import io.tracee.contextlogger.outputgenerator.writer.OutputWriterConfiguration;
import io.tracee.contextlogger.profile.ProfileSettings;

/**
 * Helper class for creating ProfileSettings.
 */
public class ProfileSettingsBuilder {

    public static ProfileSettings create(final Profile profile, final Map<String, Boolean> manualContextOverrides) {

        return new ProfileSettings(new TraceeContextStringRepresentationBuilder() {

            @Override
            public Set<Class> getWrapperClasses() {
                return null;
            }

            @Override
            public void setWrapperClasses(final Set<Class> wrapperClasses) {

            }

            @Override
            public boolean getEnforceOrder() {
                return false;
            }

            @Override
            public void setEnforceOrder(final boolean keepOrder) {

            }

            @Override
            public String createStringRepresentation(final Object... instancesToLog) {
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
                return manualContextOverrides;
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
                return profile;
            }
        });

    }

}

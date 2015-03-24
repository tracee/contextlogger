package io.tracee.contextlogger.profile;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import io.tracee.contextlogger.api.TraceeContextStringRepresentationBuilder;

/**
 * This class mixes profile settings with system.property overrides.
 * Created by Tobias Gindler, holisticon AG on 25.03.14.
 */
public class ProfileSettings {

    private List<Properties> profileProperties = null;

    private TraceeContextStringRepresentationBuilder toTraceeContextStringRepresentationBuilder = null;

    /**
     * Main Constructor.
     *
     * @param toTraceeContextStringRepresentationBuilder a map that defines manual overrides for profile settings.
     */
    public ProfileSettings(TraceeContextStringRepresentationBuilder toTraceeContextStringRepresentationBuilder) {
        this(toTraceeContextStringRepresentationBuilder != null && toTraceeContextStringRepresentationBuilder.getProfile() != null
                ? toTraceeContextStringRepresentationBuilder.getProfile() : Profile.getCurrentProfile());

        this.toTraceeContextStringRepresentationBuilder = toTraceeContextStringRepresentationBuilder;

    }

    /**
     * Constructor without manual overrides for convenience.
     *
     * @param profile the profile to use
     */
    public ProfileSettings(Profile profile) {
        try {
            this.profileProperties = profile.getProperties();
        }
        catch (IOException e) {
            // shouldn't occur for non CUSTOM profiles
        }
    }

    public TraceeContextStringRepresentationBuilder getToTraceeContextStringRepresentationBuilder() {
        return toTraceeContextStringRepresentationBuilder;
    }

    /**
     * Checks whether the property for the passed key is enabled or not.
     *
     * @param propertyKey the property key to check
     * @return true, if the property is enabled in profile or manual overrides and not disabled in manual overrides, otherwise false
     */
    public Boolean getPropertyValue(final String propertyKey) {

        if (propertyKey == null) {
            return null;
        }

        // check system property override
        if (toTraceeContextStringRepresentationBuilder != null && toTraceeContextStringRepresentationBuilder.getManualContextOverrides() != null) {
            Boolean manualOverrideCheck = toTraceeContextStringRepresentationBuilder.getManualContextOverrides().get(propertyKey);
            if (manualOverrideCheck != null) {
                return manualOverrideCheck;
            }
        }

        // check profile properties
        if (profileProperties != null) {
            for (final Properties properties : profileProperties) {
                String value = properties.getProperty(propertyKey);
                if (value != null) {
                    return Boolean.valueOf(value);
                }
            }
        }

        return null;
    }

}

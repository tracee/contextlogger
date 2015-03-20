package io.tracee.contextlogger.profile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * This class mixes profile settings with system.property overrides.
 * Created by Tobias Gindler, holisticon AG on 25.03.14.
 */
public class ProfileSettings {

    private List<Properties> profileProperties = null;
    private Map<String, Boolean> manualContextOverrides = null;

    /**
     * Main Constructor.
     *
     * @param profile the profile to use
     * @param manualContextOverrides a map that defines manual overrides for profile settings.
     */
    public ProfileSettings(Profile profile, Map<String, Boolean> manualContextOverrides) {

        // if passed profile is null then use default profile
        Profile tmpProfile = profile != null ? profile : Profile.getCurrentProfile();

        this.manualContextOverrides = manualContextOverrides;

        try {
            this.profileProperties = tmpProfile.getProperties();
        }
        catch (IOException e) {
            // shouldn't occur for non CUSTOM profiles
        }

    }

    /**
     * Constructor without manual overrides for convenience.
     *
     * @param profile the profile to use
     */
    public ProfileSettings(Profile profile) {
        this(profile, new HashMap<String, Boolean>());
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
        if (manualContextOverrides != null) {
            Boolean manualOverrideCheck = manualContextOverrides.get(propertyKey);
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

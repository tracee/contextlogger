package io.tracee.contextlogger.profile;

import io.tracee.contextlogger.api.TraceeContextStringRepresentationBuilder;
import io.tracee.contextlogger.contextprovider.ContextProviderServiceLoader;
import io.tracee.contextlogger.contextprovider.api.Profile;

import java.util.Properties;

/**
 * This class mixes profile settings with system.property overrides.
 * Created by Tobias Gindler, holisticon AG on 25.03.14.
 */
public class ProfileSettings {

	Properties profileProperties = null;

	private TraceeContextStringRepresentationBuilder toTraceeContextStringRepresentationBuilder = null;

	/**
	 * Main Constructor.
	 *
	 * @param toTraceeContextStringRepresentationBuilder a map that defines manual overrides for profile settings.
	 */
	public ProfileSettings(TraceeContextStringRepresentationBuilder toTraceeContextStringRepresentationBuilder) {
		this(toTraceeContextStringRepresentationBuilder != null && toTraceeContextStringRepresentationBuilder.getProfile() != null
				? toTraceeContextStringRepresentationBuilder.getProfile() : ProfileLookup.getCurrentProfile());

		this.toTraceeContextStringRepresentationBuilder = toTraceeContextStringRepresentationBuilder;

	}

	/**
	 * Constructor without manual overrides for convenience.
	 *
	 * @param profile the profile to use
	 */
	public ProfileSettings(Profile profile) {
		this.profileProperties = ContextProviderServiceLoader.getProfileSettings(profile);
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
			String value = profileProperties.getProperty(propertyKey);
			if (value != null) {
				return Boolean.valueOf(value);
			}

		}

		return null;
	}

}

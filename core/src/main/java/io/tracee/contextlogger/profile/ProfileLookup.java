package io.tracee.contextlogger.profile;

import io.tracee.contextlogger.contextprovider.api.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class that offers helper methods for getting the configured Profile
 */
public class ProfileLookup {

	private static final Logger logger = LoggerFactory.getLogger(Profile.class);

	/**
	 * Gets the current profile.
	 * Uses the following algorithm to determine the profile.
	 * 1. If System Property is set force it as profile
	 * 2. If Custom Profile resource can be located in classpath use that file as profile
	 * 3. Use default profile
	 *
	 * @return
	 */
	public static Profile getCurrentProfile() {

		// First get profile from system properties
		Profile profile = getProfileFromSystemProperties();

		// check if profile has been found otherwise try getting profile from file in classpath
		if (profile == null) {
			profile = getProfileFromFileInClasspath(ProfilePropertyNames.PROFILE_SET_BY_FILE_IN_CLASSPATH_FILENAME);
		}

		// use DEFAULT profile, if profile has not been found
		if (profile == null) {
			profile = ProfilePropertyNames.DEFAULT_PROFILE;
		}

		return profile;

	}

	/**
	 * Gets the profile defined via the system properties.
	 *
	 * @return returns the profile defined via the system properties. Returns null if property is not set or set to an invalid value
	 */
	static Profile getProfileFromSystemProperties() {

		Profile result = null;

		// try to get system property
		String systemPropertyProfileName = System.getProperty(ProfilePropertyNames.PROFILE_SET_GLOBALLY_VIA_SYSTEM_PROPERTIES);

		if (systemPropertyProfileName != null) {

			// try to convert String to enum value
			try {
				result = Profile.valueOf(systemPropertyProfileName);
			} catch (IllegalArgumentException e) {
				logger.warn("Tracee ContextLoggerBuilder profile property ('" + ProfilePropertyNames.PROFILE_SET_GLOBALLY_VIA_SYSTEM_PROPERTIES
						+ "') is set to the invalid value '" + systemPropertyProfileName + "' ==> Use default profile");
			}
		}

		return result;
	}

	/**
	 * Gets the profile defined via the system properties.
	 *
	 * @return returns the profile defined via the system properties or {@code null} if property is not set or set to an invalid value.
	 */
	static Profile getProfileFromFileInClasspath(final String filename) {

		Profile result = null;

		try {
			Properties properties = openProperties(filename);
			if (properties != null) {
				String profileFromProperties = properties.getProperty(ProfilePropertyNames.PROFILE_SET_BY_FILE_IN_CLASSPATH_PROPERTY);
				if (profileFromProperties != null) {
					// try to convert String to enum value
					try {
						result = Profile.valueOf(profileFromProperties);
					} catch (IllegalArgumentException e) {
						logger.warn("Tracee custom ContextLoggerBuilder profile property ('"
								+ ProfilePropertyNames.PROFILE_SET_BY_FILE_IN_CLASSPATH_PROPERTY + "') is set to the invalid value '"
								+ profileFromProperties + "' and will be ignored");
					}
				}
			}
		} catch (IOException e) {
			// ignore it
		}

		return result;
	}

	/**
	 * Loads properties from resources.
	 *
	 * @param propertyFileName the property file name to load
	 * @return the properties, or null if the properties can't be found
	 * @throws IOException if property file can't be opened
	 */
	public static Properties openProperties(final String propertyFileName) throws IOException {

		if (propertyFileName == null) {
			return null;
		}

		InputStream inputStream = null;
		try {
			inputStream = Profile.class.getResourceAsStream(propertyFileName);
			if (inputStream != null) {
				// file could be opened
				Properties properties = new Properties();
				properties.load(inputStream);
				return properties;
			} else {
				// file doesn't exist
				return null;
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

}

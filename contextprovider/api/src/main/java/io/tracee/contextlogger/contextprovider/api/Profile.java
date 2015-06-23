package io.tracee.contextlogger.contextprovider.api;

/**
 * enum for profile level.
 */
public enum Profile {

	NONE(null),
	BASIC("_TraceeContextLoggerBasicProfile.properties"),
	ENHANCED("_TraceeContextLoggerEnhancedProfile.properties"),
	FULL("_TraceeContextLoggerFullProfile.properties");

	private final String filename;

	Profile(final String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return this.filename;
	}

}

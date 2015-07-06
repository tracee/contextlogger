package io.tracee.contextlogger.contextprovider.api;

/**
 * enum for profile level.
 */
public enum Profile {

	NONE(null),
	BASIC("TraceeContextLoggerBasicProfile.properties"),
	ENHANCED("TraceeContextLoggerEnhancedProfile.properties"),
	FULL("TraceeContextLoggerFullProfile.properties");

	private final String filename;

	Profile(final String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return this.filename;
	}

}

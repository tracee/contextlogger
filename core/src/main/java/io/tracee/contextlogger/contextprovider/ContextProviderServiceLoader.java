package io.tracee.contextlogger.contextprovider;

import io.tracee.contextlogger.contextprovider.api.Profile;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderServiceProvider;
import io.tracee.contextlogger.profile.ProfileLookup;
import io.tracee.contextlogger.profile.ProfilePropertyNames;
import io.tracee.contextlogger.utility.GenericServiceLocator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Service loader for context providers.
 */
public class ContextProviderServiceLoader implements TraceeContextProviderServiceProvider {

	private static TraceeContextProviderServiceProvider instance = null;

	private final Class[] immplicitContextProviders;
	private final Class[] contextProviders;

	private final Map<Profile, Properties> profilePropertiesMap = new HashMap<Profile, Properties>();

	private ContextProviderServiceLoader() {

		List<TraceeContextProviderServiceProvider> serviceProviders = GenericServiceLocator.locateAll(TraceeContextProviderServiceProvider.class);

		List<Class> implicitContextProviders = new ArrayList<Class>();
		List<Class> contextProviders = new ArrayList<Class>();

		for (TraceeContextProviderServiceProvider serviceProvider : serviceProviders) {
			if (serviceProvider != null) {
				implicitContextProviders.addAll(Arrays.asList(serviceProvider.getImplicitContextProvider()));
				contextProviders.addAll(Arrays.asList(serviceProvider.getContextProvider()));
			}
		}

		this.contextProviders = contextProviders.toArray(new Class[contextProviders.size()]);
		this.immplicitContextProviders = implicitContextProviders.toArray(new Class[implicitContextProviders.size()]);

	}

	@Override
	public Class[] getImplicitContextProvider() {
		return this.immplicitContextProviders;
	}

	@Override
	public Class[] getContextProvider() {
		return this.contextProviders;
	}

	@Override
	public Properties getProfile(final Profile profile) {

		if (profile == null || Profile.NONE.equals(profile)) {
			return new Properties();
		}

		Properties properties = profilePropertiesMap.get(profile);

		if (properties == null) {

			properties = new Properties();

			List<TraceeContextProviderServiceProvider> serviceProviders = GenericServiceLocator.locateAll(TraceeContextProviderServiceProvider.class);

			// load all properties from service providers
			for (TraceeContextProviderServiceProvider serviceProvider : serviceProviders) {
				if (serviceProvider != null) {
					properties.putAll(serviceProvider.getProfile(profile));
				}
			}

			// get custom profiles settings
			properties.putAll(getCustomProperties());

			profilePropertiesMap.put(profile, properties);
		}

		return properties;

	}

	public void clear() {
		profilePropertiesMap.clear();
	}

	private Properties getCustomProperties() {

		Properties properties = null;

		try {
			properties = ProfileLookup.openProperties(getCustomPropertyFileName());
		} catch (IOException e) {
		}

		return properties != null ? properties : new Properties();
	}

	private String getCustomPropertyFileName() {

		String filename = System.getProperty(ProfilePropertyNames.CUSTOM_PROFILE_FILENAME_SET_GLOBALLY_VIA_SYSTEM_PROPERTIES);


		if (filename == null) {
			// load custom profile filename vi file in classpath
			try {
				Properties selectorProperties = ProfileLookup.openProperties(ProfilePropertyNames.PROFILE_SET_BY_FILE_IN_CLASSPATH_FILENAME);
				if (selectorProperties != null) {
					filename = selectorProperties.getProperty(ProfilePropertyNames.CUSTOM_PROFILE_FILENAME_SET_BY_FILE_IN_CLASSPATH_PROPERTY);

				}
			} catch (IOException e) {
			}
		}

		if (filename == null) {
			// load custom profile filename via system property
			filename = ProfilePropertyNames.DEFAULT_CUSTOM_PROFILE_FILE;
		}

		return "/" + filename;
	}

	public static TraceeContextProviderServiceProvider getServiceLocator() {

		if (instance == null) {
			instance = new ContextProviderServiceLoader();
		}
		return instance;

	}

}

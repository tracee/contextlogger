package io.tracee.contextlogger.contextprovider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import io.tracee.contextlogger.contextprovider.api.Profile;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderServiceProvider;
import io.tracee.contextlogger.utility.GenericServiceLocator;

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

			// get custom profiles

            profilePropertiesMap.put(profile, properties);
        }

        return properties;

    }

    public static TraceeContextProviderServiceProvider getServiceLocator() {

        if (instance == null) {
            instance = new ContextProviderServiceLoader();
        }
        return instance;

    }

}

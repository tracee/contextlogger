package io.tracee.contextlogger.contextprovider.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Abstract base class for all {@link TraceeContextProviderServiceProvider}s.
 */
public abstract class AbstractTraceeContextProviderServiceProvider implements TraceeContextProviderServiceProvider {

    protected abstract String getPropertyFilePrefix();

    protected Properties getProfile(String propertyFileName) throws IOException {
        if (propertyFileName == null) {
            return null;
        }

        InputStream inputStream = null;
        try {
            inputStream = AbstractTraceeContextProviderServiceProvider.class.getResourceAsStream("/" + this.getPropertyFilePrefix() + propertyFileName);
            if (inputStream != null) {
                // file could be opened
                Properties properties = new Properties();
                properties.load(inputStream);
                return properties;
            }
            else {
                // file doesn't exist
                return null;
            }
        }
        finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    @Override
    public Properties getProfile(final Profile profile) {
        Properties result = null;

        try {
            if (profile != null) {
                result = getProfile(profile.getFilename());
            }
        }
        catch (Exception e) {
            // ignore
        }

        return result != null ? result : new Properties();
    }
}

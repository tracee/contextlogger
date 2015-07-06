package io.tracee.contextlogger.contextprovider.api;

/**
 * Service provider interface for loading all available service providers
 */
public interface TraceeContextProviderServiceProvider {

	/**
	 * Gets all implicit context provider classes defined by the service provider.
	 *
	 * @return a list that contains all provided implicit context providers
	 */
	Class[] getImplicitContextProvider();

	/**
	 * Gets all context provider classes defined by the service provider.
	 *
	 * @return a list that contains all provided context providers
	 */
	Class[] getContextProvider();


}

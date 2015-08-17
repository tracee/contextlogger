package io.tracee.contextlogger.impl;

import io.tracee.contextlogger.contextprovider.TypeToWrapper;
import io.tracee.contextlogger.contextprovider.api.ImplicitContext;
import io.tracee.contextlogger.contextprovider.api.ImplicitContextData;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A Singleton that holds the static configuration data.
 */
public class ContextLoggerConfiguration {

	private static ContextLoggerConfiguration contextLoggerConfiguration;

	private final Map<Class, Class> classToWrapperMap;
	private final Map<ImplicitContext, Class> implicitContextClassMap;
	private final List<TypeToWrapper> wrapperList;

	/**
	 * Does the initialization stuff like Creating the lookup map and bind the wrapper classes.
	 */
	public ContextLoggerConfiguration() {
		List<TypeToWrapper> tmpWrapperList = TypeToWrapper.getTypeToWrapper();

		Map<ImplicitContext, Class> tmpImplicitContextClassMap = new ConcurrentHashMap<ImplicitContext, Class>();
		Map<Class, Class> tmpClassToWrapperMap = new ConcurrentHashMap<Class, Class>();

		// now iterate over types and fill map
		for (TypeToWrapper wrapper : tmpWrapperList) {
			tmpClassToWrapperMap.put(wrapper.getWrappedInstanceType(), wrapper.getWrapperType());
		}

		Set<ImplicitContextData> implicitContextWrapperClasses = TypeToWrapper.getImplicitContextDataProviders();
		for (ImplicitContextData instance : implicitContextWrapperClasses) {
			if (instance.getImplicitContext() != null) {
				tmpImplicitContextClassMap.put(instance.getImplicitContext(), instance.getClass());
			}
		}

		// Make collections immutable
		wrapperList = Collections.unmodifiableList(tmpWrapperList);
		implicitContextClassMap = Collections.unmodifiableMap(tmpImplicitContextClassMap);
		classToWrapperMap = Collections.unmodifiableMap(tmpClassToWrapperMap);
	}

	public static ContextLoggerConfiguration getOrCreateContextLoggerConfiguration() {
		if (contextLoggerConfiguration == null) {
			contextLoggerConfiguration = new ContextLoggerConfiguration();
		}
		return contextLoggerConfiguration;
	}

	/**
	 * Gets the context provider class for a given type.
	 *
	 * @return The context provider class or null if passed type can't be found.
	 */
	public Class getContextProviderClass(Class type) {
		return classToWrapperMap.get(type);
	}

	/**
	 * Gets the implicit context provider class for passed ImplicitContext.
	 *
	 * @return The implicit context provider class or null if it can't be found
	 */
	public Class getImplicitContextProviderClass(ImplicitContext implicitContext) {
		return implicitContextClassMap.get(implicitContext);
	}


	/**
	 * Gets a list of all non implicit context providers.
	 *
	 * @return all non explicit context providers
	 */
	public List<TypeToWrapper> getWrapperList() {
		return wrapperList;
	}


}

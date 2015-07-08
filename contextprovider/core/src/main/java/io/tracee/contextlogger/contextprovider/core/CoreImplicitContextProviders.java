package io.tracee.contextlogger.contextprovider.core;

import io.tracee.contextlogger.contextprovider.api.ImplicitContext;
import io.tracee.contextlogger.contextprovider.core.tracee.CommonDataContextProvider;
import io.tracee.contextlogger.contextprovider.core.tracee.TraceeMdcContextProvider;

/**
 * Enum that can be used to provide implicit core context providers.
 */
public enum CoreImplicitContextProviders implements ImplicitContext {

	TRACEE(TraceeMdcContextProvider.class),
	COMMON(CommonDataContextProvider.class);

	private final Class implicitContextProviderClass;

	private CoreImplicitContextProviders(final Class implicitContextProviderClass) {
		this.implicitContextProviderClass = implicitContextProviderClass;

	}

}

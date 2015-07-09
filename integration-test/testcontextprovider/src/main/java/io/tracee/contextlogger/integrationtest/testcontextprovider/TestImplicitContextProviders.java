package io.tracee.contextlogger.integrationtest.testcontextprovider;

import io.tracee.contextlogger.contextprovider.api.ImplicitContext;

/**
 * Enum for adding implicit test contextproviders.
 */
public enum TestImplicitContextProviders implements ImplicitContext {

	BROKEN,
	TEST;

}

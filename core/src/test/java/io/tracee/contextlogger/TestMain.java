package io.tracee.contextlogger;

import io.tracee.contextlogger.contextprovider.core.CoreImplicitContextProviders;

/**
 * Created by TGI on 30.01.2015.
 */
public class TestMain {

	public static void main(String[] args) {

		TraceeContextLogger.createDefault().logWithPrefixedMessage("ABC", "DEF", CoreImplicitContextProviders.COMMON, CoreImplicitContextProviders.TRACEE);
		TraceeContextLogger.createDefault().log("ABC", "DEF", CoreImplicitContextProviders.COMMON, CoreImplicitContextProviders.TRACEE);

	}

}

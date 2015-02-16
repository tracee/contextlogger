package io.tracee.contextlogger;

import io.tracee.contextlogger.api.ImplicitContext;

/**
 * Created by TGI on 30.01.2015.
 */
public class TestMain {

    public static void main(String[] args) {

        TraceeContextLogger.createDefault().logJsonWithPrefixedMessage("ABC", "DEF", ImplicitContext.COMMON, ImplicitContext.TRACEE);
		TraceeContextLogger.createDefault().logJson("ABC", "DEF", ImplicitContext.COMMON, ImplicitContext.TRACEE);

    }

}

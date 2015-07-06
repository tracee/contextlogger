package io.tracee.contextlogger.integrationtest.testcontextprovider;

/**
 * Test class which should be wrapped by {@link TestContextDataWrapper}.
 */
public class WrappedTestContextData {

	public static final String OUTPUT = "IT WORKS !!!";

	public String getOutput() {
		return OUTPUT;
	}


}

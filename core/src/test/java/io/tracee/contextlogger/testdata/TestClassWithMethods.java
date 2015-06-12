package io.tracee.contextlogger.testdata;

import io.tracee.contextlogger.contextprovider.api.Flatten;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderMethod;

/**
 * Created by Tobias Gindler, holisticon AG on 14.03.14.
 */
@TraceeContextProvider(displayName = "TEST")
public class TestClassWithMethods {

    public void methodWithNoParameters() {

    }

    public void methodWithParameters(int a, int b) {

    }

    public void methodWithVoidReturnType() {

    }

    public String methodWithNonVoidReturnType() {
        return "OK";
    }

    private void privateMethod() {
    }

    void packagePrivateMethod() {
    }

    protected void protectedMethod() {
    }

    @TraceeContextProviderMethod(displayName = "test")
    @Flatten
    public String flattenTest() {
        return null;
    }

}

package io.tracee.contextlogger.output.internal.testclasses;

/**
 * TestClass for wrapping instances in tracee context providers for unit tests.
 */
public class WrappedInstanceInTraceeContextProviderTestClass {

    private final Throwable throwable;

    public WrappedInstanceInTraceeContextProviderTestClass() {

        Throwable tmpThrowable = null;
        try {
            throw new NullPointerException();
        }
        catch (Exception e) {
            tmpThrowable = e;
        }
        throwable = tmpThrowable;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}

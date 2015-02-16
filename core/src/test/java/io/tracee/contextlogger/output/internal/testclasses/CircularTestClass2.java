package io.tracee.contextlogger.output.internal.testclasses;

/**
 * Test class to check for circular dependency handling.
 */
public class CircularTestClass2 {

    private CircularTestClass1 other;

    public CircularTestClass2(CircularTestClass1 other) {
        this.other = other;
    }

    public CircularTestClass1 getOther() {
        return other;
    }

    public void setOther(final CircularTestClass1 other) {
        this.other = other;
    }
}

package io.tracee.contextlogger.output.internal.testclasses;

/**
 * Test class to check for alreadyprocessed dependency handling.
 */
public class CircularTestClass1 {

    private CircularTestClass2 other;

    CircularTestClass1() {

    }

    public CircularTestClass2 getOther() {
        return other;
    }

    public void setOther(final CircularTestClass2 other) {
        this.other = other;
    }

    public static CircularTestClass1 builder() {

        CircularTestClass1 tc1 = new CircularTestClass1();
        CircularTestClass2 tc2 = new CircularTestClass2(tc1);
        tc1.setOther(tc2);

        return tc1;

    }

}

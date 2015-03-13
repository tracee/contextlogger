package io.tracee.contextlogger.output.internal.testclasses;

import java.util.List;

/**
 * Test class for testing behavior of null valued references
 */
public class NullValuedReferencesTestClass {

    private String nullValuedAtomic = null;
    private String nullValuedMap = null;
    private String[] nullValuedArray = null;
    private List<String> nullValuedList = null;

    public String[] getNullValuedArray() {
        return nullValuedArray;
    }

    public String getNullValuedAtomic() {
        return nullValuedAtomic;
    }

    public List<String> getNullValuedList() {
        return nullValuedList;
    }

    public String getNullValuedMap() {
        return nullValuedMap;
    }
}

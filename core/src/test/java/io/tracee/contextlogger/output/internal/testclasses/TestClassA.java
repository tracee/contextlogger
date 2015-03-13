package io.tracee.contextlogger.output.internal.testclasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Test class for unit tests
 */
public class TestClassA {

    public static final String FIELD_A = "FIELD_A";

    private Map<String, TestClassB> map = new HashMap<String, TestClassB>();

    private List<String> list = new ArrayList<String>();

    private String fieldA = FIELD_A;

    private TraceeContextProviderTestClass traceeContextProviderTestClass;

    public TestClassA() {

        // fill map
        map.put("A", new TestClassB("A"));
        map.put("B", new TestClassB("B"));
        map.put("C", new TestClassB("C"));

        // fill list
        list.add("A");
        list.add("B");
        list.add("C");

        // context provider
        traceeContextProviderTestClass = new TraceeContextProviderTestClass("DUMMY");

    }

    public String getFieldA() {
        return fieldA;
    }

    public void setFieldA(final String fieldA) {
        this.fieldA = fieldA;
    }

    public Map<String, TestClassB> getMap() {
        return map;
    }

    public void setMap(final Map<String, TestClassB> map) {
        this.map = map;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(final List<String> list) {
        this.list = list;
    }

	public TraceeContextProviderTestClass getTraceeContextProviderTestClass() {
		return traceeContextProviderTestClass;
	}

	public void setTraceeContextProviderTestClass(final TraceeContextProviderTestClass traceeContextProviderTestClass) {
		this.traceeContextProviderTestClass = traceeContextProviderTestClass;
	}
}

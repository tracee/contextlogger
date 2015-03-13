package io.tracee.contextlogger.output.internal.testclasses;

/**
 * Test class for bean tests.
 */
public class BeanTestClass {

	public static final String VALID_GETTER_FIELD_VALUE = "ABC";

	private String validGetterField = VALID_GETTER_FIELD_VALUE;
	private String getterFieldWithParameter;
	private String getterFieldWithReturnTypeVoid;
	private Integer getterFieldWithMismatchingTypes;

	public String getValidGetterField() {
		return validGetterField;
	}

	public String getGetterFieldWithParameter(String arg) {
		return getterFieldWithParameter;
	}

	public void getGetterFieldWithReturnTypeVoid() {

	}

	public String getGetterFieldWithMismatchingTypes() {
		return "";
	}

	public String getGetterWithoutCorrespondingField() {
		return "";
	}

	public String wtfValidGetterField() {
		return validGetterField;
	}

}

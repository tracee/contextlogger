package io.tracee.contextlogger.utility;

import java.lang.reflect.Field;

/**
 * Utility class to access fields via reflection.
 */
public final class FieldUtilities {

	/**
	 * Hidden constructor.
	 */
	private FieldUtilities() {
	}

	/**
	 * @param clazz     the clazz to look in
	 * @param fieldName the name of the field
	 * @return the field or null, if no field with passed name can be found
	 * @throws IllegalArgumentException if clazz or fieldName parameters is null
	 */
	public static Field getField(Class clazz, String fieldName) throws IllegalArgumentException {

		if (clazz == null || fieldName == null) {
			throw new IllegalArgumentException("Class and fieldname parameters must not be null");
		}

		// check if field is in the class
		Field field = null;
		try {
			field = clazz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			// not found in this class, should check parent
		}

		// check superclass if field couldn't be found
		Class superClass = clazz.getSuperclass();
		if (field == null && superClass != null) {
			field = getField(superClass, fieldName);
		}

		return field;
	}

}

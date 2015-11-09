package io.tracee.contextlogger.outputgenerator.functions;

import io.tracee.contextlogger.outputgenerator.RecursiveOutputElementTreeBuilder;
import io.tracee.contextlogger.outputgenerator.RecursiveOutputElementTreeBuilderState;
import io.tracee.contextlogger.outputgenerator.outputelements.ComplexOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.NullValueOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;
import io.tracee.contextlogger.outputgenerator.utility.BeanUtility;
import io.tracee.contextlogger.utility.FieldUtilities;
import io.tracee.contextlogger.utility.GetterUtilities;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Transforms a given bean to an {@link ComplexOutputElement}.
 */
public class BeanToOutputElementTransformerFunction extends ToComplexOutputElementTransformerFunction<Object> {

	private static final BeanToOutputElementTransformerFunction instance = new BeanToOutputElementTransformerFunction();

	public static BeanToOutputElementTransformerFunction getInstance() {
		return instance;
	}

	@Override
	public OutputElement apply(final RecursiveOutputElementTreeBuilder recursiveOutputElementTreeBuilder,
							   final RecursiveOutputElementTreeBuilderState state, final Object instance) {

		if (instance == null) {
			return NullValueOutputElement.INSTANCE;
		}
		ComplexOutputElement complexOutputElement = new ComplexOutputElement(instance.getClass(), instance);

		if (recursiveOutputElementTreeBuilder.checkIfInstanceIsAlreadyRegistered(complexOutputElement)) {
			return recursiveOutputElementTreeBuilder.getRegisteredOutputElementAndMarkItAsMultipleRegistered(complexOutputElement);
		}

		// must register output element before processing children to prevent problems with alreadyprocessed references
		recursiveOutputElementTreeBuilder.registerOutputElement(complexOutputElement);

		// get internal fields with matching getter
		for (Method method : BeanUtility.getGetterMethodsRecursively(instance.getClass())) {

			complexOutputElement.addOutputElement(GetterUtilities.getFieldName(method),
					recursiveOutputElementTreeBuilder.convertInstanceRecursively(state, invokeField(instance, method)));

		}

		return complexOutputElement;

	}

	/**
	 * Invokes the getter and return the return values
	 *
	 * @param instance the instance to invoke the getter method on.
	 * @param method   the method to invoke
	 * @return the return value of the getter invocation
	 */
	protected Object invokeGetter(final Object instance, final Method method) {
		try {
			return method.invoke(instance);
		} catch (IllegalAccessException e) {
			// shouldn't occur
		} catch (InvocationTargetException e) {
			// shouldn't occur
		}

		return null;
	}

	/**
	 * Invokes the getter and return the return values
	 *
	 * @param instance the instance to invoke the getter method on.
	 * @param method   the method to invoke
	 * @return the return value of the getter invocation
	 */
	protected Object invokeField(final Object instance, final Method method) {
		if (instance == null || method == null) {
			return null;
		}

		try {
			Field field = FieldUtilities.getField(instance.getClass(), GetterUtilities.getFieldName(method));
			field.setAccessible(true);
			return field.get(instance);
		} catch (IllegalAccessException e) {
			// shouldn't occur
		} catch (IllegalArgumentException e) {
			// ignore
		}

		return null;
	}

}

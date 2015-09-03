package io.tracee.contextlogger.outputgenerator.outputelements;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for processing complex element types that are defined by a single value.
 */
public class ComplexOutputElement extends AbstractOutputElement {

	public static class Entry {

		private final String key;
		private final OutputElement childOutputElement;

		public Entry(String key, OutputElement childOutputElement) {
			this.key = key;
			this.childOutputElement = childOutputElement;
		}

		public String getKey() {
			return key;
		}

		public OutputElement getChildOutputElement() {
			return childOutputElement;
		}
	}

	private List<ComplexOutputElement.Entry> outputElements = new ArrayList<Entry>();

	/**
	 * Constructor.
	 */
	public ComplexOutputElement(Class type, Object instance) {
		super(type, instance);
	}

	@Override
	public boolean isEmpty() {
		return outputElements.size() == 0;
	}

	@Override
	public OutputElementType getOutputElementType() {
		return OutputElementType.COMPLEX;
	}

	public void addOutputElement(String name, OutputElement outputElement) {
		this.outputElements.add(new Entry(name, outputElement));
	}

	public List<Entry> getOutputElements() {
		return outputElements;
	}

}

package io.tracee.contextlogger.outputgenerator.outputelements;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for processing complex element types that are defined by a single value.
 */
public class ComplexOutputElement extends AbstractOutputElement {

    private Map<String, OutputElement> outputElements = new HashMap<String, OutputElement>();

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
        this.outputElements.put(name, outputElement);
    }

    public Map<String, OutputElement> getOutputElements() {
        return outputElements;
    }

}

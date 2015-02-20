package io.tracee.contextlogger.output.internal.outputelements;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for processing collection like element types like Lists.
 */
public class CollectionOutputElement extends AbstractOutputElement {

    private List<OutputElement> outputElements = new ArrayList<OutputElement>();

    /**
     * Constructor.
     */
    public CollectionOutputElement(Class type, Object instance) {
        super(type, instance);
    }

    @Override
    public boolean isEmpty() {
        return outputElements.isEmpty();
    }

    @Override
    public OutputElementType getOutputElementType() {
        return OutputElementType.COLLECTION;
    }

    public void addElement(final OutputElement outputElement) {

        this.outputElements.add(outputElement);

    }

    public List<OutputElement> getOutputElements() {
        return this.outputElements;
    }

}

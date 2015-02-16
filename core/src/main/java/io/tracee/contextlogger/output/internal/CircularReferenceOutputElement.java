package io.tracee.contextlogger.output.internal;

/**
 * Pseudo type for handling circular references.
 */
public class CircularReferenceOutputElement extends AbstractOutputElement {

    private final Object instance;

    /**
     * Constructor.
     *
     * @param instance
     */
    public CircularReferenceOutputElement(Class type, Object instance) {

        super(type);
        this.instance = instance;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public OutputElementType getOutputElementType() {
        return OutputElementType.CIRCULAR_REFERENCE;
    }

    public Object getInstance() {
        return this.instance;
    }

}

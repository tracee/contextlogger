package io.tracee.contextlogger.output.internal;

/**
 * Class for processing atomic element types that are defined by a single value.
 */
public class AtomicOutputElement extends AbstractOutputElement {

    private final Object value;

    /**
     * Constructor.
     *
     * @param value
     */
    public AtomicOutputElement(Class type, Object value) {

        super(type);
        this.value = value;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public OutputElementType getOutputElementType() {
        return OutputElementType.ATOMIC;
    }

    public Object getValue() {
        return this.value;
    }

}

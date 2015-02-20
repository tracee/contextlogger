package io.tracee.contextlogger.output.internal.outputelements;

/**
 * Class for processing atomic element types that are defined by a single value.
 */
public class AtomicOutputElement extends AbstractOutputElement {

    /**
     * Constructor.
     *
     * @param encapsulatedInstanc
     */
    public AtomicOutputElement(Class type, Object encapsulatedInstanc) {

        super(type, encapsulatedInstanc);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public OutputElementType getOutputElementType() {
        return OutputElementType.ATOMIC;
    }

}

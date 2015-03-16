package io.tracee.contextlogger.outputgenerator.outputelements;

/**
 * Abstract base class for output element.
 */
public abstract class AbstractOutputElement implements OutputElement {

    private final Class outputElementsBaseType;
    private final int identityHashCode;

    private final Object encapsulatedInstance;
    private boolean isMarkedAsMultipleReferenced = false;

    /**
     * Constructor.
     *
     * @param outputElementsBaseType the output elements base type
     * @param instance the encapsulated instance
     */
    protected AbstractOutputElement(Class outputElementsBaseType, Object instance) {
        this.outputElementsBaseType = outputElementsBaseType;
        identityHashCode = System.identityHashCode(instance);
        encapsulatedInstance = instance;
    }

    @Override
    public Class getOutputElementsBaseType() {
        return outputElementsBaseType;
    }

    @Override
    public int getIdentityHashCode() {
        return identityHashCode;
    }

    @Override
    public Object getEncapsulatedInstance() {
        return this.encapsulatedInstance;
    }

    @Override
    public void setIsMarkedAsMultipleReferenced() {
        this.isMarkedAsMultipleReferenced = true;
    }

    @Override
    public boolean getIsAsMarkedAsMultipleReferenced() {
        return this.isMarkedAsMultipleReferenced;
    }
}

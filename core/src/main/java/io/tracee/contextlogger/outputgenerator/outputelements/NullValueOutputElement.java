package io.tracee.contextlogger.outputgenerator.outputelements;

/**
 * Class for handling null values.
 */
public class NullValueOutputElement implements OutputElement {

    public final static NullValueOutputElement INSTANCE = new NullValueOutputElement();

    /**
     * Hidden constructor.
     */
    private NullValueOutputElement() {

    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public OutputElementType getOutputElementType() {
        return OutputElementType.NULL;
    }

    @Override
    public Class getOutputElementsBaseType() {
        return null;
    }

    @Override
    public int getIdentityHashCode() {
        return 0;
    }

    @Override
    public Object getEncapsulatedInstance() {
        return null;
    }

    @Override
    public void setIsMarkedAsMultipleReferenced() {
        // nothing to do
    }

    @Override
    public boolean getIsAsMarkedAsMultipleReferenced() {
        // always return false
        return false;
    }

    public boolean useReferencesIfMarkedAsMultipleReferenced() {
        return false;
    }

}

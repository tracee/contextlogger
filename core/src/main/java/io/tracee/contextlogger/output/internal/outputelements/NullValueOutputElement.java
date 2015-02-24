package io.tracee.contextlogger.output.internal.outputelements;

/**
 * Class for handling null values.
 */
public class NullValueOutputElement implements OutputElement {

    public NullValueOutputElement() {

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
}

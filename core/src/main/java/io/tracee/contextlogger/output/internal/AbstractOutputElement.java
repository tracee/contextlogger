package io.tracee.contextlogger.output.internal;

/**
 * Abstract base class for output element.
 */
public abstract class AbstractOutputElement implements OutputElement {

    private final Class outputElementsBaseType;

    protected AbstractOutputElement(Class outputElementsBaseType) {
        this.outputElementsBaseType = outputElementsBaseType;
    }

    @Override
    public Class getOutputElementsBaseType() {
        return outputElementsBaseType;
    }

}

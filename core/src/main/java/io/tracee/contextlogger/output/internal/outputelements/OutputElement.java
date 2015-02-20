package io.tracee.contextlogger.output.internal.outputelements;

/**
 * Base interface of all OutputElement types.
 */
public interface OutputElement {

    /**
     * Checks whether value is set or object has at least on element
     *
     * @return
     */
    boolean isEmpty();

    /**
     * Gets the type of this element.
     *
     * @return
     */
    OutputElementType getOutputElementType();

    /**
     * Gets the base (original) type of the output element.
     *
     * @return
     */
    Class getOutputElementsBaseType();

    /**
     * Gets the identity hash code of the instance represented by the output element.
     *
     * @return the identity hash code of the instance represented by the output element
     */
    int getIdentityHashCode();

    /**
     * Gets the encapsulated instance.
     *
     * @return the encapsulated instance
     */
    Object getEncapsulatedInstance();

}

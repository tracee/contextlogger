package io.tracee.contextlogger.outputgenerator.outputelements;

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

    /**
     * Marks the element as referenced by multiple other OutputElements.
     */
    void setIsMarkedAsMultipleReferenced();

    /**
     * Returns if the OutputElement is marked as referenced by multiple other OutputElements.
     *
     * @return true, if the OutputElement is marked as referenced by multiple other OutputElements
     */
    boolean getIsAsMarkedAsMultipleReferenced();

}

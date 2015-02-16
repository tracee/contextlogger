package io.tracee.contextlogger.output.internal;

/**
 * Created by TGI on 06.02.2015.
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

}

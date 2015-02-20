package io.tracee.contextlogger.output.internal;

import io.tracee.contextlogger.output.internal.outputelements.OutputElement;

/**
 * Interface for recursively building the deserialization tree.
 */
public interface RecursiveContextDeserializer {

    /**
     * Method that is called recursively to create the deserialization tree
     *
     * @param instanceToDeserialize the instance ro be processed
     * @return the output element
     */
    OutputElement convertInstanceRecursively(final Object instanceToDeserialize);

    /**
     * Registers the passed output ellement in the InstanceToOutputElementPool.
     *
     * @param outputElement the output element to be registered
     */
    void registerOutputElement(final OutputElement outputElement);

    /**
     * Checks if instance is already registered.
     *
     * @param instance The instance to be checked
     */
    boolean checkIfInstanceIsAlreadyRegistered(final Object instance);

    /**
     * Checks if instance is already registered
     *
     * @param instance the instance to get the output element for
     */
    OutputElement getRegisteredOutputElement(final Object instance);
}

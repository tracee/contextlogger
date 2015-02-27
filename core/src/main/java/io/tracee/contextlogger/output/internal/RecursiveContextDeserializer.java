package io.tracee.contextlogger.output.internal;

import io.tracee.contextlogger.output.internal.outputelements.OutputElement;
import io.tracee.contextlogger.profile.ProfileSettings;

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
     * @param outputElement The output element to be checked
     */
    boolean checkIfInstanceIsAlreadyRegistered(final OutputElement outputElement);

    /**
     * Checks if instance is already registered
     *
     * @param outputElement the outputElement instance to get the existing output element for
     */
    OutputElement getRegisteredOutputElement(final OutputElement outputElement);

    /**
     * Allows access to profile settings
     *
     * @param profileSettings
     * @return
     */
    ProfileSettings getProfileSettings(final ProfileSettings profileSettings);

}

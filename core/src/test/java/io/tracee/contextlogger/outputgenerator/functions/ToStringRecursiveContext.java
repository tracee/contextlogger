package io.tracee.contextlogger.outputgenerator.functions;

import java.util.HashMap;

import io.tracee.contextlogger.outputgenerator.RecursiveOutputElementTreeBuilderState;
import io.tracee.contextlogger.outputgenerator.RecursiveOutputElementTreeBuilder;
import io.tracee.contextlogger.outputgenerator.outputelements.AtomicOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.NullValueOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;
import io.tracee.contextlogger.profile.Profile;
import io.tracee.contextlogger.profile.ProfileSettings;

/**
 * Recursive Context for testing purposes.
 */
public class ToStringRecursiveContext implements RecursiveOutputElementTreeBuilder {

    @Override
    public OutputElement convertInstanceRecursively(final RecursiveOutputElementTreeBuilderState state, final Object instanceToDeserialize) {
        return instanceToDeserialize != null ? new AtomicOutputElement(instanceToDeserialize.getClass(), instanceToDeserialize)
                : NullValueOutputElement.INSTANCE;
    }

    @Override
    public void registerOutputElement(final OutputElement outputElement) {

    }

    @Override
    public boolean checkIfInstanceIsAlreadyRegistered(final OutputElement outputElement) {
        return false;
    }

    @Override
    public OutputElement getRegisteredOutputElementAndMarkItAsMultipleRegistered(final OutputElement outputElement) {
        return null;
    }

    @Override
    public ProfileSettings getProfileSettings() {
        return new ProfileSettings(Profile.BASIC, new HashMap<String, Boolean>());
    }
}

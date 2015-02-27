package io.tracee.contextlogger.output.internal.functions;

import java.util.HashMap;

import io.tracee.contextlogger.output.internal.RecursiveOutputElementTreeBuilder;
import io.tracee.contextlogger.output.internal.outputelements.AtomicOutputElement;
import io.tracee.contextlogger.output.internal.outputelements.NullValueOutputElement;
import io.tracee.contextlogger.output.internal.outputelements.OutputElement;
import io.tracee.contextlogger.profile.Profile;
import io.tracee.contextlogger.profile.ProfileSettings;

/**
 * Recursive Context for testing purposes.
 */
public class ToStringRecursiveContext implements RecursiveOutputElementTreeBuilder {

    @Override
    public OutputElement convertInstanceRecursively(final Object instanceToDeserialize) {
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
    public OutputElement getRegisteredOutputElement(final OutputElement outputElement) {
        return null;
    }

    @Override
    public ProfileSettings getProfileSettings() {
        return new ProfileSettings(Profile.BASIC, new HashMap<String, Boolean>());
    }
}

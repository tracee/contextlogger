package io.tracee.contextlogger.outputgenerator.functions;

import java.util.HashMap;
import java.util.Map;

import io.tracee.contextlogger.outputgenerator.RecursiveOutputElementTreeBuilder;
import io.tracee.contextlogger.outputgenerator.RecursiveOutputElementTreeBuilderState;
import io.tracee.contextlogger.outputgenerator.outputelements.AtomicOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.NullValueOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;
import io.tracee.contextlogger.profile.Profile;
import io.tracee.contextlogger.profile.ProfileSettings;

/**
 * Recursive Context for testing purposes.
 */
public class ToStringRecursiveContext implements RecursiveOutputElementTreeBuilder {

    Map<Integer, OutputElement> outputElementLookup = new HashMap<Integer, OutputElement>();

    @Override
    public OutputElement convertInstanceRecursively(final RecursiveOutputElementTreeBuilderState state, final Object instanceToDeserialize) {
        return instanceToDeserialize != null ? new AtomicOutputElement(instanceToDeserialize.getClass(), instanceToDeserialize)
                : NullValueOutputElement.INSTANCE;
    }

    @Override
    public void registerOutputElement(final OutputElement outputElement) {
        outputElementLookup.put(outputElement.getIdentityHashCode(), outputElement);
    }

    @Override
    public boolean checkIfInstanceIsAlreadyRegistered(final OutputElement outputElement) {
        return outputElementLookup.containsKey(outputElement.getIdentityHashCode());
    }

    @Override
    public OutputElement getRegisteredOutputElementAndMarkItAsMultipleRegistered(final OutputElement outputElement) {
        OutputElement tmpOutputElement = outputElementLookup.get(outputElement.getIdentityHashCode());
        if (tmpOutputElement != null) {
            tmpOutputElement.setIsMarkedAsMultipleReferenced();
        }
        return tmpOutputElement;
    }

    @Override
    public ProfileSettings getProfileSettings() {
        return new ProfileSettings(Profile.BASIC);
    }
}

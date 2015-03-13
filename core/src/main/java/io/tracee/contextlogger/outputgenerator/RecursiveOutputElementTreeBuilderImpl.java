package io.tracee.contextlogger.outputgenerator;

import java.util.Collection;
import java.util.Map;

import io.tracee.Tracee;
import io.tracee.TraceeLogger;
import io.tracee.contextlogger.impl.ContextLoggerConfiguration;
import io.tracee.contextlogger.outputgenerator.functions.*;
import io.tracee.contextlogger.outputgenerator.outputelements.NullValueOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;
import io.tracee.contextlogger.outputgenerator.predicates.IsBeanTypePredicate;
import io.tracee.contextlogger.outputgenerator.predicates.IsCollectionTypePredicate;
import io.tracee.contextlogger.outputgenerator.predicates.IsMapTypePredicate;
import io.tracee.contextlogger.outputgenerator.predicates.IsTraceeContextProviderPredicate;
import io.tracee.contextlogger.profile.ProfileSettings;

/**
 * Creates output element tree for the passed instance recursively. Acts as scheduler for ToOutputTransformerFunctions invocations.
 */
public class RecursiveOutputElementTreeBuilderImpl implements RecursiveOutputElementTreeBuilder {

    private static final TraceeLogger logger = Tracee.getBackend().getLoggerFactory().getLogger(RecursiveOutputElementTreeBuilderImpl.class);

    private final InstanceToOutputElementPool instanceToOutputElementPool;
    private final ContextLoggerConfiguration contextLoggerConfiguration;
    private final ProfileSettings profileSettings;

    public RecursiveOutputElementTreeBuilderImpl(final ProfileSettings profileSettings) {
        this.profileSettings = profileSettings;
        this.instanceToOutputElementPool = new InstanceToOutputElementPool();
        this.contextLoggerConfiguration = ContextLoggerConfiguration.getOrCreateContextLoggerConfiguration();
    }

    @Override
    public OutputElement convertInstanceRecursively(final RecursiveOutputElementTreeBuilderState state, final Object passedInstanceToDeserialize) {

        OutputElement outputElement = null;

        if (passedInstanceToDeserialize == null) {

            // handle null value
            outputElement = NullValueOutputElement.INSTANCE;

        }
        else if (state.maxDepthReached()) {
            // fallback deserialize instance as atomic value
            outputElement = AtomicToOutputElementTransformerFunction.getInstance().apply(this, state.next(),
                    TraceeContextProviderWrapperFunction.getInstance().apply(contextLoggerConfiguration, passedInstanceToDeserialize));

        }
        else {

            // wraps passed instance in tracee context provider if possible or otherwise returns the passed instance
            Object instanceToDeserialize = TraceeContextProviderWrapperFunction.getInstance()
                    .apply(contextLoggerConfiguration, passedInstanceToDeserialize);

            if (IsCollectionTypePredicate.getInstance().apply(instanceToDeserialize)) {
                // handle arrays and collections

                if (instanceToDeserialize.getClass().isArray()) {
                    outputElement = ArrayToOutputElementTransformerFunction.getInstance().apply(this, state.next(), (Object[])instanceToDeserialize);
                }
                else {
                    outputElement = CollectionToOutputElementTransformerFunction.getInstance().apply(this, state.next(), (Collection)instanceToDeserialize);
                }

            }
            else if (IsMapTypePredicate.getInstance().apply(instanceToDeserialize)) {

                outputElement = MapToOutputElementTransformerFunction.getInstance().apply(this, state.next(), (Map)instanceToDeserialize);

            }
            else if (IsTraceeContextProviderPredicate.getInstance().apply(instanceToDeserialize)) {

                outputElement = TraceeContextProviderToOutputElementTransformerFunction.getInstance().apply(this, state.next(), instanceToDeserialize);

            }
            else if (IsBeanTypePredicate.getInstance().apply(instanceToDeserialize)) {

                outputElement = BeanToOutputElementTransformerFunction.getInstance().apply(this, state.next(), instanceToDeserialize);

            }
            else {
                // fallback deserialize instance as atomic value
                outputElement = AtomicToOutputElementTransformerFunction.getInstance().apply(this, state.next(), instanceToDeserialize);
            }

        }
        return outputElement;
    }

    @Override
    public void registerOutputElement(final OutputElement outputElement) {

        if (outputElement != null && outputElement.getEncapsulatedInstance() != null) {
            this.instanceToOutputElementPool.add(outputElement);
        }
    }

    @Override
    public boolean checkIfInstanceIsAlreadyRegistered(final OutputElement outputElement) {

        return outputElement != null && this.instanceToOutputElementPool.isInstanceMarkedAsProcessed(outputElement);

    }

    @Override
    public OutputElement getRegisteredOutputElementAndMarkItAsMultipleRegistered(final OutputElement outputElement) {
        OutputElement alreadyRegisteredOutputElement = this.instanceToOutputElementPool.getOutputElement(outputElement);
        if (alreadyRegisteredOutputElement != null) {
            alreadyRegisteredOutputElement.setIsMarkedAsMultipleReferenced();
        }
        return alreadyRegisteredOutputElement;
    }

    @Override
    public ProfileSettings getProfileSettings() {
        return this.profileSettings;
    }
}

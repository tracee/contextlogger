package io.tracee.contextlogger.output.internal;

import java.util.Collection;
import java.util.Map;

import io.tracee.Tracee;
import io.tracee.TraceeLogger;
import io.tracee.contextlogger.impl.ContextLoggerConfiguration;
import io.tracee.contextlogger.output.internal.functions.*;
import io.tracee.contextlogger.output.internal.outputelements.NullValueOutputElement;
import io.tracee.contextlogger.output.internal.outputelements.OutputElement;
import io.tracee.contextlogger.output.internal.predicates.IsBeanTypePredicate;
import io.tracee.contextlogger.output.internal.predicates.IsCollectionTypePredicate;
import io.tracee.contextlogger.output.internal.predicates.IsMapTypePredicate;
import io.tracee.contextlogger.output.internal.predicates.IsTraceeContextProviderPredicate;
import io.tracee.contextlogger.profile.ProfileSettings;

/**
 * Create deserialization tree for a single instance recursively. Acts as scheduler for ToOutputTransformerFunctions invocations.
 */
public class SingleInstanceContextDeserializer implements RecursiveContextDeserializer {

    private static final TraceeLogger logger = Tracee.getBackend().getLoggerFactory().getLogger(SingleInstanceContextDeserializer.class);

    private final InstanceToOutputElementPool instanceToOutputElementPool;
    private final ContextLoggerConfiguration contextLoggerConfiguration;
    private final ProfileSettings profileSettings;

    public SingleInstanceContextDeserializer(final ProfileSettings profileSettings) {
        this.profileSettings = profileSettings;
        this.instanceToOutputElementPool = new InstanceToOutputElementPool();
        this.contextLoggerConfiguration = ContextLoggerConfiguration.getOrCreateContextLoggerConfiguration();
    }

    public OutputElement convertInstanceRecursively(final Object passedInstanceToDeserialize) {

        OutputElement outputElement = null;

        if (passedInstanceToDeserialize == null) {

            // handle null value
            outputElement = NullValueOutputElement.INSTANCE;

        }
        else {

            // wraps passed instance in tracee context provider if possible or otherwise returns the passed instance
            Object instanceToDeserialize = TraceeContextProviderWrapperFunction.getInstance()
                    .apply(contextLoggerConfiguration, passedInstanceToDeserialize);

            if (IsCollectionTypePredicate.getInstance().apply(instanceToDeserialize)) {
                // handle arrays and collections

                if (instanceToDeserialize.getClass().isArray()) {
                    outputElement = ArrayToOutputElementTransformerFunction.getInstance().apply(this, (Object[])instanceToDeserialize);
                }
                else {
                    outputElement = CollectionToOutputElementTransformerFunction.getInstance().apply(this, (Collection)instanceToDeserialize);
                }

            }
            else if (IsMapTypePredicate.getInstance().apply(instanceToDeserialize)) {

                outputElement = MapToOutputElementTransformerFunction.getInstance().apply(this, (Map)instanceToDeserialize);

            }
            else if (IsTraceeContextProviderPredicate.getInstance().apply(instanceToDeserialize)) {

                outputElement = TraceeContextProviderToOutputElementTransformerFunction.getInstance().apply(this, instanceToDeserialize);

            }
            else if (IsBeanTypePredicate.getInstance().apply(instanceToDeserialize)) {

                outputElement = BeanToOutputElementTransformerFunction.getInstance().apply(this, instanceToDeserialize);

            }
            else {
                // fallback deserialize instance as atomic value
                outputElement = AtomicToOutputElementTransformerFunction.getInstance().apply(this, instanceToDeserialize);
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
    public OutputElement getRegisteredOutputElement(final OutputElement outputElement) {
        return this.instanceToOutputElementPool.getOutputElement(outputElement);
    }

    @Override
    public ProfileSettings getProfileSettings(final ProfileSettings profileSettings) {
        return this.profileSettings;
    }
}

package io.tracee.contextlogger.output.internal;

import java.util.Collection;
import java.util.Map;

import io.tracee.Tracee;
import io.tracee.TraceeLogger;
import io.tracee.contextlogger.output.internal.functions.*;
import io.tracee.contextlogger.output.internal.outputelements.OutputElement;
import io.tracee.contextlogger.output.internal.predicates.IsBeanTypePredicate;
import io.tracee.contextlogger.output.internal.predicates.IsCollectionTypePredicate;
import io.tracee.contextlogger.output.internal.predicates.IsMapTypePredicate;
import io.tracee.contextlogger.output.internal.predicates.IsTraceeContextProviderPredicate;

/**
 * Create deserialization tree for a single instance recursively. Acts as scheduler for ToOutputTransformerFunctions invocations.
 */
public class SingleInstanceContextDeserializer implements RecursiveContextDeserializer {

    private static final TraceeLogger logger = Tracee.getBackend().getLoggerFactory().getLogger(SingleInstanceContextDeserializer.class);

    private final InstanceToOutputElementPool instanceToOutputElementPool;

    public SingleInstanceContextDeserializer() {
        this.instanceToOutputElementPool = new InstanceToOutputElementPool();
    }

    public OutputElement convertInstanceRecursively(final Object instanceToDeserialize) {

        OutputElement outputElement = null;

        if (instanceToDeserialize == null) {

            // handle null value
            outputElement = AtomicToOutputElementTransformerFunction.getInstance().apply(this, instanceToDeserialize);

        }
        else {

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
            this.instanceToOutputElementPool.add(outputElement.getEncapsulatedInstance(), outputElement);
        }
    }

    @Override
    public boolean checkIfInstanceIsAlreadyRegistered(final Object instance) {

        return instance != null && this.instanceToOutputElementPool.isInstanceMarkedAsProcessed(instance);

    }

    @Override
    public OutputElement getRegisteredOutputElement(final Object instance) {
        return this.instanceToOutputElementPool.getOutputElement(instance);
    }
}

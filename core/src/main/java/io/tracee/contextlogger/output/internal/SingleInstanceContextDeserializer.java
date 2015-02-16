package io.tracee.contextlogger.output.internal;

import java.util.Collection;
import java.util.Map;

import io.tracee.Tracee;
import io.tracee.TraceeLogger;
import io.tracee.contextlogger.output.internal.functions.*;
import io.tracee.contextlogger.output.internal.predicates.IsBeanTypePredicate;
import io.tracee.contextlogger.output.internal.predicates.IsCollectionTypePredicate;
import io.tracee.contextlogger.output.internal.predicates.IsMapTypePredicate;
import io.tracee.contextlogger.output.internal.predicates.IsTraceeContextProviderPredicate;

/**
 * Create deserialization tree for a single instance recursively. Acts as scheduler for ToOutputTransformerFunctions invocations.
 */
public class SingleInstanceContextDeserializer implements RecursiveContextDeserializer {

    private static final TraceeLogger logger = Tracee.getBackend().getLoggerFactory().getLogger(SingleInstanceContextDeserializer.class);

    private final DeserializationState deserializationState;

    public SingleInstanceContextDeserializer() {
        this.deserializationState = new DeserializationState();
    }

    public OutputElement convertInstanceRecursively(final Object instanceToDeserialize) {

        if (instanceToDeserialize == null) {
            // handle null value
            return AtomicToOutputElementTransformerFunction.getInstance().apply(this, instanceToDeserialize);
        }
        else if (IsCollectionTypePredicate.getInstance().apply(instanceToDeserialize)) {
            // handle arrays and collections

            if (!deserializationState.instanceAlreadyExists(instanceToDeserialize)) {
                deserializationState.add(instanceToDeserialize);
                if (instanceToDeserialize.getClass().isArray()) {
                    return ArrayToOutputElementTransformerFunction.getInstance().apply(this, (Object[])instanceToDeserialize);
                }
                else {
                    return CollectionToOutputElementTransformerFunction.getInstance().apply(this, (Collection)instanceToDeserialize);
                }
            }
            else {
                return new CircularReferenceOutputElement(instanceToDeserialize.getClass(), instanceToDeserialize);
            }

        }
        else if (IsMapTypePredicate.getInstance().apply(instanceToDeserialize)) {

            if (!deserializationState.instanceAlreadyExists(instanceToDeserialize)) {
                // handle Map
                deserializationState.add(instanceToDeserialize);
                return MapToOutputElementTransformerFunction.getInstance().apply(this, (Map)instanceToDeserialize);
            }
            else {
                return new CircularReferenceOutputElement(instanceToDeserialize.getClass(), instanceToDeserialize);
            }

        }
        else if (IsTraceeContextProviderPredicate.getInstance().apply(instanceToDeserialize)) {

            if (!deserializationState.instanceAlreadyExists(instanceToDeserialize)) {
                // handle tracee context provider
                deserializationState.add(instanceToDeserialize);
                return TraceeContextProviderToOutputElementTransformerFunction.getInstance().apply(this, instanceToDeserialize);
            }
            else {
                return new CircularReferenceOutputElement(instanceToDeserialize.getClass(), instanceToDeserialize);
            }

        }
        else if (IsBeanTypePredicate.getInstance().apply(instanceToDeserialize)) {

            if (!deserializationState.instanceAlreadyExists(instanceToDeserialize)) {

                // handle bean instance
                deserializationState.add(instanceToDeserialize);
                return BeanToOutputElementTransformerFunction.getInstance().apply(this, instanceToDeserialize);
            }
            else {
                return new CircularReferenceOutputElement(instanceToDeserialize.getClass(), instanceToDeserialize);
            }

        }
        else {
            // fallback deserialize instance as atomic value
            return AtomicToOutputElementTransformerFunction.getInstance().apply(this, instanceToDeserialize);
        }

    }
}

package io.tracee.contextlogger.output.internal;

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
    public OutputElement convertInstanceRecursively(final Object instanceToDeserialize);

}

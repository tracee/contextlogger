package io.tracee.contextlogger.output.internal.writer.api;

/**
 * Public interface for all writers of {@link io.tracee.contextlogger.output.internal.outputelements.ComplexOutputElement}s.
 */
public interface NodeOutputElementWriter<T> {

    /**
     * Produces output for the passed AtomicOutputElement
     *
     * @param outputWriterInstance the output writer instance to handle recursive creation
     * @param stringBuilder the string builder to write the output to
     * @param outputStyle the output style to use
     * @param nodeOutputElement the AtomicOutputElement to be processed
     */
    void produceOutput(OutputWriter outputWriterInstance, StringBuilder stringBuilder, final OutputStyle outputStyle, T nodeOutputElement);

}

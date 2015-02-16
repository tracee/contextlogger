package io.tracee.contextlogger.output.internal.writer;

import org.junit.Before;
import org.junit.Test;

import io.tracee.contextlogger.output.internal.ContextDeserializer;
import io.tracee.contextlogger.output.internal.OutputElement;
import io.tracee.contextlogger.output.internal.testclasses.TestClassA;
import io.tracee.contextlogger.output.internal.writer.atomic.AtomicOutputElementWriter;
import io.tracee.contextlogger.output.internal.writer.atomic.TypedWithInstanceIdToStringAtomicOutputElementWriter;
import io.tracee.contextlogger.output.internal.writer.collection.CollectionOutputElementWriter;
import io.tracee.contextlogger.output.internal.writer.collection.SimpleCollectionOutputElementWriter;
import io.tracee.contextlogger.output.internal.writer.complex.ComplexOutputElementWriter;
import io.tracee.contextlogger.output.internal.writer.complex.SimpleComplexOutputElementWriter;
import io.tracee.contextlogger.output.internal.writer.styles.OutputStyle;
import io.tracee.contextlogger.output.internal.writer.styles.json.IntendedJsonOutputStyle;
import io.tracee.contextlogger.output.internal.writer.styles.json.SimpleJsonOutputStyle;

/**
 * Unit test for {@link io.tracee.contextlogger.output.internal.writer.OutputWriterToOutputTransformer}.
 */
public class OutputWriterToOutputTansformerTest {

    private OutputStyle intendedJsonOutputStyle;
    private OutputStyle simpleJsonOutputStyle;
    private OutputElement outputElement;
    private AtomicOutputElementWriter atomicOutputElementWriter;
    private ComplexOutputElementWriter complexOutputElementWriter;
    private CollectionOutputElementWriter collectionOutputElementWriter;

    @Before
    public void init() {
        intendedJsonOutputStyle = new IntendedJsonOutputStyle();
        simpleJsonOutputStyle = new SimpleJsonOutputStyle();
        outputElement = ContextDeserializer.deserializeContexts(new TestClassA());
        atomicOutputElementWriter = new TypedWithInstanceIdToStringAtomicOutputElementWriter();
        collectionOutputElementWriter = new SimpleCollectionOutputElementWriter();
        complexOutputElementWriter = new SimpleComplexOutputElementWriter();
    }

    @Test
    public void should_build_intended_json_string_representation_correctly() {

        String result = OutputWriterToOutputTransformer.produceOutput(intendedJsonOutputStyle, complexOutputElementWriter, collectionOutputElementWriter,
                atomicOutputElementWriter, outputElement);

        System.out.println(result);

    }

    @Test
    public void should_build_simple_json_string_representation_correctly() {

        String result = OutputWriterToOutputTransformer.produceOutput(simpleJsonOutputStyle, complexOutputElementWriter, collectionOutputElementWriter,
                atomicOutputElementWriter, outputElement);

        System.out.println(result);

    }

}

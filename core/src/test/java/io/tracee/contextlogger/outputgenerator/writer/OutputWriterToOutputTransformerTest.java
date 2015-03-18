package io.tracee.contextlogger.outputgenerator.writer;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.tracee.contextlogger.outputgenerator.outputelements.*;
import io.tracee.contextlogger.outputgenerator.writer.api.*;
import io.tracee.contextlogger.outputgenerator.writer.styles.json.SimpleJsonOutputStyle;

/**
 * test class for {@link io.tracee.contextlogger.outputgenerator.writer.OutputWriterToOutputTransformer}.
 */
public class OutputWriterToOutputTransformerTest {

    private static final OutputStyle OUTPUT_STYLE = new SimpleJsonOutputStyle();

    private OutputWriterToOutputTransformer unit;
    private OutputWriterConfiguration outputWriterConfiguration;
    private AlreadyProcessedReferenceOutputElementWriter alreadyProcessedReferenceOutputElementWriter;
    private ComplexOutputElementWriter complexOutputElementWriter;
    private CollectionOutputElementWriter collectionOutputElementWriter;
    private AtomicOutputElementWriter atomicOutputElementWriter;
    private NullValueOutputElementWriter nullValueOutputElementWriter;

    @Before
    public void init() {

        outputWriterConfiguration = Mockito.mock(OutputWriterConfiguration.class);
        alreadyProcessedReferenceOutputElementWriter = Mockito.mock(AlreadyProcessedReferenceOutputElementWriter.class);
        atomicOutputElementWriter = Mockito.mock(AtomicOutputElementWriter.class);
        complexOutputElementWriter = Mockito.mock(ComplexOutputElementWriter.class);
        collectionOutputElementWriter = Mockito.mock(CollectionOutputElementWriter.class);
        nullValueOutputElementWriter = Mockito.mock(NullValueOutputElementWriter.class);

        Mockito.when(outputWriterConfiguration.getAlreadyProcessedReferenceOutputElementWriter()).thenReturn(alreadyProcessedReferenceOutputElementWriter);
        Mockito.when(outputWriterConfiguration.getCollectionOutputElementWriter()).thenReturn(collectionOutputElementWriter);
        Mockito.when(outputWriterConfiguration.getComplexOutputElementWriter()).thenReturn(complexOutputElementWriter);
        Mockito.when(outputWriterConfiguration.getAtomicOutputElementWriter()).thenReturn(atomicOutputElementWriter);
        Mockito.when(outputWriterConfiguration.getNullValueOutputElementWriter()).thenReturn(nullValueOutputElementWriter);

        unit = new OutputWriterToOutputTransformer();
        unit.init(outputWriterConfiguration);
    }

    @Test
    public void should_output_reference_for_already_processed_output_element() {

        OutputElement outputElement = new ComplexOutputElement(OutputWriterToOutputTransformerTest.class, this);

        unit.outputElementPool.add(outputElement);

        StringBuilder stringBuilder = new StringBuilder();

        unit.produceOutputRecursively(stringBuilder, OUTPUT_STYLE, outputElement);

        Mockito.verify(alreadyProcessedReferenceOutputElementWriter).produceOutput(outputElement);

    }

    @Test
    public void should_output_complex_output_element() {

        OutputElement outputElement = new ComplexOutputElement(OutputWriterToOutputTransformerTest.class, this);

        StringBuilder stringBuilder = new StringBuilder();

        unit.produceOutputRecursively(stringBuilder, OUTPUT_STYLE, outputElement);

        Mockito.verify(complexOutputElementWriter).produceOutput(unit, stringBuilder, OUTPUT_STYLE, (ComplexOutputElement)outputElement);

    }

    @Test
    public void should_output_collection_output_element() {

        OutputElement outputElement = new CollectionOutputElement(ArrayList.class, new ArrayList<String>());

        StringBuilder stringBuilder = new StringBuilder();

        unit.produceOutputRecursively(stringBuilder, OUTPUT_STYLE, outputElement);

        Mockito.verify(collectionOutputElementWriter).produceOutput(unit, stringBuilder, OUTPUT_STYLE, (CollectionOutputElement)outputElement);

    }

    @Test
    public void should_output_atomic_output_element() {

        OutputElement outputElement = new AtomicOutputElement(String.class, "ABC");

        StringBuilder stringBuilder = new StringBuilder();

        unit.produceOutputRecursively(stringBuilder, OUTPUT_STYLE, outputElement);

        Mockito.verify(atomicOutputElementWriter).produceOutput((AtomicOutputElement)outputElement);

    }

    @Test
    public void should_output_null_output_element() {

        OutputElement outputElement = NullValueOutputElement.INSTANCE;

        StringBuilder stringBuilder = new StringBuilder();

        unit.produceOutputRecursively(stringBuilder, OUTPUT_STYLE, outputElement);

        Mockito.verify(nullValueOutputElementWriter).produceOutput(NullValueOutputElement.INSTANCE);

    }
}

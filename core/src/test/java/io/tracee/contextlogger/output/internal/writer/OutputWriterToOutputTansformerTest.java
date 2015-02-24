package io.tracee.contextlogger.output.internal.writer;

import io.tracee.contextlogger.output.internal.writer.atomic.AtomicOutputElementWriter;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import io.tracee.contextlogger.output.internal.ContextDeserializer;
import io.tracee.contextlogger.output.internal.outputelements.OutputElement;
import io.tracee.contextlogger.output.internal.testclasses.CircularTestClass1;
import io.tracee.contextlogger.output.internal.testclasses.NullValuedReferencesTestClass;
import io.tracee.contextlogger.output.internal.testclasses.TestClassA;
import io.tracee.contextlogger.output.internal.writer.atomic.TypedWithInstanceIdToStringAtomicOutputElementWriter;
import io.tracee.contextlogger.output.internal.writer.collection.CollectionOutputElementWriter;
import io.tracee.contextlogger.output.internal.writer.collection.SimpleCollectionOutputElementWriter;
import io.tracee.contextlogger.output.internal.writer.complex.ComplexOutputElementWriter;
import io.tracee.contextlogger.output.internal.writer.complex.SimpleComplexOutputElementWriter;
import io.tracee.contextlogger.output.internal.writer.styles.OutputStyle;
import io.tracee.contextlogger.output.internal.writer.styles.json.IntendedJsonOutputStyle;
import io.tracee.contextlogger.output.internal.writer.styles.json.SimpleJsonOutputStyle;
import io.tracee.contextlogger.util.test.RegexMatcher;

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

        MatcherAssert
                .assertThat(
                        result,
                        new RegexMatcher(
                                "\\{\"fieldA\":\"String@\\d+\\['FIELD_A']\",\"traceeContextProviderTestClass\":\\{\"dummyString\":\"String@\\d+\\['DUMMY']\"},\"map\":\\{\"A\":\\{\"fieldB\":\"String@\\d+\\['A']\"},\"B\":\\{\"fieldB\":\"String@\\d+\\['B']\"},\"C\":\\{\"fieldB\":\"String@\\d+\\['C']\"}},\"list\":\\[\"String@\\d+\\['A']\",\"String@\\d+\\['B']\",\"String@\\d+\\['C']\"]}"));

        /*
         * MatcherAssert.assertThat(result, new RegexMatcher("\\{\\w+\"fieldA\":\"String@\\d+\\['FIELD_A'\\]\",\\w+"
         * + "\"traceeContextProviderTestClass\":\\{\\w+" + "\"dummyString\":\"String@\\d+\\['DUMMY'\\]\"\\w+" + "},\\w+\"map\":\\{\\w+"
         * + "\"A\":\\{\\w+" + "\"fieldB\":\"String@\\d+\\['A'\\]\"\\w+" + "},\\w+" + "\"B\":\\{\\w+" + "\"fieldB\":\"String@\\d+\\['B'\\]\"\\w+"
         * + "},\\w+" + "\"C\":\\{\\w+" + "\"fieldB\":\"String@\\d+\\['C'\\]\"\\w+" + "}\\w+" + "},\\w" + "  \"list\":\\[\\w+"
         * + "\"String@\\d+\\['A'\\]\",\\w+" + "\"String@\\d+\\['B'\\]\",\\w+" + "\"String@\\d+\\['C'\\]\"\\w+" + "\\]\\w+" + "\\}"));
         */

    }

    @Test
    public void should_build_recursive_json_string_representation_correctly() {

        OutputElement tmp = ContextDeserializer.deserializeContexts(CircularTestClass1.builder());

        String result = OutputWriterToOutputTransformer.produceOutput(simpleJsonOutputStyle, complexOutputElementWriter, collectionOutputElementWriter,
                atomicOutputElementWriter, tmp);

        MatcherAssert.assertThat(result, new RegexMatcher("\\{\"other\":\\{\"other\":\"see CircularTestClass1@\\d+\"}}"));

    }

    @Test
    public void should_build_null_valued_references_in_representation_correctly() {

        OutputElement tmp = ContextDeserializer.deserializeContexts(new NullValuedReferencesTestClass());

        String result = OutputWriterToOutputTransformer.produceOutput(simpleJsonOutputStyle, complexOutputElementWriter, collectionOutputElementWriter,
                atomicOutputElementWriter, tmp);

        System.out.println(result);
        // MatcherAssert.assertThat(result, new RegexMatcher("\\{\"other\":\\{\"other\":\"see CircularTestClass1@\\d+\"}}"));

    }

}

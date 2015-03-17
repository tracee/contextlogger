package io.tracee.contextlogger.outputgenerator.writer.collection;

import java.util.List;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import io.tracee.contextlogger.outputgenerator.outputelements.AtomicOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.CollectionOutputElement;
import io.tracee.contextlogger.outputgenerator.writer.TestOutputElementWriter;
import io.tracee.contextlogger.outputgenerator.writer.styles.json.SimpleJsonOutputStyle;
import io.tracee.contextlogger.util.test.RegexMatcher;

/**
 * Unit test for {@link io.tracee.contextlogger.outputgenerator.writer.collection.SimpleCollectionOutputElementWriter}.
 */
public class SimpleCollectionOutputElementWriterTest {

    private TestOutputElementWriter outputWriter;

    private SimpleCollectionOutputElementWriter unit;

    @Before
    public void init() {
        outputWriter = new TestOutputElementWriter();
        unit = new SimpleCollectionOutputElementWriter();
    }

    @Test
    public void should_process_collection_correctly() {

        List<String> givenCollection = Lists.newArrayList("ABC");

        CollectionOutputElement givenCollectionOutputElement = new CollectionOutputElement(givenCollection.getClass(), givenCollection);
        givenCollectionOutputElement.addElement(new AtomicOutputElement(String.class, "ABC"));
        StringBuilder stringBuilder = new StringBuilder();

        unit.produceOutput(outputWriter, stringBuilder, new SimpleJsonOutputStyle(), givenCollectionOutputElement);

        MatcherAssert.assertThat(stringBuilder.toString(), RegexMatcher.matches("\\[\"TYPE:ArrayList\",ABC]"));

    }

    @Test
    public void should_process_collection_with_id_correctly() {

        List<String> givenCollection = Lists.newArrayList("ABC");

        CollectionOutputElement givenCollectionOutputElement = new CollectionOutputElement(givenCollection.getClass(), givenCollection);
        givenCollectionOutputElement.addElement(new AtomicOutputElement(String.class, "ABC"));
        givenCollectionOutputElement.setIsMarkedAsMultipleReferenced();
        StringBuilder stringBuilder = new StringBuilder();

        unit.produceOutput(outputWriter, stringBuilder, new SimpleJsonOutputStyle(), givenCollectionOutputElement);

        MatcherAssert.assertThat(stringBuilder.toString(), RegexMatcher.matches("\\[\"TYPE:ArrayList@\\d+\",ABC]"));

    }
}

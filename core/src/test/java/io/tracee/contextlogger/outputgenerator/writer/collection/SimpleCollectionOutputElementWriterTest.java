package io.tracee.contextlogger.outputgenerator.writer.collection;

import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.Lists;

import io.tracee.contextlogger.outputgenerator.outputelements.AtomicOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.CollectionOutputElement;
import io.tracee.contextlogger.outputgenerator.writer.TestOutputElementWriter;
import io.tracee.contextlogger.outputgenerator.writer.styles.json.SimpleJsonOutputStyle;

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
    @Ignore
    public void should_process_collection_correctly() {

        List<String> givenCollection = Lists.newArrayList("ABC", "DEF", "GHI");

        CollectionOutputElement givenCollectionOutputElement = new CollectionOutputElement(givenCollection.getClass(), givenCollection);
        givenCollectionOutputElement.addElement(new AtomicOutputElement(String.class, "ABC"));
        givenCollectionOutputElement.addElement(new AtomicOutputElement(String.class, "DEF"));
        givenCollectionOutputElement.addElement(new AtomicOutputElement(String.class, "GHI"));
        StringBuilder stringBuilder = new StringBuilder();

        unit.produceOutput(outputWriter, stringBuilder, new SimpleJsonOutputStyle(), givenCollectionOutputElement);

        MatcherAssert.assertThat(stringBuilder.toString(), Matchers.is("[ABC,DEF,GHI]"));

    }
}

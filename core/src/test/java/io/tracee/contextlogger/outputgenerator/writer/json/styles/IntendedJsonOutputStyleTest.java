package io.tracee.contextlogger.outputgenerator.writer.json.styles;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.outputgenerator.writer.styles.json.IntendedJsonOutputStyle;

/**
 * Test class for {@link io.tracee.contextlogger.outputgenerator.writer.json.styles.IntendedJsonOutputStyleTest}.
 */
public class IntendedJsonOutputStyleTest {

    @Test
    public void should_get_child_configuration() {

        IntendedJsonOutputStyle result = (IntendedJsonOutputStyle)new IntendedJsonOutputStyle().getChildConfiguration();
        MatcherAssert.assertThat(result.getIndent(), Matchers.is(2));

    }

    @Test
    public void should_create_indent_correctly() {
        IntendedJsonOutputStyle intendedJsonOutputStyle = new IntendedJsonOutputStyle();
        MatcherAssert.assertThat(intendedJsonOutputStyle.openingComplexType(), Matchers.is("{\n  "));
        MatcherAssert.assertThat(intendedJsonOutputStyle.closingComplexType(), Matchers.is("\n" + "}"));

        MatcherAssert.assertThat(intendedJsonOutputStyle.complexTypeOpeningName(), Matchers.is("\""));

        MatcherAssert.assertThat(intendedJsonOutputStyle.complexTypeClosingName(), Matchers.is("\""));

        MatcherAssert.assertThat(intendedJsonOutputStyle.complexTypeNameValueSeparator(), Matchers.is(":"));

        MatcherAssert.assertThat(intendedJsonOutputStyle.complexTypeElementSeparator(), Matchers.is(",\n" + "  "));

        MatcherAssert.assertThat(intendedJsonOutputStyle.openingCollectionType(), Matchers.is("[\n" + "  "));

        MatcherAssert.assertThat(intendedJsonOutputStyle.closingCollectionType(), Matchers.is("\n" + "]"));

        MatcherAssert.assertThat(intendedJsonOutputStyle.collectionTypeElementSeparator(), Matchers.is(",\n" + "  "));

        MatcherAssert.assertThat(intendedJsonOutputStyle.openingAtomicType(), Matchers.is("\""));

        MatcherAssert.assertThat(intendedJsonOutputStyle.closingAtomicType(), Matchers.is("\""));

    }

    @Test
    public void should_create_other_indent_correctly() {
        IntendedJsonOutputStyle intendedJsonOutputStyle = (IntendedJsonOutputStyle)new IntendedJsonOutputStyle().getChildConfiguration();
        MatcherAssert.assertThat(intendedJsonOutputStyle.openingComplexType(), Matchers.is("{\n    "));
        MatcherAssert.assertThat(intendedJsonOutputStyle.closingComplexType(), Matchers.is("\n" + "  " + "}"));

        MatcherAssert.assertThat(intendedJsonOutputStyle.complexTypeOpeningName(), Matchers.is("\""));

        MatcherAssert.assertThat(intendedJsonOutputStyle.complexTypeClosingName(), Matchers.is("\""));

        MatcherAssert.assertThat(intendedJsonOutputStyle.complexTypeNameValueSeparator(), Matchers.is(":"));

        MatcherAssert.assertThat(intendedJsonOutputStyle.complexTypeElementSeparator(), Matchers.is(",\n" + "    "));

        MatcherAssert.assertThat(intendedJsonOutputStyle.openingCollectionType(), Matchers.is("[\n" + "    "));

        MatcherAssert.assertThat(intendedJsonOutputStyle.closingCollectionType(), Matchers.is("\n" + "  " + "]"));

        MatcherAssert.assertThat(intendedJsonOutputStyle.collectionTypeElementSeparator(), Matchers.is(",\n" + "    "));

        MatcherAssert.assertThat(intendedJsonOutputStyle.openingAtomicType(), Matchers.is("\""));

        MatcherAssert.assertThat(intendedJsonOutputStyle.closingAtomicType(), Matchers.is("\""));

    }
}

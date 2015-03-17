package io.tracee.contextlogger.outputgenerator.writer.json.styles;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import io.tracee.contextlogger.outputgenerator.writer.api.OutputStyle;
import io.tracee.contextlogger.outputgenerator.writer.styles.json.JsonOutputStyle;

/**
 * Test class for {@link io.tracee.contextlogger.outputgenerator.writer.styles.json.JsonOutputStyle}.
 */
public class JsonOutputStyleTest {

    JsonOutputStyle jsonOutputStyle;

    @Before
    public void init() {
        jsonOutputStyle = new JsonOutputStyle() {

            @Override
            public OutputStyle getChildConfiguration() {
                return null;
            }
        };
    }

    @Test
    public void should_escape_null_or_empty_string_correctly() {

        MatcherAssert.assertThat(jsonOutputStyle.escapeString(null), Matchers.is("\"\""));
        MatcherAssert.assertThat(jsonOutputStyle.escapeString(""), Matchers.is("\"\""));

    }

    @Test
    public void should_escape_string_correctly() {

        final String givenString = "\\ \" / \b \t \n \f \r ü ABCD" + (char)27;
        String result = jsonOutputStyle.escapeString(givenString);

        MatcherAssert.assertThat(result, Matchers.is("\\\\ \\\" \\/ \\b \\t \\n \\f \\r ü ABCD \\u001b"));

    }

    @Test
    public void should_return_opening_and_closing_parts_correctly() {

        MatcherAssert.assertThat(jsonOutputStyle.openingComplexType(), Matchers.is("{"));
        MatcherAssert.assertThat(jsonOutputStyle.closingComplexType(), Matchers.is("}"));
        MatcherAssert.assertThat(jsonOutputStyle.complexTypeOpeningName(), Matchers.is("\""));
        MatcherAssert.assertThat(jsonOutputStyle.complexTypeClosingName(), Matchers.is("\""));
        MatcherAssert.assertThat(jsonOutputStyle.complexTypeNameValueSeparator(), Matchers.is(":"));
        MatcherAssert.assertThat(jsonOutputStyle.complexTypeElementSeparator(), Matchers.is(","));
        MatcherAssert.assertThat(jsonOutputStyle.openingCollectionType(), Matchers.is("["));
        MatcherAssert.assertThat(jsonOutputStyle.closingCollectionType(), Matchers.is("]"));
        MatcherAssert.assertThat(jsonOutputStyle.collectionTypeElementSeparator(), Matchers.is(","));
        MatcherAssert.assertThat(jsonOutputStyle.openingAtomicType(), Matchers.is("\""));
        MatcherAssert.assertThat(jsonOutputStyle.closingAtomicType(), Matchers.is("\""));

    }

}

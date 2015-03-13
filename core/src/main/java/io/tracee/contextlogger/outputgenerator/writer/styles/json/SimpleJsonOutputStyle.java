package io.tracee.contextlogger.outputgenerator.writer.styles.json;

/**
 * Simple output style. Write the output into a single line without formatting the output.
 */
public class SimpleJsonOutputStyle extends JsonOutputStyle {

    @Override
    public SimpleJsonOutputStyle getChildConfiguration() {
        return this;
    }

}

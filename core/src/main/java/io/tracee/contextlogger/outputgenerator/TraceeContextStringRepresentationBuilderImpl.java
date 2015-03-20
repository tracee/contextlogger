package io.tracee.contextlogger.outputgenerator;

import io.tracee.contextlogger.api.TraceeContextStringRepresentationBuilder;
import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;
import io.tracee.contextlogger.outputgenerator.writer.OutputWriterToOutputTransformer;
import io.tracee.contextlogger.profile.ProfileSettings;

/**
 * Main class for creating output with given OutputWriterConfiguration.
 */
public class TraceeContextStringRepresentationBuilderImpl extends AbstractContextStringRepresentationBuilder {

    public TraceeContextStringRepresentationBuilderImpl() {
    }

    @Override
    public String createStringRepresentation(final Object... instancesToLog) {
        OutputElement tmp = RootOutputElementTreeBuilder.buildOutputElementTree(new ProfileSettings(this.getProfile(), this.getManualContextOverrides()),
                instancesToLog);

        return OutputWriterToOutputTransformer.produceOutput(this.getOutputWriterConfiguration(), tmp);
    }

    @Override
    public TraceeContextStringRepresentationBuilder cloneStringRepresentationBuilder() {
        TraceeContextStringRepresentationBuilderImpl clone = new TraceeContextStringRepresentationBuilderImpl();
        this.cloneTo(clone);
        return clone;
    }

}

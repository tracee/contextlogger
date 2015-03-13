package io.tracee.contextlogger.outputgenerator;

import io.tracee.contextlogger.impl.AbstractContextStringRepresentationBuilder;
import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;
import io.tracee.contextlogger.outputgenerator.writer.OutputWriterToOutputTransformer;
import io.tracee.contextlogger.profile.ProfileSettings;

/**
 * Main class for creating output with given OutputWriterConfiguration.
 */
public class TraceeContextStringRepresentationBuilder extends AbstractContextStringRepresentationBuilder {


    public TraceeContextStringRepresentationBuilder() {
    }

    @Override
    public String createStringRepresentation(final Object... instancesToLog) {
        OutputElement tmp = RootOutputElementTreeBuilder.deserializeContexts(new ProfileSettings(this.getProfile(), this.getManualContextOverrides()),
				instancesToLog);

        return OutputWriterToOutputTransformer.produceOutput(this.getOutputWriterConfiguration(), tmp);
    }



}

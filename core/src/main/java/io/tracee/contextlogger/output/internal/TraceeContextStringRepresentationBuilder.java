package io.tracee.contextlogger.output.internal;

import io.tracee.contextlogger.impl.AbstractContextStringRepresentationBuilder;
import io.tracee.contextlogger.output.internal.outputelements.OutputElement;
import io.tracee.contextlogger.output.internal.writer.OutputWriterConfiguration;
import io.tracee.contextlogger.output.internal.writer.OutputWriterToOutputTransformer;
import io.tracee.contextlogger.profile.ProfileSettings;

/**
 * Main class for creating output with given OutputWriterConfiguration.
 */
public class TraceeContextStringRepresentationBuilder extends AbstractContextStringRepresentationBuilder {


    public TraceeContextStringRepresentationBuilder() {
    }

    @Override
    public String createStringRepresentation(final Object... instancesToLog) {
        OutputElement tmp = ContextDeserializer.deserializeContexts(new ProfileSettings(this.getProfile(), this.getManualContextOverrides()),
                instancesToLog);

        return OutputWriterToOutputTransformer.produceOutput(this.getOutputWriterConfiguration(), tmp);
    }



}

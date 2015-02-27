package io.tracee.contextlogger.api;

import java.util.Set;

import io.tracee.contextlogger.output.internal.writer.OutputWriterConfiguration;

/**
 * Annotation to mark class as toJson builder implementations.
 * Created by Tobias Gindler, holisticon AG on 20.03.14.
 */
public interface TraceeContextStringRepresentationBuilder {

    Set<Class> getWrapperClasses();

    void setWrapperClasses(final Set<Class> wrapperClasses);

    boolean getKeepOrder();

    void setKeepOrder(final boolean keepOrder);

    String createStringRepresentation(final Object... instancesToLog);

    void setOutputWriterConfiguration(final OutputWriterConfiguration outputWriterConfiguration);

}

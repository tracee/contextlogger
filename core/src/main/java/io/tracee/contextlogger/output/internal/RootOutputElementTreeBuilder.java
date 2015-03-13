package io.tracee.contextlogger.output.internal;

import io.tracee.contextlogger.output.internal.outputelements.AtomicOutputElement;
import io.tracee.contextlogger.output.internal.outputelements.CollectionOutputElement;
import io.tracee.contextlogger.output.internal.outputelements.OutputElement;
import io.tracee.contextlogger.profile.ProfileSettings;

/**
 * Converts given instances to an OutputElement instance hierarchy.
 */
public class RootOutputElementTreeBuilder {

    private final RecursiveOutputElementTreeBuilderImpl recursiveOutputElementTreeBuilderImpl;

    /**
     * Hidden constructor.
     */
    protected RootOutputElementTreeBuilder(final ProfileSettings profileSettings) {
        recursiveOutputElementTreeBuilderImpl = new RecursiveOutputElementTreeBuilderImpl(profileSettings);
    }

    protected OutputElement deserializeContextsMain(Object... instances) {

        if (instances == null || instances.length == 0) {

            return new AtomicOutputElement(Void.class, null);

        }
        else if (instances.length == 1) {

            return recursiveOutputElementTreeBuilderImpl.convertInstanceRecursively(new RecursiveOutputElementTreeBuilderState(), instances[0]);

        }
        else {

            // must wrap all passed objects inside a complex element
            CollectionOutputElement complexOutputElement = new CollectionOutputElement(Object[].class, instances);

            for (Object instance : instances) {
                complexOutputElement.addElement(recursiveOutputElementTreeBuilderImpl.convertInstanceRecursively(
                        new RecursiveOutputElementTreeBuilderState(), instance));
            }

            return complexOutputElement;

        }
    }

    public static OutputElement deserializeContexts(final ProfileSettings profileSettings, final Object... objects) {
        return new RootOutputElementTreeBuilder(profileSettings).deserializeContextsMain(objects);
    }

}

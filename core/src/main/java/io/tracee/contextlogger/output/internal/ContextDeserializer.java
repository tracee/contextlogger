package io.tracee.contextlogger.output.internal;

/**
 * Converts given instances to an OutputElement instance hierarchy.
 */
public class ContextDeserializer {

    private static final ContextDeserializer instance = new ContextDeserializer();

    /**
     * Hidden constructor.
     */
    protected ContextDeserializer() {

    }

    protected OutputElement deserializeContextsMain(Object... instances) {

        if (instances == null || instances.length == 0) {

            return new AtomicOutputElement(Void.class, null);

        }
        else if (instances.length == 1) {

            return SingleInstanceContextDeserializer.convertInstance(instances[0]);

        }
        else {

            // must wrap all passed objects inside a complex element
            CollectionOutputElement complexOutputElement = new CollectionOutputElement(Object[].class);

            for (Object instance : instances) {
                complexOutputElement.addElement(SingleInstanceContextDeserializer.convertInstance(instance));
            }

            return complexOutputElement;

        }
    }

    public static OutputElement deserializeContexts(Object... objects) {
        return instance.deserializeContextsMain(objects);
    }

}

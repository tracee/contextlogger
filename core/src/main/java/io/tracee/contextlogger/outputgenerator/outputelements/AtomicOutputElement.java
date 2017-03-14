package io.tracee.contextlogger.outputgenerator.outputelements;

/**
 * Class for processing atomic element types that are defined by a single value.
 */
public class AtomicOutputElement extends AbstractOutputElement {

    private final static Class[] NON_STRING_BASED_ATOMIC_CLASSES = {long.class, Long.class, int.class, Integer.class, short.class, Short.class, byte.class, Byte.class, float.class, Float.class, double.class, Double.class, boolean.class, Boolean.class};
    private final static Class[] STRING_BASED_ATOMIC_CLASSES = {String.class, char.class, Character.class};
    private final static int MAX_NON_MULTIPLE_REFERENCE_STRING_LENGTH = 255;

    private final boolean shouldUseReferencesWhenMarkedMultipleReferenced;

    /**
     * Constructor.
     *
     * @param type                 the type
     * @param encapsulatedInstance the encapsulated instance
     */
    public AtomicOutputElement(Class type, Object encapsulatedInstance) {
        super(type, encapsulatedInstance);
        shouldUseReferencesWhenMarkedMultipleReferenced = false;
    }


    /**
     * Constructor. Used to add processed instance
     *
     * @param type                 the type
     * @param encapsulatedInstance the encapsulated processed instance
     * @param originalInstance     the unprocessed original instance
     */
    public AtomicOutputElement(Class type, Object encapsulatedInstance, Object originalInstance) {
        super(type, encapsulatedInstance, originalInstance);
        this.shouldUseReferencesWhenMarkedMultipleReferenced = true;
    }


    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public OutputElementType getOutputElementType() {
        return OutputElementType.ATOMIC;
    }


    public boolean isNonStringBasedAtomic() {

        return isOfType(this.getOutputElementsBaseType(), NON_STRING_BASED_ATOMIC_CLASSES);
    }

    public boolean isStringBasedAtomic() {

        return isOfType(this.getOutputElementsBaseType(), STRING_BASED_ATOMIC_CLASSES);
    }

    private boolean isOfType(Class type, Class[] typesToSearchIn) {

        if (type != null) {
            for (Class clazz : typesToSearchIn) {
                if (clazz.equals(type)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean useReferencesIfMarkedAsMultipleReferenced() {
        if (shouldUseReferencesWhenMarkedMultipleReferenced) {
            return true;
        } else if (isNonStringBasedAtomic()) {
            return false;
        } else if (isStringBasedAtomic() && this.getEncapsulatedInstance() != null) {
            String value = this.getEncapsulatedInstance().toString();
            return value.length() > MAX_NON_MULTIPLE_REFERENCE_STRING_LENGTH;
        } else {
            return true;
        }
    }

}

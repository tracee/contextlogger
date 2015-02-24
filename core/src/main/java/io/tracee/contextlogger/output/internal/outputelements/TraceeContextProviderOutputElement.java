package io.tracee.contextlogger.output.internal.outputelements;

import io.tracee.contextlogger.contextprovider.api.WrappedContextData;

/**
 * Output element for tracee context providers. Enhances ComplexOutputElement.
 */
public class TraceeContextProviderOutputElement extends ComplexOutputElement {

    /**
     * Constructor.
     *
     * @param type
     * @param instance
     */
    public TraceeContextProviderOutputElement(final Class type, final Object instance) {
        super(type, instance);
    }

    @Override
    public int getIdentityHashCode() {
        Object encapsulatedInstance = this.getEncapsulatedInstance();
        if (encapsulatedInstance instanceof WrappedContextData) {
            // use identity hash of encaspulated WrappedContextData instance
            WrappedContextData wrappedContextData = (WrappedContextData)encapsulatedInstance;
            return System.identityHashCode(wrappedContextData.getContextData());
        }
        else {
            return super.getIdentityHashCode();
        }
    }

}

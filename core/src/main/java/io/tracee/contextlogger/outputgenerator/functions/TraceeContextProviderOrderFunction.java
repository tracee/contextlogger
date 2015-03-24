package io.tracee.contextlogger.outputgenerator.functions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.OutputElementType;
import io.tracee.contextlogger.outputgenerator.outputelements.TraceeContextProviderOutputElement;
import io.tracee.contextlogger.utility.TraceeContextLogAnnotationUtilities;

/**
 * Enforces order order of TraceeOutput elements. Postpends non Tracee context provider output elements.
 */
public class TraceeContextProviderOrderFunction {

    private static TraceeContextProviderOrderFunction instance = new TraceeContextProviderOrderFunction();

    public class TraceeContextProviderOutputElementComparator implements Comparator<TraceeContextProviderOutputElement> {

        @Override
        public int compare(final TraceeContextProviderOutputElement o1, final TraceeContextProviderOutputElement o2) {

            if ((o1 == null) && (o2 == null)) return 0;
            if (o1 == null) return -1;
            if (o2 == null) {
                return 1;
            }

            TraceeContextProvider o1cp = o1.getEncapsulatedInstance() != null ? TraceeContextLogAnnotationUtilities.getAnnotationFromType(o1
                    .getEncapsulatedInstance()) : null;
            TraceeContextProvider o2cp = o2.getEncapsulatedInstance() != null ? TraceeContextLogAnnotationUtilities.getAnnotationFromType(o2
                    .getEncapsulatedInstance()) : null;

            if ((o1cp == null) && (o2cp == null)) return 0;
            if (o1cp == null) return -1;
            if (o2cp == null) {
                return 1;
            }

            return ((Integer)o1cp.order()).compareTo(o2cp.order());
        }

    }

    public List<OutputElement> apply(List<OutputElement> outputElements) {

        List<TraceeContextProviderOutputElement> traceeContextProviderElements = new ArrayList<TraceeContextProviderOutputElement>();
        List<OutputElement> otherOutputElements = new ArrayList<OutputElement>();
        List<OutputElement> results = new ArrayList<OutputElement>();

        for (OutputElement outputElement : outputElements) {

            if (OutputElementType.COMPLEX.equals(outputElement.getOutputElementType()) && outputElement instanceof TraceeContextProviderOutputElement) {
                traceeContextProviderElements.add((TraceeContextProviderOutputElement)outputElement);
            }
            else {
                otherOutputElements.add(outputElement);
            }

        }

        Collections.sort(traceeContextProviderElements, new TraceeContextProviderOutputElementComparator());
        results.addAll(traceeContextProviderElements);
        results.addAll(otherOutputElements);

        return results;
    }

    public static TraceeContextProviderOrderFunction getInstance() {
        return instance;
    }

}

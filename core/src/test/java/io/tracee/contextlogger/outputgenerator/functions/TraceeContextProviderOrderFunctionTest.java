package io.tracee.contextlogger.outputgenerator.functions;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.outputgenerator.outputelements.AtomicOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.TraceeContextProviderOutputElement;

/**
 * Test class for {@link TraceeContextProviderOrderFunction}.
 */

public class TraceeContextProviderOrderFunctionTest {

    @TraceeContextProvider(displayName = "FirstType", order = 10)
    public static class FirstType {

    }

    @TraceeContextProvider(displayName = "SecondType", order = 30)
    public static class SecondType {

    }

    private TraceeContextProviderOutputElement firstType = new TraceeContextProviderOutputElement(FirstType.class, new FirstType());
    private TraceeContextProviderOutputElement secondType = new TraceeContextProviderOutputElement(SecondType.class, new SecondType());
    private TraceeContextProviderOutputElement nonTCPType = new TraceeContextProviderOutputElement(String.class, "");
    private AtomicOutputElement atomicOutputElement = new AtomicOutputElement(String.class, "");

    TraceeContextProviderOrderFunction.TraceeContextProviderOutputElementComparator traceeContextProviderOutputElementComparator = new TraceeContextProviderOrderFunction.TraceeContextProviderOutputElementComparator();

    @Test
    public void should_compare_traceecontextprovideroutputelements_correctly() {

        // null | null
        MatcherAssert.assertThat(traceeContextProviderOutputElementComparator.compare(null, null), Matchers.is(0));

        // null | not null
        MatcherAssert.assertThat(traceeContextProviderOutputElementComparator.compare(firstType, null), Matchers.is(1));

        // not null | null
        MatcherAssert.assertThat(traceeContextProviderOutputElementComparator.compare(null, firstType), Matchers.is(-1));

        // not null (no TCP) | not null(no TCP)
        MatcherAssert.assertThat(traceeContextProviderOutputElementComparator.compare(nonTCPType, nonTCPType), Matchers.is(0));

        // not null (TCP) | not null(no TCP)
        MatcherAssert.assertThat(traceeContextProviderOutputElementComparator.compare(firstType, nonTCPType), Matchers.is(1));

        // not null (no TCP) | not null(TCP)
        MatcherAssert.assertThat(traceeContextProviderOutputElementComparator.compare(nonTCPType, firstType), Matchers.is(-1));

        // not null (TCP) | not null(TCP)
        MatcherAssert.assertThat(traceeContextProviderOutputElementComparator.compare(firstType, secondType), Matchers.is(-1));

        // not null (TCP) | not null(TCP)
        MatcherAssert.assertThat(traceeContextProviderOutputElementComparator.compare(secondType, firstType), Matchers.is(1));

        // not null (TCP) | not null(TCP)
        MatcherAssert.assertThat(traceeContextProviderOutputElementComparator.compare(firstType, firstType), Matchers.is(0));

    }

    @Test
    public void should_apply_order_correctly1() {

        List<OutputElement> outputElementList = new ArrayList<OutputElement>();
        outputElementList.add(atomicOutputElement);
        outputElementList.add(secondType);
        outputElementList.add(firstType);

        List<OutputElement> result = TraceeContextProviderOrderFunction.getInstance().apply(outputElementList);

        MatcherAssert.assertThat(result.size(), Matchers.is(3));
        MatcherAssert.assertThat(result.get(0), Matchers.is((OutputElement)firstType));
        MatcherAssert.assertThat(result.get(1), Matchers.is((OutputElement)secondType));
        MatcherAssert.assertThat(result.get(2), Matchers.is((OutputElement)atomicOutputElement));

    }

    @Test
    public void should_apply_order_correctly2() {

        List<OutputElement> outputElementList = new ArrayList<OutputElement>();
        outputElementList.add(secondType);
        outputElementList.add(atomicOutputElement);
        outputElementList.add(firstType);

        List<OutputElement> result = TraceeContextProviderOrderFunction.getInstance().apply(outputElementList);

        MatcherAssert.assertThat(result.size(), Matchers.is(3));
        MatcherAssert.assertThat(result.get(0), Matchers.is((OutputElement)firstType));
        MatcherAssert.assertThat(result.get(1), Matchers.is((OutputElement)secondType));
        MatcherAssert.assertThat(result.get(2), Matchers.is((OutputElement)atomicOutputElement));

    }
}

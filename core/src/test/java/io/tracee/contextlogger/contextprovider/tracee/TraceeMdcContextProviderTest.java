package io.tracee.contextlogger.contextprovider.tracee;

import io.tracee.Tracee;
import io.tracee.contextlogger.api.ImplicitContext;
import io.tracee.contextlogger.contextprovider.utility.NameStringValuePair;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

/**
 * Test class for  {@link TraceeMdcContextProvider}.
 * Created by Tobias Gindler, holisticon AG on 31.03.14.
 */
public class TraceeMdcContextProviderTest {

    @Test
    public void should_return_implicit_context() {
        final ImplicitContext implicitContext = new TraceeMdcContextProvider().getImplicitContext();

        assertThat(implicitContext, notNullValue());
        assertThat(implicitContext, equalTo(ImplicitContext.TRACEE));
    }

    @Test
    public void should_return_null_for_empty_mdc() {

        final TraceeMdcContextProvider traceeMdcContextProvider = new TraceeMdcContextProvider();

		assertThat(traceeMdcContextProvider.getNameValuePairs(), nullValue());
    }

    @Test
    public void should_return_tracee_properties() {

        Tracee.getBackend().put("ID", "ID_value");

        final TraceeMdcContextProvider traceeMdcContextProvider = new TraceeMdcContextProvider();

        final List<NameStringValuePair> result = traceeMdcContextProvider.getNameValuePairs();

        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getName(), equalTo("ID"));
        assertThat(result.get(0).getValue(), equalTo("ID_value"));
    }
}

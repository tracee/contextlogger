package io.tracee.contextlogger.impl.gson;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import io.tracee.contextlogger.contextprovider.java.JavaThrowableContextProvider;
import io.tracee.contextlogger.contextprovider.tracee.PassedDataContextProvider;
import io.tracee.contextlogger.util.test.RegexMatcher;

/**
 * Test class for {@link TraceeGsonContextStringRepresentationBuilder}.
 * Created by Tobias Gindler on 20.03.14.
 */
public class TraceeGsonContextLogBuilderTest {

    @Test
    public void should_create_context_log() {
        final TraceeGsonContextStringRepresentationBuilder logBuilder = new TraceeGsonContextStringRepresentationBuilder();
        logBuilder.setWrapperClasses(new HashSet<Class>(Arrays.<Class> asList(JavaThrowableContextProvider.class)));

        String json = logBuilder.createStringRepresentation(new JavaThrowableContextProvider(new NullPointerException()));

        assertThat(json, RegexMatcher.matches("\\[\\{\\\"stacktrace\\\":\\\"java.lang.Null.*\\}\\]"));
    }

    @Test
    public void should_create_mixed_context_log() {
        TraceeGsonContextStringRepresentationBuilder logBuilder = new TraceeGsonContextStringRepresentationBuilder();
        Set<Class> classes = new HashSet<Class>();
        classes.add(PassedDataContextProvider.class);
        classes.add(JavaThrowableContextProvider.class);

        logBuilder.setWrapperClasses(classes);

        Object[] oarray = new Object[2];
        oarray[0] = new JavaThrowableContextProvider(new NullPointerException());
        oarray[1] = "TATA";
        PassedDataContextProvider cp = new PassedDataContextProvider(oarray);

        String json = logBuilder.createStringRepresentation(cp);

        assertThat(
                json,
                RegexMatcher
                        .matches("\\[\\{\\\"throwable\\\":\\{\\\"stacktrace\\\":\\\"java.lang.NullPointerException.*java.lang.String\\\":\\\"TATA\\\"\\}\\]"));
    }
}

package io.tracee.contextlogger.contextprovider;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import io.tracee.contextlogger.contextprovider.core.tracee.TraceeMessage;

/**
 * Test class for {@link TypeToWrapper}.
 * Created by Tobias Gindler, holisticon AG on 20.03.14.
 */
public class TypeToWrapperTest {

    @Test
    public void getAvailableWrappers_should_load_all_available_wrappers() {

        List<TypeToWrapper> result = TypeToWrapper.getAvailableWrappers();

        assertThat(result, notNullValue());
        assertThat(result.size(), greaterThan(0));
    }

    @Test
    public void getAllWrappedClasses_should_get_all_wrapped_classes() {

        Set<Class> wrappedClassesSet = TypeToWrapper.getAllWrappedClasses();

        assertThat(wrappedClassesSet, notNullValue());
        assertThat(wrappedClassesSet.size(), greaterThan(0));
        assertThat(wrappedClassesSet, containsInAnyOrder((Class)TraceeMessage.class, (Class)Throwable.class));
    }

}

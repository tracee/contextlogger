package io.tracee.contextlogger.output.internal.utility;

import java.lang.reflect.Method;
import java.util.Set;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.output.internal.testclasses.BeanTestClass;

/**
 * Test class for {@link io.tracee.contextlogger.output.internal.utility.BeanUtility}.
 */
public class BeanUtilityTest {

    @Test
    public void getGetterMethodsRecursively_should_get_all_valid_getter_methods() {

        Set<Method> validGetterMethods = BeanUtility.getGetterMethodsRecursively(BeanTestClass.class);

        MatcherAssert.assertThat(validGetterMethods.size(), Matchers.is(1));
        MatcherAssert.assertThat(validGetterMethods.iterator().next().getName(), Matchers.is("getValidGetterField"));
        MatcherAssert.assertThat(validGetterMethods.size(), Matchers.is(1));

    }
}

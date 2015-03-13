package io.tracee.contextlogger.utility.reflection;

import java.lang.reflect.Method;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit test for {@link io.tracee.contextlogger.utility.reflection.IsGetterMethodPredicate}.
 */
public class IsGetterMethodPredicateTest {

    class TestClass {

        private String validGetterField;
        private String getterFieldWithParameter;
        private String getterFieldWithReturnTypeVoid;
        private Integer getterFieldWithMismatchingTypes;

        public String getValidGetterField() {
            return validGetterField;
        }

        public String getGetterFieldWithParameter(String arg) {
            return getterFieldWithParameter;
        }

        public void getGetterFieldWithReturnTypeVoid() {

        }

        public String getGetterFieldWithMismatchingTypes() {
            return "";
        }

        public String getGetterWithoutCorrespondingField() {
            return "";
        }

        public String wtfValidGetterField() {
            return validGetterField;
        }

    }

    @Test
    public void should_check_valid_getter_method_correctly() throws NoSuchMethodException {

        Method method = TestClass.class.getMethod("getValidGetterField");

        boolean result = IsGetterMethodPredicate.check(TestClass.class, method);

        MatcherAssert.assertThat(result, Matchers.is(true));

    }

    @Test
    public void should_return_false_for_getter_method_with_parameter() throws NoSuchMethodException {

        Method method = TestClass.class.getMethod("getGetterFieldWithParameter", String.class);

        boolean result = IsGetterMethodPredicate.check(TestClass.class, method);

        MatcherAssert.assertThat(result, Matchers.is(false));

    }

    @Test
    public void should_return_false_for_getter_method_with_return_type_void() throws NoSuchMethodException {

        Method method = TestClass.class.getMethod("getGetterFieldWithReturnTypeVoid");

        boolean result = IsGetterMethodPredicate.check(TestClass.class, method);

        MatcherAssert.assertThat(result, Matchers.is(false));

    }

    @Test
    public void should_return_false_for_getter_method_with_mismatching_return_type() throws NoSuchMethodException {

        Method method = TestClass.class.getMethod("getGetterFieldWithMismatchingTypes");

        boolean result = IsGetterMethodPredicate.check(TestClass.class, method);

        MatcherAssert.assertThat(result, Matchers.is(false));

    }

    @Test
    public void should_return_false_for_getter_method_without_corresponding_field() throws NoSuchMethodException {

        Method method = TestClass.class.getMethod("getGetterWithoutCorrespondingField");

        boolean result = IsGetterMethodPredicate.check(TestClass.class, method);

        MatcherAssert.assertThat(result, Matchers.is(false));

    }

    @Test
    public void should_return_false_for_getter_method_with_irregular_getter_prefix() throws NoSuchMethodException {

        Method method = TestClass.class.getMethod("wtfValidGetterField");

        boolean result = IsGetterMethodPredicate.check(TestClass.class, method);

        MatcherAssert.assertThat(result, Matchers.is(false));

    }

}

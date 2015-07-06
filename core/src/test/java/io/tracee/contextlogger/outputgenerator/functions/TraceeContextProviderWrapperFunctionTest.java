package io.tracee.contextlogger.outputgenerator.functions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.tracee.contextlogger.contextprovider.api.ImplicitContext;
import io.tracee.contextlogger.contextprovider.TypeToWrapper;
import io.tracee.contextlogger.contextprovider.api.WrappedContextData;
import io.tracee.contextlogger.impl.ContextLoggerConfiguration;

/**
 * Unit test for {@link TraceeContextProviderWrapperFunction}.
 */
public class TraceeContextProviderWrapperFunctionTest {

    private ContextLoggerConfiguration contextLoggerConfiguration = Mockito.mock(ContextLoggerConfiguration.class);

    private TraceeContextProviderWrapperFunction unit = TraceeContextProviderWrapperFunction.getInstance();

    public static class ClassWithNoNoargConstructor {

        public ClassWithNoNoargConstructor(String xyz) {

        }

    }

    public static class UnmappedClass {

    }

    public static class MappedClass {

    }

    public static class MappedClassWrapper implements WrappedContextData<MappedClass> {

        private MappedClass instance;

        @Override
        public void setContextData(final Object instance) throws ClassCastException {
            this.instance = (MappedClass)instance;
        }

        @Override
        public MappedClass getContextData() {
            return this.instance;
        }

        @Override
        public Class getWrappedType() {
            return MappedClass.class;
        }
    }

    public static class IncorrectMappedClass {

    }

    public static class IncorrectMappedClassWrapper {

    }

    public static class WrappedTypeListType {

    }

    public static class WrappedTypeListTypeWrapper implements WrappedContextData<WrappedTypeListType> {

        private WrappedTypeListType instance;

        @Override
        public void setContextData(final Object instance) throws ClassCastException {
            this.instance = (WrappedTypeListType)instance;
        }

        @Override
        public WrappedTypeListType getContextData() {
            return this.instance;
        }

        @Override
        public Class<WrappedTypeListType> getWrappedType() {
            return WrappedTypeListType.class;
        }
    }

    @Before
    public void init() {

        Map<ImplicitContext, Class> implicitContextClassMap = new HashMap<ImplicitContext, Class>();
        implicitContextClassMap.put(ImplicitContext.COMMON, TraceeContextProviderWrapperFunctionTest.class);

        when(contextLoggerConfiguration.getImplicitContextClassMap()).thenReturn(implicitContextClassMap);

        Map<Class, Class> classToWrapperMap = new HashMap<Class, Class>();
        classToWrapperMap.put(MappedClass.class, MappedClassWrapper.class);
        classToWrapperMap.put(IncorrectMappedClass.class, IncorrectMappedClassWrapper.class);

        when(contextLoggerConfiguration.getClassToWrapperMap()).thenReturn(classToWrapperMap);

        List<TypeToWrapper> typeToWrapperList = new ArrayList<TypeToWrapper>();
        typeToWrapperList.add(new TypeToWrapper(WrappedTypeListType.class, WrappedTypeListTypeWrapper.class));

        when(contextLoggerConfiguration.getWrapperList()).thenReturn(typeToWrapperList);

    }

    @Test
    public void apply_should_handle_implicit_context_correctly() {

        Object result = unit.apply(contextLoggerConfiguration, ImplicitContext.COMMON);
        assertThat(result, Matchers.notNullValue());
        assertThat(result.getClass(), Matchers.typeCompatibleWith(this.getClass()));

    }

    @Test
    public void apply_should_handle_known_mapped_context_class_correctly() {

        Object instance = new MappedClass();

        Object result = unit.apply(contextLoggerConfiguration, instance);

        assertThat(result, Matchers.notNullValue());
        assertThat(result.getClass(), Matchers.typeCompatibleWith(MappedClassWrapper.class));

    }

    @Test
    public void apply_should_handle_type_to_wrapper_type_correctly() {

        Object instance = new WrappedTypeListType();

        Object result = unit.apply(contextLoggerConfiguration, instance);

        assertThat(result, Matchers.notNullValue());
        assertThat(result.getClass(), Matchers.typeCompatibleWith(WrappedTypeListTypeWrapper.class));

    }

    @Test
    public void apply_should_handle_broken_known_mapped_context_class_correctly() {

        Object instance = new IncorrectMappedClass();

        Object result = unit.apply(contextLoggerConfiguration, instance);

        // should return passed instance if mapping is broken
        assertThat(result, Matchers.notNullValue());
        assertThat(result, Matchers.is(instance));

    }

    @Test
    public void apply_should_return_passed_instance_if_no_wrapper_can_be_found() {

        Object instance = new UnmappedClass();

        Object result = unit.apply(contextLoggerConfiguration, instance);
        assertThat(result, Matchers.notNullValue());
        assertThat(result, Matchers.is(instance));

    }

    @Test
    public void createInstance_should_create_instance_correctly() {
        Object result = unit.createInstance(this.getClass());

        assertThat(result, Matchers.notNullValue());
        assertThat(result.getClass(), Matchers.typeCompatibleWith(this.getClass()));
    }

    @Test
    public void createInstance_should_create_null_for_type_with_no_noarg_constructor() {

        Object result = unit.createInstance(ClassWithNoNoargConstructor.class);

        assertThat(result, Matchers.nullValue());

    }
}

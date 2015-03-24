package io.tracee.contextlogger.impl;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.tracee.contextlogger.api.ConfigBuilder;
import io.tracee.contextlogger.api.ContextLogger;
import io.tracee.contextlogger.api.internal.Configuration;
import io.tracee.contextlogger.api.internal.ContextLoggerBuilderAccessable;
import io.tracee.contextlogger.profile.Profile;

/**
 * Test class for {@link ConfigBuilderImpl}.
 * Created by Tobias Gindler on 19.06.14.
 */
public class ConfigBuilderImplTest {

    ContextLoggerBuilderAccessable contextLogger;
    ConfigBuilder<ContextLogger> configBuilder;

    @Before
    public void init() {

        contextLogger = Mockito.mock(ContextLoggerBuilderAccessable.class);
        Mockito.when(contextLogger.getContextLoggerConfiguration()).thenReturn(ContextLoggerConfiguration.getOrCreateContextLoggerConfiguration());

        configBuilder = new ConfigBuilderImpl(contextLogger);

    }

    @Test
    public void should_set_enable_and_disable_correctly() {

        final String DISABLED_1 = "D1";
        final String DISABLED_2 = "D2";

        final String ENABLED_1 = "E1";
        final String ENABLED_2 = "E2";

        Configuration configuration = (Configuration)configBuilder.disable(DISABLED_1, DISABLED_2).enable(ENABLED_1, ENABLED_2);
        assertThat(configuration, Matchers.notNullValue());

        Map<String, Boolean> manualContextOverrides = configuration.getManualContextOverrides();

        assertThat(manualContextOverrides.get(DISABLED_1), Matchers.is(false));
        assertThat(manualContextOverrides.get(DISABLED_2), Matchers.is(false));
        assertThat(manualContextOverrides.get(ENABLED_1), Matchers.is(true));
        assertThat(manualContextOverrides.get(ENABLED_2), Matchers.is(true));

    }

    @Test
    public void should_have_default_empty_manual_context_override_map() {
        Configuration configuration = (Configuration)configBuilder;
        assertThat(configuration, Matchers.notNullValue());

        Map<String, Boolean> manualContextOverrides = configuration.getManualContextOverrides();
        assertThat(manualContextOverrides, Matchers.notNullValue());
        assertThat(manualContextOverrides.size(), Matchers.is(0));
    }

    @Test
    public void should_set_keepOrder_correctly() {
        Configuration configuration = (Configuration)configBuilder.enforceOrder();
        assertThat(configuration, Matchers.notNullValue());
        assertThat(configuration.getEnforceOrder(), Matchers.is(true));
    }

    @Test
    public void should_have_correct_default_keep_order_value() {
        Configuration configuration = (Configuration)configBuilder;
        assertThat(configuration, Matchers.notNullValue());
        assertThat(configuration.getEnforceOrder(), Matchers.is(false));
    }

    @Test
    public void should_return_config_logger_builder_on_apply() {

        ContextLogger result = configBuilder.apply();
        assertThat(result, Matchers.is((ContextLogger)contextLogger));

    }

    @Test
    public void should_return_null_valued_default_profile() {

        Configuration configuration = (Configuration)configBuilder;
        assertThat(configuration, Matchers.notNullValue());
        assertThat(configuration.getProfile(), Matchers.nullValue());

    }

    @Test
    public void should_set_profile_correctly() {

        Configuration configuration = (Configuration)configBuilder.enforceProfile(Profile.FULL);
        assertThat(configuration, Matchers.notNullValue());
        assertThat(configuration.getProfile(), Matchers.is(Profile.FULL));

    }

}

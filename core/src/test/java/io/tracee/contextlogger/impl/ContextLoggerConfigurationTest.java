package io.tracee.contextlogger.impl;

import io.tracee.contextlogger.contextprovider.api.Profile;
import io.tracee.contextlogger.contextprovider.core.CoreImplicitContextProviders;
import io.tracee.contextlogger.contextprovider.core.java.JavaThrowableContextProvider;
import io.tracee.contextlogger.profile.ProfilePropertyNames;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link ContextLoggerConfiguration}.
 * Created by Tobias Gindler, holisticon AG on 01.04.14.
 */
public class ContextLoggerConfigurationTest {

	@Before
	public void init() {
		System.setProperty(ProfilePropertyNames.PROFILE_SET_GLOBALLY_VIA_SYSTEM_PROPERTIES, Profile.BASIC.name());
	}

	@Test
	public void should_get_context_logger_configuration() {

		ContextLoggerConfiguration contextLoggerConfiguration = ContextLoggerConfiguration.getOrCreateContextLoggerConfiguration();

		MatcherAssert.assertThat(contextLoggerConfiguration, Matchers.notNullValue());

	}

	@Test
	public void should_get_implicit_wrapper_list_of_context_logger_configuration() {

		ContextLoggerConfiguration contextLoggerConfiguration = ContextLoggerConfiguration.getOrCreateContextLoggerConfiguration();

		MatcherAssert.assertThat(contextLoggerConfiguration, Matchers.notNullValue());
		MatcherAssert.assertThat(contextLoggerConfiguration.getImplicitContextProviderClass(CoreImplicitContextProviders.COMMON), Matchers.notNullValue());
		MatcherAssert.assertThat(contextLoggerConfiguration.getImplicitContextProviderClass(CoreImplicitContextProviders.TRACEE), Matchers.notNullValue());

	}

	@Test
	public void should_get_wrapper_class_mapping_of_context_logger_configuration() {

		ContextLoggerConfiguration contextLoggerConfiguration = ContextLoggerConfiguration.getOrCreateContextLoggerConfiguration();

		MatcherAssert.assertThat(contextLoggerConfiguration, Matchers.notNullValue());
		MatcherAssert.assertThat(contextLoggerConfiguration.getContextProviderClass(Throwable.class), Matchers.notNullValue());
		MatcherAssert.assertThat(contextLoggerConfiguration.getContextProviderClass(Throwable.class).equals(JavaThrowableContextProvider.class),
				Matchers.equalTo(true));

	}


	@Test
	public void should_get_wrappers_of_context_logger_configuration() {

		ContextLoggerConfiguration contextLoggerConfiguration = ContextLoggerConfiguration.getOrCreateContextLoggerConfiguration();

		MatcherAssert.assertThat(contextLoggerConfiguration, Matchers.notNullValue());

		MatcherAssert.assertThat(contextLoggerConfiguration.getWrapperList(), Matchers.notNullValue());
		MatcherAssert.assertThat(contextLoggerConfiguration.getWrapperList().size(), Matchers.greaterThan(0));
	}

}

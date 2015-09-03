package io.tracee.contextlogger.integrationtest;

import io.tracee.contextlogger.contextprovider.ContextProviderServiceLoader;
import io.tracee.contextlogger.contextprovider.api.Profile;
import io.tracee.contextlogger.profile.ProfileLookup;
import io.tracee.contextlogger.profile.ProfilePropertyNames;
import io.tracee.contextlogger.profile.ProfileSettings;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * Integration test to check if loading mechanism for profiles works correctly and without classpath issues.
 */
public class ProfileIntegrationTest {

	public static Logger logger = LoggerFactory.getLogger(ProfileIntegrationTest.class);

	@Before
	public void init() {
		System.getProperties().remove(ProfilePropertyNames.CUSTOM_PROFILE_FILENAME_SET_GLOBALLY_VIA_SYSTEM_PROPERTIES);
	}

	@Test
	public void shouldLoadCorrectDefaultProfile() {

		Profile profile = ProfileLookup.getCurrentProfile();
		MatcherAssert.assertThat(profile, Matchers.is(Profile.ENHANCED));

	}

	@Test
	public void shouldLoadAllPropertiesCorrectly() {

		ProfileSettings profileSettings = new ProfileSettings(Profile.BASIC);

		// MatcherAssert.assertThat(profileSettings.getPropertyValue(), Matchers.is());

		// CORE
		MatcherAssert
				.assertThat(
						profileSettings
								.getPropertyValue("io.tracee.contextlogger.contextprovider.core.java.JavaThrowableContextProvider.message"),
						Matchers.is(true));


		// ASPECTJ
		MatcherAssert
				.assertThat(
						profileSettings
								.getPropertyValue("io.tracee.contextlogger.contextprovider.aspectj.contextprovider.AspectjProceedingJoinPointContextProvider.serializedTargetInstance"),
						Matchers.is(false));
		MatcherAssert.assertThat(profileSettings
				.getPropertyValue("io.tracee.contextlogger.contextprovider.aspectj.contextprovider.WatchdogContextProvider.proceedingJoinPoint"), Matchers
				.is(true));

		// JAVAEE
		MatcherAssert
				.assertThat(
						profileSettings
								.getPropertyValue("io.tracee.contextlogger.contextprovider.javaee.contextprovider.InvocationContextContextProvider.methodName"),
						Matchers.is(true));

		// JAXWS
		MatcherAssert
				.assertThat(
						profileSettings
								.getPropertyValue("io.tracee.contextlogger.contextprovider.jaxws.contextprovider.JaxWsContextProvider.soapRequest"),
						Matchers.is(true));

		// SERVLET
		MatcherAssert
				.assertThat(
						profileSettings
								.getPropertyValue("io.tracee.contextlogger.contextprovider.servlet.contextprovider.ServletRequestContextProvider.httpParameters"),
						Matchers.is(true));


		// SPRING-MVC
		MatcherAssert
				.assertThat(
						profileSettings
								.getPropertyValue("io.tracee.contextlogger.contextprovider.springmvc.contextprovider.HandlerMethodContextProvider.method"),
						Matchers.is(true));
	}


	@Test
	public void shouldLoadCustomPropertiesViaSelectorPropertyFile() {

		ProfileSettings profileSettings = new ProfileSettings(Profile.BASIC);

		MatcherAssert
				.assertThat(
						profileSettings
								.getPropertyValue("abc.def"),
						Matchers.is(true));
		MatcherAssert
				.assertThat(
						profileSettings
								.getPropertyValue("abc.def2"),
						Matchers.is(false));

	}


	@Test
	public void shouldLoadCustomPropertiesViaSystemProperty() {

		Enumeration<URL> enumeration = null;
		try {
			enumeration = this.getClass().getClassLoader().getResources("META-INF/services/io.tracee.contextlogger.contextprovider.api.TraceeContextProviderServiceProvider");

			while (enumeration.hasMoreElements()) {
				logger.error(enumeration.nextElement().toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}


		System.getProperties().setProperty(ProfilePropertyNames.CUSTOM_PROFILE_FILENAME_SET_GLOBALLY_VIA_SYSTEM_PROPERTIES, "test2.properties");

		((ContextProviderServiceLoader) ContextProviderServiceLoader.getServiceLocator()).clear();

		ProfileSettings profileSettings = new ProfileSettings(Profile.BASIC);

		MatcherAssert
				.assertThat(
						profileSettings
								.getPropertyValue("hij.klm"),
						Matchers.is(true));
		MatcherAssert
				.assertThat(
						profileSettings
								.getPropertyValue("hij.klm2"),
						Matchers.is(false));

	}

}

package io.tracee.contextlogger.integrationtest;

import io.tracee.contextlogger.contextprovider.ContextProviderServiceLoader;
import io.tracee.contextlogger.contextprovider.api.Profile;
import io.tracee.contextlogger.profile.ProfileLookup;
import io.tracee.contextlogger.profile.ProfilePropertyNames;
import io.tracee.contextlogger.profile.ProfileSettings;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Integration test to check if loading mechanism for profiles works correctly and without classpath issues.
 */
public class ProfileIntegrationTest {

	@Test
	public void shouldLoadCorrectDefaultProfile() {

		Profile profile = ProfileLookup.getCurrentProfile();
		MatcherAssert.assertThat(profile, Matchers.is(Profile.ENHANCED));

	}

	@Test
	public void shouldLoadAllPropertiesCorrectly() {

		ProfileSettings profileSettings = new ProfileSettings(Profile.BASIC);

		// MatcherAssert.assertThat(profileSettings.getPropertyValue(), Matchers.is());

		// ASPECTJ
		MatcherAssert
				.assertThat(
						profileSettings
								.getPropertyValue("io.tracee.contextlogger.contextprovider.aspectj.contextprovider.AspectjProceedingJoinPointContextProvider.serializedTargetInstance"),
						Matchers.is(false));
		MatcherAssert.assertThat(profileSettings
				.getPropertyValue("io.tracee.contextlogger.contextprovider.aspectj.contextprovider.WatchdogContextProvider.proceedingJoinPoint"), Matchers
				.is(true));

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

package io.tracee.contextlogger.integrationtest;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.contextprovider.api.Profile;
import io.tracee.contextlogger.profile.ProfileLookup;
import io.tracee.contextlogger.profile.ProfileSettings;

/**
 * Integration test to check if loading mechanism for profiles works correctly and without classpath issues.
 */
public class ProfileIntegrationTest {

    @Test
    public void shouldLoadCorrectDefaultProfile() {

        Profile profile = ProfileLookup.getCurrentProfile();
        MatcherAssert.assertThat(profile, Matchers.is(Profile.BASIC));

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

}

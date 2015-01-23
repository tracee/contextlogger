package io.tracee.contextlogger.profile;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.contextprovider.java.JavaThrowableContextProvider;

/**
 * Test class for {@link io.tracee.contextlogger.profile.ProfileSettings} Created by Tobias Gindler, holisticon AG on 25.03.14.
 */
public class ProfileSettingsTest {

    @Test
    public void should_return_true_for_throwable_message_in_basic_profile() {

        ProfileSettings profileSettings = new ProfileSettings(Profile.BASIC, null);
        boolean shouldBeFalse = profileSettings.getPropertyValue(JavaThrowableContextProvider.class.getCanonicalName() + ".message");

        MatcherAssert.assertThat(shouldBeFalse, Matchers.equalTo(true));

    }

    @Test
    public void should_return_false_for_unknown_property_key_in_basic_profile() {

        ProfileSettings profileSettings = new ProfileSettings(Profile.BASIC, null);
        Boolean shouldBeFalse = profileSettings.getPropertyValue("UNKNOWN");

        MatcherAssert.assertThat(shouldBeFalse, Matchers.nullValue());

    }

    @Test
    public void should_return_null_for_null_valued_property_key_in_basic_profile() {

        ProfileSettings profileSettings = new ProfileSettings(Profile.BASIC, null);
        Boolean shouldBeNull = profileSettings.getPropertyValue(null);

        MatcherAssert.assertThat(shouldBeNull, Matchers.nullValue());

    }

    public void should_return_true_for_manual_override() {
        final String KEY = "KEY_UNKNOWN_IN_PROFILE";
        final boolean VALUE = true;
        Map<String, Boolean> overrideMap = new HashMap<String, Boolean>();
        overrideMap.put(KEY, VALUE);

        ProfileSettings profileSettings = new ProfileSettings(Profile.NONE, overrideMap);

        boolean result = profileSettings.getPropertyValue(KEY);

        MatcherAssert.assertThat(result, Matchers.equalTo(true));

    }

    public void should_return_false_for_manual_override() {
        final String KEY = "KEY_UNKNOWN_IN_PROFILE";
        final boolean VALUE = false;
        Map<String, Boolean> overrideMap = new HashMap<String, Boolean>();
        overrideMap.put(KEY, VALUE);

        ProfileSettings profileSettings = new ProfileSettings(Profile.NONE, overrideMap);

        boolean result = profileSettings.getPropertyValue(KEY);

        MatcherAssert.assertThat(result, Matchers.equalTo(false));

    }

}

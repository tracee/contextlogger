package io.tracee.contextlogger.output.internal;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import io.tracee.contextlogger.output.internal.outputelements.AtomicOutputElement;
import io.tracee.contextlogger.output.internal.outputelements.OutputElement;
import io.tracee.contextlogger.profile.Profile;
import io.tracee.contextlogger.profile.ProfileSettings;

/**
 * Unit test for {@link io.tracee.contextlogger.output.internal.RecursiveOutputElementTreeBuilderImplTest}.
 */
public class RecursiveOutputElementTreeBuilderImplTest {

    private static final ProfileSettings PROFILE_SETTINGS = new ProfileSettings(Profile.BASIC);
    private static final OutputElement ATOMIC_OUTPUT_ELEMENT = new AtomicOutputElement(String.class, "ABC");

    private RecursiveOutputElementTreeBuilderImpl unit;

    @Before
    public void init() {
        unit = new RecursiveOutputElementTreeBuilderImpl(PROFILE_SETTINGS);
    }

    @Test
    public void getProfileSettings_should_return_profile_settings() {
        ProfileSettings profileSettings = unit.getProfileSettings();
        MatcherAssert.assertThat(profileSettings, Matchers.is(PROFILE_SETTINGS));
    }

    @Test
    public void should_register_output_elements_correctly() {

        // initial checks
        boolean resultShouldInitiallyNotBeRegistered = unit.checkIfInstanceIsAlreadyRegistered(ATOMIC_OUTPUT_ELEMENT);
        MatcherAssert.assertThat(resultShouldInitiallyNotBeRegistered, Matchers.is(false));

        OutputElement resultShouldInitiallyNotBeRegisteredOutputElement = unit.getRegisteredOutputElement(ATOMIC_OUTPUT_ELEMENT);
        MatcherAssert.assertThat(resultShouldInitiallyNotBeRegisteredOutputElement, Matchers.nullValue());

        // register
        unit.registerOutputElement(ATOMIC_OUTPUT_ELEMENT);

        // final checks
        boolean resultShouldBeRegistered = unit.checkIfInstanceIsAlreadyRegistered(ATOMIC_OUTPUT_ELEMENT);
        MatcherAssert.assertThat(resultShouldBeRegistered, Matchers.is(true));

        OutputElement resultShouldBeRegisteredOutputElement = unit.getRegisteredOutputElement(ATOMIC_OUTPUT_ELEMENT);
        MatcherAssert.assertThat(resultShouldBeRegisteredOutputElement, Matchers.notNullValue());
        MatcherAssert.assertThat(resultShouldBeRegisteredOutputElement, Matchers.is(ATOMIC_OUTPUT_ELEMENT));

    }

}

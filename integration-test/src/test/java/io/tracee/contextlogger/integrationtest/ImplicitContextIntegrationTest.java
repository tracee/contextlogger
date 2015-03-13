package io.tracee.contextlogger.integrationtest;

import io.tracee.contextlogger.TraceeContextLogger;
import io.tracee.contextlogger.api.ImplicitContext;
import io.tracee.contextlogger.profile.Profile;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Created by TGI on 13.03.2015.
 */
public class ImplicitContextIntegrationTest {

	@Test
	public void shouldOutputImplicitContextCorrectly() {
		String result = TraceeContextLogger.create().config().enforceProfile(Profile.BASIC).apply().build()
				.createJson(ImplicitContext.COMMON, ImplicitContext.TRACEE);

		MatcherAssert.assertThat(result, Matchers.is("{\"testImplicitContextData\":{}}"));
	}

}

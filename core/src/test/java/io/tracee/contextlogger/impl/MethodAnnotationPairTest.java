package io.tracee.contextlogger.impl;

import io.tracee.contextlogger.contextprovider.api.Profile;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderMethod;
import io.tracee.contextlogger.profile.ProfileSettings;
import io.tracee.contextlogger.testdata.ProfileSettingsBuilder;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Test class for {@link io.tracee.contextlogger.impl.MethodAnnotationPair}.
 * Created by Tobias Gindler, holisticon AG on 01.04.14.
 */
@TraceeContextProvider(displayName = "MethodAnnotationPairTest")
public class MethodAnnotationPairTest {

	private final static String PROPERTY_NAME = MethodAnnotationPairTest.class.getCanonicalName() + ".testWithPropertyName";
	private static Method METHOD;

	@TraceeContextProviderMethod(displayName = "")
	public int testWithEmptyPropertyName() {
		return 0;
	}

	@TraceeContextProviderMethod(displayName = "")
	public int testWithPropertyName() {
		return 0;
	}

	{
		try {
			METHOD = MethodAnnotationPairTest.class.getMethod("testWithPropertyName", new Class[0]);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void should_return_true_for_non_annotated_method() {

		MethodAnnotationPair methodAnnotationPair = new MethodAnnotationPair(MethodAnnotationPairTest.class, METHOD, null);
		ProfileSettings profileSettings = new ProfileSettings(Profile.NONE);

		Boolean result = methodAnnotationPair.shouldBeProcessed(profileSettings);

		assertThat(result, Matchers.equalTo(true));

	}

	@Test
	public void should_return_true_for_annotated_method_with_property_name_and_set_profiler_settings() {

		final Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put(PROPERTY_NAME, true);

		MethodAnnotationPair methodAnnotationPair = new MethodAnnotationPair(MethodAnnotationPairTest.class, METHOD, getAnnotation(METHOD));
		ProfileSettings profileSettings = ProfileSettingsBuilder.create(Profile.NONE, map);

		boolean result = methodAnnotationPair.shouldBeProcessed(profileSettings);

		assertThat(result, Matchers.equalTo(true));

	}

	@Test
	public void should_return_false_for_annotated_method_with_property_name_and_not_set_profiler_settings() {

		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put(PROPERTY_NAME, false);

		MethodAnnotationPair methodAnnotationPair = new MethodAnnotationPair(MethodAnnotationPairTest.class, METHOD, getAnnotation(METHOD));
		ProfileSettings profileSettings = ProfileSettingsBuilder.create(Profile.NONE, map);

		boolean result = methodAnnotationPair.shouldBeProcessed(profileSettings);

		assertThat(result, Matchers.equalTo(false));

	}

	@Test
	public void should_return_false_for_annotated_method_with_property_name_and_null_valued_profiler_settings() {

		MethodAnnotationPair methodAnnotationPair = new MethodAnnotationPair(MethodAnnotationPairTest.class, METHOD, getAnnotation(METHOD));

		boolean result = methodAnnotationPair.shouldBeProcessed(null);

		assertThat(result, Matchers.equalTo(true));

	}

	@Test
	public void getPropertyName_should_return_property_name_for_passed_method() {
		MethodAnnotationPair methodAnnotationPair = new MethodAnnotationPair(MethodAnnotationPairTest.class, METHOD, getAnnotation(METHOD));
		String propertyName = methodAnnotationPair.getPropertyName();
		assertThat(propertyName, Matchers.equalTo("io.tracee.contextlogger.impl.MethodAnnotationPairTest.testWithPropertyName"));
	}

	private TraceeContextProviderMethod getAnnotation(final Method method) {
		return method.getAnnotation(TraceeContextProviderMethod.class);
	}

}

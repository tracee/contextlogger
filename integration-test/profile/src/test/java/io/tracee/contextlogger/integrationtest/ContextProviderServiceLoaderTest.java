package io.tracee.contextlogger.integrationtest;

import io.tracee.contextlogger.contextprovider.ContextProviderServiceLoader;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderServiceProvider;
import io.tracee.contextlogger.contextprovider.aspectj.contextprovider.AspectjProceedingJoinPointContextProvider;
import io.tracee.contextlogger.contextprovider.core.java.JavaThrowableContextProvider;
import io.tracee.contextlogger.contextprovider.javaee.contextprovider.InvocationContextContextProvider;
import io.tracee.contextlogger.contextprovider.jaxws.contextprovider.JaxWsContextProvider;
import io.tracee.contextlogger.contextprovider.servlet.contextprovider.ServletRequestContextProvider;
import io.tracee.contextlogger.contextprovider.springmvc.contextprovider.HandlerMethodContextProvider;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Arrays;

/**
 * Integration test to check if loading mechanism for context providers works correctly and without classpath issues.
 */
public class ContextProviderServiceLoaderTest {

	@Test
	public void shouldLoadCoreContextProviders() {
		TraceeContextProviderServiceProvider contextProviderServiceLoader = ContextProviderServiceLoader.getServiceLocator();
		Class[] contextProviders = contextProviderServiceLoader.getContextProvider();

		MatcherAssert.assertThat(Arrays.asList(contextProviders), Matchers.hasItem((Class) JavaThrowableContextProvider.class));

	}

	@Test
	public void shouldLoadServletContextProviders() {
		TraceeContextProviderServiceProvider contextProviderServiceLoader = ContextProviderServiceLoader.getServiceLocator();
		Class[] contextProviders = contextProviderServiceLoader.getContextProvider();

		MatcherAssert.assertThat(Arrays.asList(contextProviders), Matchers.hasItem((Class) ServletRequestContextProvider.class));

	}

	@Test
	public void shouldLoadJaxWsContextProviders() {
		TraceeContextProviderServiceProvider contextProviderServiceLoader = ContextProviderServiceLoader.getServiceLocator();
		Class[] contextProviders = contextProviderServiceLoader.getContextProvider();

		MatcherAssert.assertThat(Arrays.asList(contextProviders), Matchers.hasItem((Class) JaxWsContextProvider.class));

	}

	@Test
	public void shouldLoadJavaEEContextProviders() {
		TraceeContextProviderServiceProvider contextProviderServiceLoader = ContextProviderServiceLoader.getServiceLocator();
		Class[] contextProviders = contextProviderServiceLoader.getContextProvider();

		MatcherAssert.assertThat(Arrays.asList(contextProviders), Matchers.hasItem((Class) InvocationContextContextProvider.class));

	}

	@Test
	public void shouldLoadAspectjContextProviders() {
		TraceeContextProviderServiceProvider contextProviderServiceLoader = ContextProviderServiceLoader.getServiceLocator();
		Class[] contextProviders = contextProviderServiceLoader.getContextProvider();

		MatcherAssert.assertThat(Arrays.asList(contextProviders), Matchers.hasItem((Class) AspectjProceedingJoinPointContextProvider.class));

	}

	@Test
	public void shouldLoadSpringMvcContextProviders() {
		TraceeContextProviderServiceProvider contextProviderServiceLoader = ContextProviderServiceLoader.getServiceLocator();
		Class[] contextProviders = contextProviderServiceLoader.getContextProvider();

		MatcherAssert.assertThat(Arrays.asList(contextProviders), Matchers.hasItem((Class) HandlerMethodContextProvider.class));

	}

}

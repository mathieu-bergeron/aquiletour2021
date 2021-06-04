package ca.ntro.jdk.services;

import ca.ntro.core.NtroPromiseProvider;
import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.regex.RegEx;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.system.trace.__T;
import ca.ntro.jdk.NtroPromiseJdk;
import ca.ntro.jdk.regex.RegExJdk;
import ca.ntro.jdk.web.interactivity.runtime.InteractivityRuntimeJdk;
import ca.ntro.services.AppCloser;
import ca.ntro.services.CollectionsService;
import ca.ntro.services.EarlyInitialization;
import ca.ntro.services.Logger;
import ca.ntro.web.interactivity.DomProcessor;
import ca.ntro.jdk.web.interactivity.builders.FormBuilderJdk;
import ca.ntro.jdk.web.interactivity.builders.LinkBuilderJdk;
import ca.ntro.web.interactivity.runtime.InteractivityRuntime;

public class EarlyInitializationJdk extends EarlyInitialization {

	@Override
	protected AppCloser provideAppCloser() {
		__T.call(this, "provideAppCloser");

		return new AppCloserJdk();
	}

	@Override
	protected RegEx provideRegEx() {
		__T.call(this, "provideRegEx");

		return new RegExJdk();
	}

	@Override
	protected Introspector provideIntrospector() {
		T.call(this);

		return new IntrospectorJdk();
	}

	@Override
	protected DomProcessor provideDomProcessor() {
		return new DomProcessor(new LinkBuilderJdk(), new FormBuilderJdk());
	}

	@Override
	protected InteractivityRuntime provideInteractivityRuntime() {
		return new InteractivityRuntimeJdk();
	}

	@Override
	protected NtroPromiseProvider provideNtroPromiseProvider() {
		return new NtroPromiseJdk.Provider();
	}

	@Override
	protected Logger provideLogger() {
		__T.call(InitializationTaskJdk.class, "provideLogger");

		return new LoggerJdk();
	}

	@Override
	protected CollectionsService provideCollectionsService() {
		return new CollectionsServiceJdk();
	}

}

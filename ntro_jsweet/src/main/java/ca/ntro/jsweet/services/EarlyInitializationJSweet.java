package ca.ntro.jsweet.services;

import ca.ntro.core.NtroPromiseProvider;
import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.regex.RegEx;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.system.trace.__T;
import ca.ntro.jsweet.NtroPromiseJSweet;
import ca.ntro.jsweet.introspection.IntrospectorJSweet;
import ca.ntro.jsweet.regex.RegExJSweet;
import ca.ntro.services.AppCloser;
import ca.ntro.services.CollectionsService;
import ca.ntro.services.EarlyInitialization;
import ca.ntro.services.Logger;
import ca.ntro.web.interactivity.DomProcessor;
import ca.ntro.jsweet.web.interactivity.builders.FormBuilderJSweet;
import ca.ntro.jsweet.web.interactivity.builders.LinkBuilderJSweet;
import ca.ntro.web.interactivity.runtime.InteractivityRuntime;
import ca.ntro.web.interactivity.runtime.InteractivityRuntimeJSweet;

public class EarlyInitializationJSweet extends EarlyInitialization {

	@Override
	protected Introspector provideIntrospector() {
		T.call(this);

		return new IntrospectorJSweet();
	}

	@Override
	protected DomProcessor provideDomProcessor() {
		T.call(this);

		return new DomProcessor(new LinkBuilderJSweet(), new FormBuilderJSweet());
	}

	@Override
	protected InteractivityRuntime provideInteractivityRuntime() {
		return new InteractivityRuntimeJSweet();
	}

	@Override
	protected NtroPromiseProvider provideNtroPromiseProvider() {
		return new NtroPromiseJSweet.Provider();
	}

	@Override
	protected Logger provideLogger() {
		__T.call(this, "provideLogger");

		return new LoggerJSweet();
	}


	@Override
	protected AppCloser provideAppCloser() {
		__T.call(this, "provideAppCloser");

		return new AppCloserJSweet();
	}

	@Override
	protected CollectionsService provideCollectionsService() {
		return new CollectionsServiceJSweet();
	}


	@Override
	protected RegEx provideRegEx() {
		__T.call(this, "provideRegEx");

		return new RegExJSweet();
	}
}

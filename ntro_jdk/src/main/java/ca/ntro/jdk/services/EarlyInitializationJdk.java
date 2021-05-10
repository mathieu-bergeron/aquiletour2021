package ca.ntro.jdk.services;

import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.regex.RegEx;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.system.trace.__T;
import ca.ntro.jdk.regex.RegExJdk;
import ca.ntro.services.AppCloser;
import ca.ntro.services.CollectionsService;
import ca.ntro.services.EarlyInitialization;
import ca.ntro.services.Logger;

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
	protected Logger provideLogger() {
		__T.call(InitializationTaskJdk.class, "provideLogger");

		return new LoggerJdk();
	}

	@Override
	protected CollectionsService provideCollectionsService() {
		return new CollectionsServiceJdk();
	}
}

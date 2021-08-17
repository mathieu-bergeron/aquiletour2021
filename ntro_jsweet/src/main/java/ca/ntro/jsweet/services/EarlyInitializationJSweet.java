package ca.ntro.jsweet.services;

import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.regex.RegEx;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.system.trace.__T;
import ca.ntro.jsweet.introspection.IntrospectorJSweet;
import ca.ntro.jsweet.regex.RegExJSweet;
import ca.ntro.services.AppCloser;
import ca.ntro.services.CollectionsService;
import ca.ntro.services.ConfigService;
import ca.ntro.services.EarlyInitialization;
import ca.ntro.services.Logger;

public class EarlyInitializationJSweet extends EarlyInitialization {

	@Override
	protected Introspector provideIntrospector() {
		T.call(this);

		return new IntrospectorJSweet();
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


	@Override
	protected ConfigService provideConfigService() {
		return new ConfigService();
	}
}

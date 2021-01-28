// Copyright (C) (2020) (Mathieu Bergeron) (mathieu.bergeron@cmontmorency.qc.ca)
//
// This file is part of tutoriels4f5
//
// tutoriels4f5 is free software: you can redistribute it and/or modify
// it under the terms of the GNU Affero General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// tutoriels4f5 is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Affero General Public License for more details.
//
// You should have received a copy of the GNU Affero General Public License
// along with aquiletour.  If not, see <https://www.gnu.org/licenses/>

package ca.ntro.jdk.services;

import ca.ntro.core.initialization.InitializationTask;
import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.regex.RegEx;
import ca.ntro.core.services.AppCloser;
import ca.ntro.core.services.Logger;
import ca.ntro.core.services.NtroCollections;
import ca.ntro.core.services.ResourceLoader;
import ca.ntro.core.services.ValueFormatter;
import ca.ntro.core.system.stack.StackAnalyzer;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.system.trace.__T;
import ca.ntro.jdk.regex.RegExJdk;
import ca.ntro.jdk.web.ViewLoaderWebJdk;
import ca.ntro.web.mvc.ViewLoaderWeb;

public class InitializationTaskJdk extends InitializationTask {

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
	protected StackAnalyzer provideStackAnalyzer() {
		__T.call(this, "provideStackAnalyzer");

		return new StackAnalyzerJdk();
	}

	@Override
	protected Introspector provideIntrospector() {
		T.call(this);
		
		return new IntrospectorJdk();
	}

	@Override
	protected ValueFormatter provideValueFormatter() {
		T.call(this);
		
		return new ValueFormatterJdk();
	}

	@Override
	protected NtroCollections provideNtroCollections() {
		T.call(this);

		return new CollectionProviderJdk();
	}

	@Override
	protected Logger provideLogger() {
		__T.call(InitializationTaskJdk.class, "provideLogger");

		return new LoggerJdk();
	}

	@Override
	protected ResourceLoader provideResourceLoader() {
		__T.call(InitializationTaskJdk.class, "provideResourceLoader");

		return new ResourceLoaderJdk();
	}

	@Override
	protected Class<? extends ViewLoaderWeb> provideViewLoaderWebClass() {
		__T.call(InitializationTaskJdk.class, "provideViewLoaderWeb");

		return ViewLoaderWebJdk.class;
	}

}

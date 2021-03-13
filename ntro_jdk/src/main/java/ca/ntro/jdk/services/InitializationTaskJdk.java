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

import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.models.ModelStore;
import ca.ntro.core.regex.RegEx;
import ca.ntro.core.system.stack.StackAnalyzer;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.system.trace.__T;
import ca.ntro.jdk.regex.RegExJdk;
import ca.ntro.jdk.web.ViewLoaderWebJdk;
import ca.ntro.services.AppCloser;
import ca.ntro.services.AssertService;
import ca.ntro.services.BackendService;
import ca.ntro.services.InitializationTask;
import ca.ntro.services.JsonService;
import ca.ntro.services.Logger;
import ca.ntro.services.MessageService;
import ca.ntro.services.NtroCollections;
import ca.ntro.services.ResourceLoader;
import ca.ntro.services.ThreadService;
import ca.ntro.services.UserService;
import ca.ntro.services.ValueFormatter;
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

		return new NtroCollectionsJdk();
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

	@Override
	protected JsonParser provideJsonParser() {
		__T.call(InitializationTaskJdk.class, "provideJsonParser");

		return new JsonParserJdk();
	}

	@Override
	protected ThreadService provideThreadService() {
		__T.call(InitializationTaskJdk.class, "provideThreadService");
		
		return new ThreadServiceJdk();
	}

	@Override
	protected Class<? extends MessageService> provideMessageServiceClass() {
		__T.call(InitializationTaskJdk.class, "provideMessageServiceClass");
		
		return MessageServiceJdk.class;
	}

	@Override
	protected BackendService provideBackendService() {
		__T.call(InitializationTaskJdk.class, "provideBackendService");
		
		return new BackendServiceJdk();
	}

	@Override
	protected AssertService provideAssertService() {
		return new AssertServiceJdkDev();
	}

	@Override
	protected JsonService provideJsonService() {
		return new JsonServiceJdk();
	}

	@Override
	protected UserService provideUserService() {
		return new UserServiceJdk();
	}

	@Override
	protected Class<? extends ModelStore> provideModelStoreClass() {
		return LocalStoreFiles.class;
	}

}

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

package ca.ntro.jsweet.services;

import ca.ntro.core.system.stack.StackAnalyzer;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.system.trace.__T;
import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.models.ModelStore;
import ca.ntro.core.regex.RegEx;
import ca.ntro.jsweet.debug.StackAnalyzerJSweet;
import ca.ntro.jsweet.introspection.IntrospectorJSweet;
import ca.ntro.jsweet.regex.RegExJSweet;
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

public class InitializationTaskJSweet extends InitializationTask {
	
	private final BackendServiceJSweet backendServiceJSweet;
	
	public InitializationTaskJSweet(BackendServiceJSweet backendServiceJSweet) {
		super();
		T.call(this);

		this.backendServiceJSweet = backendServiceJSweet;
	}
	
	
	@Override
	protected StackAnalyzer provideStackAnalyzer() {
		__T.call(this, "provideStackAnalyzer");

		return new StackAnalyzerJSweet();
	}


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
	protected RegEx provideRegEx() {
		__T.call(this, "provideRegEx");
		
		return new RegExJSweet();
	}


	@Override
	protected ValueFormatter provideValueFormatter() {
		__T.call(this, "provideValueFormatter");
		
		return new ValueFormatterJSweet();
	}


	@Override
	protected NtroCollections provideNtroCollections() {
		__T.call(this, "provideNtroCollections");

		return new NtroCollectionsJSweet();
	}


	@Override
	protected ResourceLoader provideResourceLoader() {
		__T.call(this, "provideResourceLoader");

		return new ResourceLoaderJSweet();
	}


	@Override
	protected Class<? extends ViewLoaderWeb> provideViewLoaderWebClass() {
		__T.call(this, "provideViewLoaderWeb");

		return ViewLoaderWebJSweet.class;
	}


	@Override
	protected JsonParser provideJsonParser() {
		__T.call(this, "provideJsonParser");
		
		return new JsonParserJSweet();
	}


	@Override
	protected ModelStore provideLocalStore() {
		__T.call(this, "provideLocalStore");

		return new LocalStoreJSweet();
	}


	@Override
	protected ModelStore provideNetworkStore() {
		__T.call(this, "provideNetworkStore");

		return new NetworkStoreJSweet();
	}


	@Override
	protected ThreadService provideThreadService() {
		__T.call(this, "provideThreadService");
		
		return new ThreadServiceJSweet();
	}


	@Override
	protected Class<? extends MessageService> provideMessageServiceClass() {
		__T.call(this, "provideMessageServiceClass");

		return MessageServiceJSweet.class;
	}


	@Override
	protected BackendService provideBackendService() {
		__T.call(this, "provideBackendService");

		return backendServiceJSweet;
	}

	@Override
	protected AssertService provideAssertService() {
		return new AssertServiceJSweet();
	}


	@Override
	protected JsonService provideJsonService() {
		return new JsonServiceJSweet();
	}


	@Override
	protected UserService provideUserService() {
		return new UserServiceJSweet();
	}
}
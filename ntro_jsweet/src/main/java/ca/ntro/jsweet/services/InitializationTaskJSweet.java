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
import ca.ntro.jsweet.debug.StackAnalyzerJSweet;
import ca.ntro.services.AssertService;
import ca.ntro.services.BackendService;
import ca.ntro.services.CalendarService;
import ca.ntro.services.ConfigService;
import ca.ntro.services.InitializationTask;
import ca.ntro.services.JsonService;
import ca.ntro.services.MessageService;
import ca.ntro.services.ModelStore;
import ca.ntro.services.ResourceLoader;
import ca.ntro.services.RouterService;
import ca.ntro.services.SessionService;
import ca.ntro.services.SystemService;
import ca.ntro.services.ThreadService;
import ca.ntro.services.ValueFormatter;
import ca.ntro.web.mvc.ViewLoaderWeb;

public class InitializationTaskJSweet extends InitializationTask {
	
	private final RouterService routerService;
	
	public InitializationTaskJSweet(RouterService routerService) {
		super();
		T.call(this);

		this.routerService = routerService;
	}
	
	@Override
	protected StackAnalyzer provideStackAnalyzer() {
		__T.call(this, "provideStackAnalyzer");

		return new StackAnalyzerJSweet();
	}

	@Override
	protected ValueFormatter provideValueFormatter() {
		__T.call(this, "provideValueFormatter");
		
		return new ValueFormatterJSweet();
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
	protected Class<? extends BackendService> provideBackendServiceClass() {
		__T.call(this, "provideBackendService");

		return BackendServiceJSweetSockJS.class;
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
	protected Class<? extends SessionService> provideSessionServiceClass() {
		return SessionServiceJSweet.class;
	}

	@Override
	protected ModelStore provideModelStore() {
		return new NetworkStoreJSweet();
	}

	@Override
	protected CalendarService provideCalendarService() {
		return new CalendarServiceJSweet();
	}

	@Override
	protected RouterService provideRouterService() {
		return routerService;
	}

	@Override
	protected SystemService provideSystemService() {
		return new SystemServiceJSweet();
	}
}

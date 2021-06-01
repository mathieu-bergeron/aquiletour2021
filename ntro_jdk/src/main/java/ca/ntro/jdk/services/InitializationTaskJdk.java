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

import java.util.Map;

import ca.ntro.core.Path;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.stack.StackAnalyzer;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.system.trace.__T;
import ca.ntro.jdk.web.ViewLoaderWebJdk;
import ca.ntro.services.AssertService;
import ca.ntro.services.BackendService;
import ca.ntro.services.CalendarService;
import ca.ntro.services.InitializationTask;
import ca.ntro.services.JsonService;
import ca.ntro.services.MessageService;
import ca.ntro.services.ModelStore;
import ca.ntro.services.Ntro;
import ca.ntro.services.ConfigService;
import ca.ntro.services.ResourceLoader;
import ca.ntro.services.RouterService;
import ca.ntro.services.SessionService;
import ca.ntro.services.ThreadService;
import ca.ntro.services.ValueFormatter;
import ca.ntro.web.mvc.ViewLoaderWeb;

public class InitializationTaskJdk extends InitializationTask { 


	@Override
	protected StackAnalyzer provideStackAnalyzer() {
		__T.call(this, "provideStackAnalyzer");

		return new StackAnalyzerJdk();
	}


	@Override
	protected ValueFormatter provideValueFormatter() {
		T.call(this);
		
		return new ValueFormatterJdk();
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
	protected Class<? extends BackendService> provideBackendServiceClass() {
		__T.call(InitializationTaskJdk.class, "provideBackendService");
		
		return BackendServiceJdk.class;
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
	protected Class<? extends SessionService> provideSessionServiceClass() {
		return SessionServiceJdk.class;
	}

	@Override
	protected ModelStore provideModelStore() {
		return Ntro.factory().newInstance(LocalStoreFiles.class);
	}


	@Override
	protected ConfigService provideConfigService() {
		return new ConfigService();
	}


	@Override
	protected CalendarService provideCalendarService() {
		return new CalendarServiceJdk();
	}


	@Override
	protected RouterService provideRouterService() {
		return new RouterService() {

			@Override
			public void sendFrontendMessagesFor(NtroContext<?, ?> context, Path path,
					Map<String, String[]> parameters) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void sendBackendMessagesFor(NtroContext<?, ?> context, Path path, Map<String, String[]> parameters) {
				// TODO Auto-generated method stub
				
			}
			
		};
	}



}

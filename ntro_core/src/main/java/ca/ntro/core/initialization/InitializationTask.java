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

package ca.ntro.core.initialization;

import ca.ntro.core.Ntro;
import ca.ntro.core.__Ntro;
import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.models.ModelStore;
import ca.ntro.core.regex.RegEx;
import ca.ntro.core.services.AppCloser;
import ca.ntro.core.services.AssertService;
import ca.ntro.core.services.BackendService;
import ca.ntro.core.services.JsonService;
import ca.ntro.core.services.Logger;
import ca.ntro.core.services.MessageService;
import ca.ntro.core.services.NtroCollections;
import ca.ntro.core.services.ResourceLoader;
import ca.ntro.core.services.ThreadService;
import ca.ntro.core.services.ValueFormatter;
import ca.ntro.core.services.stores.LocalStore;
import ca.ntro.core.services.stores.NetworkStore;
import ca.ntro.core.system.stack.StackAnalyzer;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.system.trace.__T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.web.mvc.ViewLoaderWeb;

public abstract class InitializationTask extends NtroTaskSync {

	@Override
	protected void runTask() {
		__T.call(InitializationTask.class, "runSyncTask");
		performInitialization();
	}

	@Override
	public void onFailure(Exception e) {
	}

	private void performInitialization() {
		Introspector introspector = provideIntrospector();
		T.__registerIntrospector(introspector);
		Ntro.__registerIntrospector(introspector);

		Logger logger = provideLogger();
		T.__registerLogger(logger);
		Ntro.__registerLogger(logger);
		
		Ntro.__registerAppCloser(provideAppCloser());
		Ntro.__registerRegEx(provideRegEx());

		StackAnalyzer stackAnalyzer = provideStackAnalyzer();
		T.__registerStackAnalyzer(stackAnalyzer);
		__Ntro.registerStackAnalyzer(stackAnalyzer);

		Ntro.zzz_registerResourceLoader(provideResourceLoader());
		
		Ntro.__registerViewLoaderWeb(provideViewLoaderWebClass());
		
		ValueFormatter.initialize(provideValueFormatter());

		NtroCollections.initialize(provideNtroCollections());
		
		JsonParser.initialize(provideJsonParser());
		
		LocalStore.initialize(provideLocalStore());
		NetworkStore.initialize(provideNetworkStore());
		
		Ntro.zzz_registerThreadService(provideThreadService());
		Ntro.zzz_registerMessageServiceClass(provideMessageServiceClass());
		Ntro.zzz_registerBackendService(provideBackendService());

		Ntro.zzz_registerAssertService(provideAssertService());
		Ntro.zzz_registerJsonService(provideJsonService());
	}




	protected abstract Logger provideLogger();
	protected abstract AppCloser provideAppCloser();
	protected abstract RegEx provideRegEx();
	protected abstract StackAnalyzer provideStackAnalyzer();
	protected abstract Introspector provideIntrospector();
	protected abstract ValueFormatter provideValueFormatter();
	protected abstract NtroCollections provideNtroCollections();
	protected abstract ResourceLoader provideResourceLoader();
	protected abstract Class<? extends ViewLoaderWeb> provideViewLoaderWebClass();
	protected abstract JsonParser provideJsonParser();
	protected abstract ModelStore provideLocalStore();
	protected abstract ModelStore provideNetworkStore();
	protected abstract ThreadService provideThreadService();
	protected abstract Class<? extends MessageService> provideMessageServiceClass();
	protected abstract BackendService provideBackendService();
	protected abstract AssertService provideAssertService();
	protected abstract JsonService provideJsonService();
}

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

import ca.ntro.core.Constants;
import ca.ntro.core.system.trace.__T;
import ca.ntro.jsweet.JSweetGlobals;
import ca.ntro.services.NtroInitializationTask;
import ca.ntro.services.RouterService;

public class NtroJSweet {
	
	public static NtroInitializationTask defaultInitializationTask(RouterService routerService) {
		__T.call(NtroJSweet.class, "defaultInitializationTask");

		EarlyInitializationJSweet earlyInitialization = new EarlyInitializationJSweet();
		earlyInitialization.performInitialization();
		
		NtroInitializationTask initializationTask = new NtroInitializationTask();
		initializationTask.setTaskId(Constants.INITIALIZATION_TASK_ID);
		
		InitializationTaskJSweet initJSweet = new InitializationTaskJSweet(routerService);

		JSweetGlobals.initialize();

		initializationTask.addSubTask(initJSweet);
		initializationTask.addSubTask(JSweetGlobals.onLoadTask);
		
		// TODO: add the WebSocket initialization as a SubTask

		return initializationTask;
	}
}

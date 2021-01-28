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

package ca.ntro.jsweet;

import ca.ntro.core.initialization.NtroInitializationTask;
import ca.ntro.core.system.trace.__T;
import ca.ntro.jsweet.initialization.InitializationTaskJSweet;
import ca.ntro.jsweet.initialization.LoadSourceMapTask;
import ca.ntro.web.mvc.ViewLoaderWeb;

public class NtroJSweet {

	public static NtroInitializationTask defaultInitializationTask() {
		__T.call(NtroJSweet.class, "defaultInitializationTask");
		
		NtroInitializationTask initializationTask = new NtroInitializationTask();
		
		InitializationTaskJSweet initJSweet = new InitializationTaskJSweet();

		// FIXME/TODO
		//initJSweet.addNextTask(new LoadSourceMapTask("/js/tutoriel02/bundle.js.map"));

		initializationTask.addSubTask(initJSweet);
		initializationTask.addSubTask(Globals.onLoadTask);

		return initializationTask;
	}

	public static ViewLoaderWeb viewLoader() {
		return new ViewLoaderWeb();
	}


}

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

package ca.ntro.jdk;

import ca.ntro.core.Constants;
import ca.ntro.core.initialization.NtroInitializationTask;
import ca.ntro.core.services.NtroCollections;
import ca.ntro.core.system.trace.__T;
import ca.ntro.jdk.services.NtroCollectionsJdk;
import ca.ntro.jdk.services.InitializationTaskJdk;

public class NtroJdk {

	public static NtroInitializationTask defaultInitializationTask() {
		__T.call(NtroJdk.class, "defaultInitializationTask");
		
		NtroCollections.initialize(new NtroCollectionsJdk());
		
		NtroInitializationTask initializationTask = new NtroInitializationTask();
		initializationTask.setTaskId(Constants.INITIALIZATION_TASK_ID);

		initializationTask.addSubTask(new InitializationTaskJdk());

		return initializationTask;
	}

}

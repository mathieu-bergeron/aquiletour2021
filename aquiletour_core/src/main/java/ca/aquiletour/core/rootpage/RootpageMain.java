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

package ca.aquiletour.core.rootpage;

import ca.ntro.core.NtroView;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTask;

public class RootpageMain extends NtroTask {

	@Override
	protected void runTask() {
		T.call(this);
		
		NtroView rootpageView = getPreviousTask(ViewLoader.class,"View").getView();
		
		// install view on the main window (e.g. Document or MainStage)

	}

	@Override
	protected void onFailure(Exception e) {
		
	}

}

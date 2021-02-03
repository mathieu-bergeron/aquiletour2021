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

package ca.aquiletour.web.pages.root;

import java.util.Map;

import ca.aquiletour.core.pages.root.RootController;
import ca.aquiletour.web.pages.dashboard.DashboardControllerWeb;
import ca.aquiletour.web.pages.settings.SettingsControllerWeb;
import ca.ntro.core.Ntro;
import ca.ntro.core.Path;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.State;
import ca.ntro.web.HtmlWriter;
import ca.ntro.web.NtroWindowWeb;
import ca.ntro.web.RequestHandler;

public class      RootControllerWeb 
       extends    RootController 
       implements RequestHandler,
                  HtmlWriter {
	
	@Override
	public void writeHtml(StringBuilder out) {
		T.call(this);

		((NtroWindowWeb) Ntro.window()).writeHtml(out);
	}

	@Override
	public void initialRequest(Path path, Map<String, String[]> parameters, String authToken) {
		T.call(this);
		
	}

	@Override
	public void newRequest(Path oldPath, Path path, Map<String, String[]> oldParameters,
			Map<String, String[]> parameters, String authToken) {
		T.call(this);

	}
}

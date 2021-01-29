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
import ca.ntro.core.mvc.view.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.HtmlWriterTask;
import ca.ntro.web.NtroWindowWeb;
import ca.ntro.web.Path;
import ca.ntro.web.RequestHandlerTask;

public abstract class   RootControllerWeb 
                extends RootController 
                implements RequestHandlerTask,
                           HtmlWriterTask {

	protected ViewLoader createViewLoader(String lang) {
		return Ntro.viewLoaderWeb()
		           .setHtmlUrl("/views/rootpage/structure.html")
		           .setCssUrl("/views/rootpage/style.css")
		           .setTranslationsUrl("/i18/"+lang+"/strings.json")
		           .setTargetClass(RootViewWeb.class);
	}

	// FIXME: we specialize return type 
	//        is this supported in JSweet???
	@Override
	protected abstract NtroWindowWeb getWindow();


	public void initialRequest(Path path, 
		                       Map<String, String[]> parameters, 
		                       String authToken) {
		T.call(this);
		
		getWindow().setCurrentPath(path);
		
		if(path.startsWith("settings")) {

			SettingsControllerWeb settingsController = (SettingsControllerWeb) getSettingsController();
			
			settingsController.initialRequest(path.subPath(1), parameters, authToken);
			
			addNextTask(settingsController.createShowSettingsTask());

		}else if(path.startsWith("dashboard")){
			
			DashboardControllerWeb dashboardController = (DashboardControllerWeb) getDashboardController();
			
			dashboardController.initialRequest(path.subPath(1), parameters, authToken);
			
			addNextTask(dashboardController.createShowDashboardTask());
		}
		
		// TODO: comment envoyer un message? P.ex. formulaire via POST?
	}

	@Override
	public abstract SettingsControllerWeb createSettingsController();

	@Override
	public abstract DashboardControllerWeb createDashboardController();

	@Override
	public void newRequest(Path oldPath, 
			               Path path, 
			               Map<String, String[]> oldParameters, 
			               Map<String, String[]> parameters, 
			               String authToken) {
		T.call(this);
		
	}

	@Override
	public void writeHtml(StringBuilder out) {
		T.call(this);
		
		getWindow().writeHtml(out);
	}
}
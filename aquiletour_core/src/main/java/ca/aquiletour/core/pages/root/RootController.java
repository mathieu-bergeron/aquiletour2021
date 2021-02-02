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

package ca.aquiletour.core.pages.root;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.dashboard.DashboardController;
import ca.aquiletour.core.pages.settings.SettingsController;
import ca.aquiletour.core.pages.settings.SettingsView;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.mvc.view.ViewLoaders;
import ca.ntro.core.system.trace.T;

public class RootController extends NtroController {
	
	private RootViewReceptor rootViewReceptor = new RootViewReceptor();

	@Override
	protected void initialize() {
		T.call(this);
		
		setViewLoader(ViewLoaders.getViewLoader(RootView.class, Constants.LANG));

		setViewReceptor(rootViewReceptor);
		
		addSubController(SettingsController.class, "settings");
		addSubController(DashboardController.class, "dashboard");
		
		addMessageReceptor(QuitMessage.class, new QuitReceptor());
	}

	public void displaySettings(SettingsView settingsView) {
		T.call(this);
		
		rootViewReceptor.displaySettings(settingsView);
	}
}

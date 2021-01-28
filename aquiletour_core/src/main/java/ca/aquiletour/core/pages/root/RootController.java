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
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.mvc.NtroWindow;
import ca.ntro.core.mvc.view.NtroView;
import ca.ntro.core.mvc.view.ViewLoader;
import ca.ntro.core.system.trace.T;

public abstract class RootController extends NtroController {
	
	private ViewLoader viewLoader;
	private DashboardController dashboardController;
	private SettingsController settingsController;
	
	private RootView rootView;

	@Override
	protected void initializeTask() {
		T.call(this);

		viewLoader = createViewLoader(Constants.LANG);
		dashboardController = createDashboardController();
		settingsController = createSettingsController();

		addSubTask(viewLoader);
		addSubTask(dashboardController);
		addSubTask(settingsController);
	}

	protected abstract ViewLoader createViewLoader(String lang);
	protected abstract NtroWindow getWindow();

	@Override
	protected void runTaskAsync() {
		T.call(this);
		
		getWindow().installRootView(viewLoader);

		rootView = (RootView) viewLoader.getView();
		
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		
	}

	public abstract SettingsController createSettingsController();
	public abstract DashboardController createDashboardController();
	
	public void installSubView(NtroView view) {
		T.call(this);
		
		rootView.installSubView(view);
	}
	
	protected SettingsController getSettingsController() {
		T.call(this);

		return settingsController;
	}

	protected DashboardController getDashboardController() {
		T.call(this);

		return dashboardController;
	}

}

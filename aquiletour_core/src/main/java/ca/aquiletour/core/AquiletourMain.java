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

package ca.aquiletour.core;

import ca.aquiletour.core.pages.activity.ActivityController;
import ca.aquiletour.core.pages.course.CourseController;
import ca.aquiletour.core.pages.dashboard.DashboardController;
import ca.aquiletour.core.pages.root.RootController;
import ca.aquiletour.core.pages.settings.SettingsController;
import ca.ntro.core.initialization.NtroInitializationTask;
import ca.ntro.core.mvc.ControllerFactory;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskImpl;

public abstract class AquiletourMain extends NtroTaskImpl {

	@Override
	protected void initializeTask() {

	}

	@Override
	protected void runTaskAsync() {
		T.call(this);
		
		Constants.LANG = getPreviousTask(NtroInitializationTask.class).getOption("lang");

		// FIXME
		Constants.LANG = "fr";
		
		ControllerFactory.registerController("/", RootController.class);
		ControllerFactory.registerController("/settings", SettingsController.class);
		ControllerFactory.registerController("/dashboard", DashboardController.class);
		ControllerFactory.registerController("/dashboard/*", CourseController.class);
		ControllerFactory.registerController("/dashboard/*/*", ActivityController.class);

		RootController rootController = ControllerFactory.createController(RootController.class, "/**");  // create all controllers

		rootController.execute();

		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		System.err.println("[FATAL] Initialization error");
		e.printStackTrace(System.err);
	}
}

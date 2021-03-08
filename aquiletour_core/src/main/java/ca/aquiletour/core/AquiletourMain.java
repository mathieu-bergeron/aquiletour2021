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

import ca.aquiletour.core.models.users.AnonUser;
import ca.aquiletour.core.models.users.Student;
import ca.aquiletour.core.models.users.Teacher;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.aquiletour.core.pages.dashboards.values.ObservableCourseList;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.aquiletour.core.pages.queue.values.ObservableAppointmentMap;
import ca.aquiletour.core.pages.queues.QueuesModel;
import ca.aquiletour.core.pages.queues.values.ObservableQueueList;
import ca.aquiletour.core.pages.root.RootController;
import ca.aquiletour.core.pages.users.UsersModel;
import ca.aquiletour.core.pages.users.values.ObservableUserMap;
import ca.ntro.core.Ntro;
import ca.ntro.core.initialization.NtroInitializationTask;
import ca.ntro.core.mvc.ControllerFactory;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroWindow;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;

public abstract class AquiletourMain extends NtroTaskSync {

	protected abstract void registerViewLoaders();

	@Override
	protected void runTask() {
		T.call(this);

		Constants.LANG = getPreviousTask(NtroInitializationTask.class, "initializationTask").getOption("lang");

		// FIXME
		Constants.LANG = "fr";
		
		NtroContext<User> context = new NtroContext<>();
		
		// TODO: in JSweet, get user from Cookies
		User devUser = new Teacher();
		devUser.setId("alice");
		devUser.setAuthToken("aliceToken");
		context.setUser(devUser);
		context.setLang(Constants.LANG);
		
		registerViewLoaders();
		registerSerializableClasses();
		
		// XXX: "/**" means: execute every subController
		// XXX: "/*/*/*" means: execute every subController down 3 levels
		// XXX: "/settings/*" means: execute the settings controller, then subController of settings
		RootController rootController = ControllerFactory.createRootController(RootController.class, "*", getWindow(), context);  

		rootController.execute();

	}
	
	public static void registerSerializableClasses() {
		T.call(AquiletourMain.class);

		Ntro.jsonService().registerSerializableClass(DashboardModel.class);
		Ntro.jsonService().registerSerializableClass(ObservableCourseList.class);
		Ntro.jsonService().registerSerializableClass(CourseSummary.class);

		Ntro.jsonService().registerSerializableClass(QueueModel.class);
		Ntro.jsonService().registerSerializableClass(ObservableAppointmentMap.class);
		Ntro.jsonService().registerSerializableClass(Appointment.class);

		Ntro.jsonService().registerSerializableClass(QueuesModel.class);
		Ntro.jsonService().registerSerializableClass(ObservableQueueList.class);

		Ntro.jsonService().registerSerializableClass(UsersModel.class);
		Ntro.jsonService().registerSerializableClass(ObservableUserMap.class);
		Ntro.jsonService().registerSerializableClass(Teacher.class);
		Ntro.jsonService().registerSerializableClass(Student.class);
	}
	
	protected abstract NtroWindow getWindow();
	

	@Override
	protected void onFailure(Exception e) {
		System.err.println("[FATAL] Initialization error");
		e.printStackTrace(System.err);
	}
}

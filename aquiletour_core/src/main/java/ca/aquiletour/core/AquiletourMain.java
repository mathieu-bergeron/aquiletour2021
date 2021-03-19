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

import ca.aquiletour.core.models.users.Student;
import ca.aquiletour.core.models.users.Teacher;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.aquiletour.core.pages.dashboards.values.ObservableCourseList;
import ca.aquiletour.core.pages.git.commit_list.CommitListModel;
import ca.aquiletour.core.pages.git.commit_list.CommitView;
import ca.aquiletour.core.pages.git.values.Commit;
import ca.aquiletour.core.pages.git.values.ObservableCommitList;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queue.student.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.DeleteAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherClosesQueueMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherUsesQueueMessage;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.aquiletour.core.pages.queue.values.ObservableAppointmentList;
import ca.aquiletour.core.pages.queues.QueuesModel;
import ca.aquiletour.core.pages.queues.values.ObservableQueueList;
import ca.aquiletour.core.pages.queues.values.QueueSummary;
import ca.aquiletour.core.pages.root.RootController;
import ca.aquiletour.core.pages.users.UsersModel;
import ca.aquiletour.core.pages.users.values.ObservableUserMap;
import ca.ntro.core.mvc.ControllerFactory;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroWindow;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.messages.NtroMessage;
import ca.ntro.services.Ntro;
import ca.ntro.services.NtroInitializationTask;

public abstract class AquiletourMain extends NtroTaskSync {

	protected abstract void registerViewLoaders();

	@Override
	protected void runTask() {
		T.call(this);
		
		Constants.LANG = getPreviousTask(NtroInitializationTask.class, "initializationTask").getOption("lang");

		// FIXME
		Constants.LANG = "fr";

		User currentUser = (User) Ntro.userService().currentUser();
		
		NtroContext<User> context = new NtroContext<>();
		context.registerUser(currentUser);
		context.registerLang(Constants.LANG);

		registerViewLoaders();
		

		// XXX: "/**" means: execute every subController
		// XXX: "/*/*/*" means: execute every subController down 3 levels
		// XXX: "/settings/*" means: execute the settings controller, then subController of settings
		RootController rootController = ControllerFactory.createRootController(RootController.class, "*", getWindow(), context);  

		rootController.execute();

	}
	
	public static void registerSerializableClasses() {
		T.call(AquiletourMain.class);

		Ntro.registerSerializableClass(DashboardModel.class);
		Ntro.registerSerializableClass(ObservableCourseList.class);
		Ntro.registerSerializableClass(CourseSummary.class);

		Ntro.registerSerializableClass(QueueModel.class);
		Ntro.registerSerializableClass(ObservableAppointmentList.class);
		Ntro.registerSerializableClass(Appointment.class);

		Ntro.registerSerializableClass(QueuesModel.class);
		Ntro.registerSerializableClass(ObservableQueueList.class);
		Ntro.registerSerializableClass(QueueSummary.class);

		Ntro.registerSerializableClass(UsersModel.class);
		Ntro.registerSerializableClass(ObservableUserMap.class);
		Ntro.registerSerializableClass(User.class);
		Ntro.registerSerializableClass(Teacher.class);
		Ntro.registerSerializableClass(Student.class);

		Ntro.registerSerializableClass(NtroMessage.class);
		Ntro.registerSerializableClass(AddCourseMessage.class);
		Ntro.registerSerializableClass(AddAppointmentMessage.class);
		Ntro.registerSerializableClass(DeleteAppointmentMessage.class);
		Ntro.registerSerializableClass(TeacherClosesQueueMessage.class);
		Ntro.registerSerializableClass(TeacherUsesQueueMessage.class);
		
		Ntro.registerSerializableClass(CommitListModel.class);
		Ntro.registerSerializableClass(ObservableCommitList.class);
		Ntro.registerSerializableClass(Commit.class);
		
		
		
	}
	
	protected abstract NtroWindow getWindow();
	

	@Override
	protected void onFailure(Exception e) {
		System.err.println("[FATAL] Initialization error");
		e.printStackTrace(System.err);
	}
}

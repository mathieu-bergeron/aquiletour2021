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
import ca.aquiletour.core.messages.ShowDashboardMessage;
import ca.aquiletour.core.models.users.Student;
import ca.aquiletour.core.models.users.StudentGuest;
import ca.aquiletour.core.models.users.Teacher;
import ca.aquiletour.core.models.users.TeacherGuest;
import ca.aquiletour.core.pages.course.student.CourseControllerStudent;
import ca.aquiletour.core.pages.course.teacher.CourseControllerTeacher;
import ca.aquiletour.core.pages.course_list.CourseListController;
import ca.aquiletour.core.pages.dashboards.student.DashboardControllerStudent;
import ca.aquiletour.core.pages.dashboards.student.messages.ShowStudentDashboardMessage;
import ca.aquiletour.core.pages.dashboards.teacher.DashboardControllerTeacher;
import ca.aquiletour.core.pages.dashboards.teacher.messages.ShowTeacherDashboardMessage;
import ca.aquiletour.core.pages.git.CommitListController;
import ca.aquiletour.core.pages.home.HomeController;
import ca.aquiletour.core.pages.login.LoginController;
import ca.aquiletour.core.pages.login.ShowLoginMessage;
import ca.aquiletour.core.pages.open_queue_list.OpenQueueListController;
import ca.aquiletour.core.pages.queue.student.QueueControllerStudent;
import ca.aquiletour.core.pages.queue.teacher.QueueControllerTeacher;
import ca.aquiletour.core.pages.semester_list.SemesterListController;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroRootController;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageHandler;
import ca.ntro.services.Ntro;

public class RootController extends NtroRootController {
	
	// FIXME: ugly, but modelStore does not support
	//        two models of the same kind
	private boolean ifRoleSpecificSubControllersAdded = false;
	
	@Override
	protected void onCreate(NtroContext<?> context) {
		T.call(this);
		
		setViewLoader(RootView.class, context.lang());
		
		// FIXME: modelStore does not support
		//        two models of the same kind (or with the same DocumentPath)
		addRoleSpecificSubControllers(context);

		addSubController(OpenQueueListController.class, Constants.QUEUES_URL_SEGMENT);
	
		addSubController(LoginController.class, Constants.LOGIN_URL_SEGMENT);
		addSubController(HomeController.class, Constants.HOME_URL_SEGMENT);

		addSubController(CommitListController.class, Constants.GIT_PROGRESS_URL_SEGMENT);

		addSubController(SemesterListController.class, Constants.SEMESTER_LIST_URL_SEGMENT);

		addSubController(CourseListController.class, Constants.COURSE_LIST_URL_SEGMENT);

		addWindowViewHandler(new RootViewHandler());
		
		// FIXME: could be in main. Not specific to Controller
		Ntro.messages().registerHandler(QuitMessage.class, new QuitMessageHandler());
		
		Ntro.messages().registerHandler(ShowDashboardMessage.class, new MessageHandler<ShowDashboardMessage>() {
			@Override
			public void handle(ShowDashboardMessage message) {
				T.call(this);

				if(context().user() instanceof Teacher) {

					ShowTeacherDashboardMessage showDashboardMessage = Ntro.messages().create(ShowTeacherDashboardMessage.class);
					Ntro.messages().send(showDashboardMessage);

				}else if(context().user() instanceof Student){

					ShowStudentDashboardMessage showDashboardMessage = Ntro.messages().create(ShowStudentDashboardMessage.class);
					Ntro.messages().send(showDashboardMessage);

				}else {

					ShowLoginMessage showLoginMessage = Ntro.messages().create(ShowLoginMessage.class);
					showLoginMessage.setMessageToUser("SVP entrer votre DA pour voir vos cours");
					Ntro.messages().send(showLoginMessage);

				}
			}
		});
	}

	@Override
	protected void onChangeContext(NtroContext<?> previousContext, NtroContext<?> context) {
		T.call(this);
		
		RootView view = (RootView) getView();

		view.adjustLoginLinkText(context);
		
		addRoleSpecificSubControllers(context);
	}

	private void addRoleSpecificSubControllers(NtroContext<?> context) {
		T.call(this);
		
		if(ifRoleSpecificSubControllersAdded) return;

		if(context.user() instanceof Teacher
				|| context.user() instanceof TeacherGuest) {
			
			addSubController(QueueControllerTeacher.class, Constants.QUEUE_URL_SEGMENT);
			addSubController(DashboardControllerTeacher.class, Constants.DASHBOARD_URL_SEGMENT);
			addSubController(CourseControllerTeacher.class, Constants.COURSE_URL_SEGMENT);
			
			ifRoleSpecificSubControllersAdded = true;
			
		}else if(context.user() instanceof Student
				|| context.user() instanceof StudentGuest){

			addSubController(QueueControllerStudent.class, Constants.QUEUE_URL_SEGMENT);
			addSubController(DashboardControllerStudent.class, Constants.DASHBOARD_URL_SEGMENT);
			addSubController(CourseControllerStudent.class, Constants.COURSE_URL_SEGMENT);

			ifRoleSpecificSubControllersAdded = true;
		}
	}

	@Override
	protected void onFailure(Exception e) {
		T.call(this);
	}

}

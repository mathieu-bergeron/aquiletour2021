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

import ca.aquiletour.core.messages.ShowDashboardMessage;
import ca.aquiletour.core.models.users.Student;
import ca.aquiletour.core.models.users.StudentGuest;
import ca.aquiletour.core.models.users.Teacher;
import ca.aquiletour.core.models.users.TeacherGuest;
import ca.aquiletour.core.pages.course.CourseController;
import ca.aquiletour.core.pages.course.student.CourseControllerStudent;
import ca.aquiletour.core.pages.course.teacher.CourseControllerTeacher;
import ca.aquiletour.core.pages.dashboards.student.DashboardControllerStudent;
import ca.aquiletour.core.pages.dashboards.student.messages.ShowStudentDashboardMessage;
import ca.aquiletour.core.pages.dashboards.teacher.DashboardControllerTeacher;
import ca.aquiletour.core.pages.dashboards.teacher.messages.ShowTeacherDashboardMessage;
import ca.aquiletour.core.pages.git.CommitListController;
import ca.aquiletour.core.pages.home.HomeController;
import ca.aquiletour.core.pages.login.LoginController;
import ca.aquiletour.core.pages.login.ShowLoginMessage;
import ca.aquiletour.core.pages.queue.student.QueueControllerStudent;
import ca.aquiletour.core.pages.queue.teacher.QueueControllerTeacher;
import ca.aquiletour.core.pages.queues.QueuesController;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroRootController;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageHandler;
import ca.ntro.services.Ntro;

public class RootController extends NtroRootController {
	
	// FIXME: ugly, but modelStore does not support
	//        two models of the same kind
	private boolean ifStudentOrTeacherSubControllerAdded = false;
	
	@Override
	protected void onCreate(NtroContext<?> context) {
		T.call(this);
		
		setViewLoader(RootView.class, context.lang());
		
		// FIXME: modelStore does not support
		//        two models of the same kind (or with the same DocumentPath)
		addStudentOrTeacherSubController(context);

		addSubController(QueuesController.class, "profs");
	

		addSubController(LoginController.class, "connexion");
		addSubController(HomeController.class, "accueil");
		addSubController(CommitListController.class, "progressiongit");

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
		
		addStudentOrTeacherSubController(context);
	}

	private void addStudentOrTeacherSubController(NtroContext<?> context) {
		T.call(this);
		
		if(ifStudentOrTeacherSubControllerAdded) return;

		if(context.user() instanceof Teacher
				|| context.user() instanceof TeacherGuest) {
			
			addSubController(QueueControllerTeacher.class, "billetterie");
			addSubController(DashboardControllerTeacher.class, "mescours");
			addSubController(CourseControllerTeacher.class, "cours");
			
			ifStudentOrTeacherSubControllerAdded = true;
			
		}else if(context.user() instanceof Student
				|| context.user() instanceof StudentGuest){

			addSubController(QueueControllerStudent.class, "billetterie");
			addSubController(DashboardControllerStudent.class, "mescours");
			addSubController(CourseControllerStudent.class, "cours");

			ifStudentOrTeacherSubControllerAdded = true;
		}
	}

	@Override
	protected void onFailure(Exception e) {
		T.call(this);
	}

}

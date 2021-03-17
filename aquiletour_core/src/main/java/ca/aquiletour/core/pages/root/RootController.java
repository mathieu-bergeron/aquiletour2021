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
import ca.aquiletour.core.pages.dashboards.student.StudentDashboardController;
import ca.aquiletour.core.pages.dashboards.student.messages.ShowStudentDashboardMessage;
import ca.aquiletour.core.pages.dashboards.teacher.TeacherDashboardController;
import ca.aquiletour.core.pages.dashboards.teacher.messages.ShowTeacherDashboardMessage;
import ca.aquiletour.core.pages.home.HomeController;
import ca.aquiletour.core.pages.login.LoginController;
import ca.aquiletour.core.pages.login.ShowLoginMessage;
import ca.aquiletour.core.pages.queue.student.StudentQueueController;
import ca.aquiletour.core.pages.queue.teacher.TeacherQueueController;
import ca.aquiletour.core.pages.queues.QueuesController;
import ca.ntro.core.mvc.NtroContext;
import ca.aquiletour.core.pages.users.UsersController;
import ca.ntro.core.mvc.NtroRootController;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageHandler;
import ca.ntro.services.Ntro;

public class RootController extends NtroRootController {

	@Override
	protected void onCreate() {
		T.call(this);
		
		setViewLoader(RootView.class, currentContext().lang());
		
		// FIXME: this means we cannot switch user w/o reloading
		if(currentContext().user() instanceof Teacher) {
			addSubController(TeacherDashboardController.class, "mescours");
		}else {
			addSubController(StudentDashboardController.class, "mescours");
		}

		addSubController(QueuesController.class, "billetteries");
	
		addSubController(TeacherQueueController.class, "billetterie");
		addSubController(StudentQueueController.class, "billetterie");

		addSubController(UsersController.class, "usagers");
		addSubController(LoginController.class, "connexion");
		addSubController(HomeController.class, "accueil");

		addWindowViewHandler(new RootViewHandler());
		
		// FIXME: could be in main. Not specific to Controller
		Ntro.messages().registerHandler(QuitMessage.class, new QuitMessageHandler());
		
		Ntro.messages().registerHandler(ShowDashboardMessage.class, new MessageHandler<ShowDashboardMessage>() {
			@Override
			public void handle(ShowDashboardMessage message) {
				T.call(this);

				if(currentContext().user() instanceof Teacher) {

					ShowTeacherDashboardMessage showDashboardMessage = Ntro.messages().create(ShowTeacherDashboardMessage.class);
					Ntro.messages().send(showDashboardMessage);

				}else if(currentContext().user() instanceof Student){

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
	protected void onChangeContext(NtroContext<?> previousContext) {
		T.call(this);
		
	}

	@Override
	protected void onFailure(Exception e) {
		T.call(this);
	}

}

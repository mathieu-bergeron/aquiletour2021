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

package ca.aquiletour.javafx;

import ca.aquiletour.core.AquiletourMain;
import ca.aquiletour.core.pages.dashboards.CourseSummaryView;
import ca.aquiletour.core.pages.dashboards.student.StudentDashboardView;
import ca.aquiletour.core.pages.dashboards.teacher.TeacherDashboardView;
import ca.aquiletour.core.pages.home.HomeView;
import ca.aquiletour.core.pages.login.LoginView;
import ca.aquiletour.core.pages.queue.AppointmentView;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.queues.QueueSummaryView;
import ca.aquiletour.core.pages.queues.QueuesView;
import ca.aquiletour.core.pages.root.RootView;
import ca.aquiletour.core.pages.users.UserView;
import ca.aquiletour.core.pages.users.UsersView;
import ca.ntro.core.mvc.NtroWindow;
import ca.ntro.core.mvc.ViewLoaders;
import ca.ntro.core.system.trace.T;
import ca.ntro.javafx.NtroWindowFx;
import ca.ntro.javafx.ViewLoaderFx;
import javafx.stage.Stage;

public class AquiletourMainFx extends AquiletourMain {
	
	private Stage primaryStage;
	
	public AquiletourMainFx(Stage primaryStage) {
		T.call(this);
		
		this.primaryStage = primaryStage;
	}

	@Override
	protected void registerViewLoaders() {
		T.call(this);

		ViewLoaders.registerViewLoader(RootView.class,
				"fr"
				, new ViewLoaderFx()
			     	.setFxmlUrl("/views/root/root.xml")
			     	.setCssUrl("/views/root/root.css")
			     	.setTranslationsName("i18n.strings"));

		ViewLoaders.registerViewLoader(TeacherDashboardView.class,
				"fr"
				, new ViewLoaderFx()
			     	.setFxmlUrl("/views/dashboards/teacher/teacher_dashboard.xml")
			     	.setCssUrl("/views/dashboards/student/teacher_dashboard.css")
			     	.setTranslationsName("i18n.strings"));

		ViewLoaders.registerViewLoader(StudentDashboardView.class,
				"fr"
				, new ViewLoaderFx()
			     	.setFxmlUrl("/views/dashboards/teacher/student_dashboard.xml")
			     	.setCssUrl("/views/dashboards/student/student_dashboard.css")
			     	.setTranslationsName("i18n.strings"));

		ViewLoaders.registerViewLoader(CourseSummaryView.class,
				"fr"
				, new ViewLoaderFx()
			     	.setFxmlUrl("/views/course_summary/course_summary.xml")
			     	.setCssUrl("/views/course_summary/course_summary.css")
			     	.setTranslationsName("i18n.strings"));

		ViewLoaders.registerViewLoader(QueueView.class,
				"fr"
				, new ViewLoaderFx()
			     	.setFxmlUrl("/views/queue/queue.xml")
			     	.setCssUrl("/views/queue/queue.css")
			     	.setTranslationsName("i18n.strings"));


		ViewLoaders.registerViewLoader(QueuesView.class,
				"fr"
				, new ViewLoaderFx()
			     	.setFxmlUrl("/views/queues/queues.xml")
			     	.setCssUrl("/views/queues/queues.css")
			     	.setTranslationsName("i18n.strings"));

		ViewLoaders.registerViewLoader(QueueSummaryView.class,
				"fr"
				, new ViewLoaderFx()
			     	.setFxmlUrl("/views/queue_summary/queue_summary.xml")
			     	.setCssUrl("/views/queue_summary/queue_summary.css")
			     	.setTranslationsName("i18n.strings"));

		ViewLoaders.registerViewLoader(AppointmentView.class,
				"fr"
				, new ViewLoaderFx()
			     	.setFxmlUrl("/views/appointment/appointment.xml")
			     	.setCssUrl("/views/appointment/appointment.css")
			     	.setTranslationsName("i18n.strings"));

		ViewLoaders.registerViewLoader(UsersView.class,
				"fr"
				, new ViewLoaderFx()
			     	.setFxmlUrl("/views/users/users.xml")
			     	.setCssUrl("/views/users/users.css")
			     	.setTranslationsName("i18n.strings"));

		ViewLoaders.registerViewLoader(UserView.class,
				"fr"
				, new ViewLoaderFx()
			     	.setFxmlUrl("/views/user/user.xml")
			     	.setCssUrl("/views/user/user.css")
			     	.setTranslationsName("i18n.strings"));

		ViewLoaders.registerViewLoader(LoginView.class,
				"fr"
				, new ViewLoaderFx()
			     	.setFxmlUrl("/views/login/login.xml")
			     	.setCssUrl("/views/login/login.css")
			     	.setTranslationsName("i18n.strings"));

		ViewLoaders.registerViewLoader(HomeView.class,
				"fr"
				, new ViewLoaderFx()
			     	.setFxmlUrl("/views/home/home.xml")
			     	.setCssUrl("/views/home/home.css")
			     	.setTranslationsName("i18n.strings"));

	}

	@Override
	protected NtroWindow getWindow() {
		T.call(this);
		
		return new NtroWindowFx(primaryStage);
	}
}

package ca.aquiletour.web;

import ca.aquiletour.core.pages.dashboards.CourseSummaryView;
import ca.aquiletour.core.pages.dashboards.student.StudentDashboardView;
import ca.aquiletour.core.pages.dashboards.teacher.TeacherDashboardView;
import ca.aquiletour.core.pages.login.LoginView;
import ca.aquiletour.core.pages.queue.AppointmentView;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.queues.QueueSummaryView;
import ca.aquiletour.core.pages.queues.QueuesView;
import ca.aquiletour.core.pages.root.RootView;
import ca.aquiletour.core.pages.users.UserView;
import ca.aquiletour.core.pages.users.UsersView;
import ca.aquiletour.web.pages.dashboard.CourseSummaryViewWeb;
import ca.aquiletour.web.pages.dashboard.StudentDashboardViewWeb;
import ca.aquiletour.web.pages.dashboard.TeacherDashboardViewWeb;
import ca.aquiletour.web.pages.login.LoginViewWeb;
import ca.aquiletour.web.pages.queue.AppointmentViewWeb;
import ca.aquiletour.web.pages.queue.QueueViewWeb;
import ca.aquiletour.web.pages.queues.QueueSummaryViewWeb;
import ca.aquiletour.web.pages.queues.QueuesViewWeb;
import ca.aquiletour.web.pages.root.RootViewWeb;
import ca.aquiletour.web.pages.users.UserViewWeb;
import ca.aquiletour.web.pages.users.UsersViewWeb;
import ca.ntro.core.Ntro;
import ca.ntro.core.mvc.ViewLoaders;
import ca.ntro.core.system.trace.T;

public class ViewLoaderRegistrationWeb {
	
	public static void registerViewLoaders() {
		T.call(ViewLoaderRegistrationWeb.class);
		
		ViewLoaders.registerViewLoader(RootView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/root/root.html")
			     	.setCssUrl("/views/root/root.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(RootViewWeb.class));

		ViewLoaders.registerViewLoader(TeacherDashboardView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/dashboards/teacher_dashboard/teacher_dashboard.html")
			     	.setCssUrl("/views/dashboards/teacher_dashboard/teacher_dashboard.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(TeacherDashboardViewWeb.class));

		ViewLoaders.registerViewLoader(StudentDashboardView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/dashboards/student_dashboard/student_dashboard.html")
			     	.setCssUrl("/views/dashboards/student_dashboard/student_dashboard.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(StudentDashboardViewWeb.class));

		ViewLoaders.registerViewLoader(CourseSummaryView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/course_summary/course_summary.html")
			     	.setCssUrl("/views/course_summary/course_summary.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(CourseSummaryViewWeb.class));

		ViewLoaders.registerViewLoader(QueueView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/queue/queue.html")
			     	.setCssUrl("/views/queue/queue.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(QueueViewWeb.class));

		ViewLoaders.registerViewLoader(QueuesView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/queues/queues.html")
			     	.setCssUrl("/views/queues/queues.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(QueuesViewWeb.class));

		ViewLoaders.registerViewLoader(QueueSummaryView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/queue_summary/queue_summary.html")
			     	.setCssUrl("/views/queue_summary/queue_summary.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(QueueSummaryViewWeb.class));

		ViewLoaders.registerViewLoader(AppointmentView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/appointment/appointment.html")
			     	.setCssUrl("/views/appointment/appointment.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(AppointmentViewWeb.class));
		
		ViewLoaders.registerViewLoader(UsersView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/users/users.html")
			     	.setCssUrl("/views/users/users.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(UsersViewWeb.class));

		ViewLoaders.registerViewLoader(UserView.class,
				"fr"
				, Ntro.viewLoaderWeb()
				.setHtmlUrl("/views/user/user.html")
				.setCssUrl("/views/user/user.css")
				.setTranslationsUrl("/i18n/fr/string.json")
				.setTargetClass(UserViewWeb.class));
		
		ViewLoaders.registerViewLoader(LoginView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/login/login.html")
			     	.setCssUrl("/views/login/login.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(LoginViewWeb.class));

	}
}

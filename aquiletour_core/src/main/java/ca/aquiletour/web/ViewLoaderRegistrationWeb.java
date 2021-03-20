package ca.aquiletour.web;

import ca.aquiletour.core.pages.dashboards.student.StudentCourseSummaryView;
import ca.aquiletour.core.pages.dashboards.student.StudentDashboardView;
import ca.aquiletour.core.pages.dashboards.teacher.TeacherCourseSummaryView;
import ca.aquiletour.core.pages.dashboards.teacher.TeacherDashboardView;
import ca.aquiletour.core.pages.home.HomeView;
import ca.aquiletour.core.pages.login.LoginView;
import ca.aquiletour.core.pages.queue.student.StudentAppointmentView;
import ca.aquiletour.core.pages.queue.student.StudentQueueView;
import ca.aquiletour.core.pages.queue.teacher.TeacherAppointmentView;
import ca.aquiletour.core.pages.queue.teacher.TeacherQueueView;
import ca.aquiletour.core.pages.queues.QueueSummaryView;
import ca.aquiletour.core.pages.queues.QueuesView;
import ca.aquiletour.core.pages.root.RootView;
import ca.aquiletour.web.pages.dashboard.student.StudentCourseSummaryViewWeb;
import ca.aquiletour.web.pages.dashboard.student.StudentDashboardViewWeb;
import ca.aquiletour.web.pages.dashboard.teacher.TeacherCourseSummaryViewWeb;
import ca.aquiletour.web.pages.dashboard.teacher.TeacherDashboardViewWeb;
import ca.aquiletour.web.pages.home.HomeViewWeb;
import ca.aquiletour.web.pages.login.LoginViewWeb;
import ca.aquiletour.web.pages.queue.student.StudentAppointmentViewWeb;
import ca.aquiletour.web.pages.queue.student.StudentQueueViewWeb;
import ca.aquiletour.web.pages.queue.teacher.TeacherAppointmentViewWeb;
import ca.aquiletour.web.pages.queue.teacher.TeacherQueueViewWeb;
import ca.aquiletour.web.pages.queues.QueueSummaryViewWeb;
import ca.aquiletour.web.pages.queues.QueuesViewWeb;
import ca.aquiletour.web.pages.root.RootViewWeb;
import ca.ntro.core.mvc.ViewLoaders;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

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
			     	.setHtmlUrl("/views/dashboard/teacher/dashboard.html")
			     	.setCssUrl("/views/dashboard/teacher/dashboard.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(TeacherDashboardViewWeb.class));

		ViewLoaders.registerViewLoader(StudentDashboardView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/dashboard/student/student_dashboard.html")
			     	.setCssUrl("/views/dashboard/student/student_dashboard.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(StudentDashboardViewWeb.class));

		ViewLoaders.registerViewLoader(TeacherCourseSummaryView.class,
				"fr"
				, Ntro.viewLoaderWeb()
				.setHtmlUrl("/views/course_summary/teacher/course_summary.html")
		     	.setCssUrl("/views/course_summary/teacher/course_summary.css")
				.setTranslationsUrl("/i18n/fr/string.json")
				.setTargetClass(TeacherCourseSummaryViewWeb.class));
		
		ViewLoaders.registerViewLoader(StudentCourseSummaryView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/course_summary/student/course_summary.html")
			     	.setCssUrl("/views/course_summary/student/course_summary.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(StudentCourseSummaryViewWeb.class));

		ViewLoaders.registerViewLoader(StudentQueueView.class,
				"fr"
				, Ntro.viewLoaderWeb()
				.setHtmlUrl("/views/queue/student/queue.html")
				.setCssUrl("/views/queue/student/queue.css")
				.setTranslationsUrl("/i18n/fr/string.json")
				.setTargetClass(StudentQueueViewWeb.class));
		
		ViewLoaders.registerViewLoader(TeacherQueueView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/queue/teacher/queue.html")
			     	.setCssUrl("/views/queue/teacher/queue.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(TeacherQueueViewWeb.class));

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

		ViewLoaders.registerViewLoader(TeacherAppointmentView.class,
				"fr"
				, Ntro.viewLoaderWeb()
				.setHtmlUrl("/views/appointment/teacher/appointment.html")
				.setCssUrl("/views/appointment/teacher/appointment.css")
				.setTranslationsUrl("/i18n/fr/string.json")
				.setTargetClass(TeacherAppointmentViewWeb.class));
		
		ViewLoaders.registerViewLoader(StudentAppointmentView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/appointment/student/appointment.html")
			     	.setCssUrl("/views/appointment/student/appointment.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(StudentAppointmentViewWeb.class));

		ViewLoaders.registerViewLoader(LoginView.class,
				"fr"
				, Ntro.viewLoaderWeb()
				.setHtmlUrl("/views/login/login.html")
				.setCssUrl("/views/login/login.css")
				.setTranslationsUrl("/i18n/fr/string.json")
				.setTargetClass(LoginViewWeb.class));
		
		ViewLoaders.registerViewLoader(HomeView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/home/home.html")
			     	.setCssUrl("/views/home/home.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(HomeViewWeb.class));

	}
}

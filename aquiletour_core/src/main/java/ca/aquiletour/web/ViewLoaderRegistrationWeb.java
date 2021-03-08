package ca.aquiletour.web;

import ca.aquiletour.core.pages.dashboards.CourseSummaryView;
import ca.aquiletour.core.pages.dashboards.student.StudentCourseSummaryView;
import ca.aquiletour.core.pages.dashboards.student.StudentDashboardView;
import ca.aquiletour.core.pages.dashboards.teacher.TeacherCourseSummaryView;
import ca.aquiletour.core.pages.dashboards.teacher.TeacherDashboardView;
import ca.aquiletour.core.pages.home.HomeView;
import ca.aquiletour.core.pages.login.LoginView;
import ca.aquiletour.core.pages.queue.AppointmentView;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.queue.student.StudentAppointmentView;
import ca.aquiletour.core.pages.queue.student.StudentQueueView;
import ca.aquiletour.core.pages.queue.teacher.TeacherAppointmentView;
import ca.aquiletour.core.pages.queue.teacher.TeacherQueueView;
import ca.aquiletour.core.pages.queues.QueueSummaryView;
import ca.aquiletour.core.pages.queues.QueuesView;
import ca.aquiletour.core.pages.root.RootView;
import ca.aquiletour.core.pages.users.UserView;
import ca.aquiletour.core.pages.users.UsersView;
import ca.aquiletour.web.pages.dashboard.student.StudentCourseSummaryViewWeb;
import ca.aquiletour.web.pages.dashboard.student.StudentDashboardViewWeb;
import ca.aquiletour.web.pages.dashboard.teacher.TeacherCourseSummaryViewWeb;
import ca.aquiletour.web.pages.dashboard.teacher.TeacherDashboardViewWeb;
import ca.aquiletour.web.pages.home.HomeViewWeb;
import ca.aquiletour.web.pages.login.LoginViewWeb;
import ca.aquiletour.web.pages.queue.AppointmentViewWeb;
import ca.aquiletour.web.pages.queue.QueueViewWeb;
import ca.aquiletour.web.pages.queue.student.StudentAppointmentViewWeb;
import ca.aquiletour.web.pages.queue.student.StudentQueueViewWeb;
import ca.aquiletour.web.pages.queue.teacher.TeacherAppointmentViewWeb;
import ca.aquiletour.web.pages.queue.teacher.TeacherQueueViewWeb;
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

		ViewLoaders.registerViewLoader(TeacherCourseSummaryView.class,
				"fr"
				, Ntro.viewLoaderWeb()
				.setHtmlUrl("/views/course_summaries/teacher_course_summary/course_summary.html")
		     	.setCssUrl("/views/course_summaries/teacher_course_summary/course_summary.css")
				.setTranslationsUrl("/i18n/fr/string.json")
				.setTargetClass(TeacherCourseSummaryViewWeb.class));
		
		ViewLoaders.registerViewLoader(StudentCourseSummaryView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/course_summaries/student_course_summary/course_summary.html")
			     	.setCssUrl("/views/course_summaries/student_course_summary/course_summary.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(StudentCourseSummaryViewWeb.class));

		ViewLoaders.registerViewLoader(StudentQueueView.class,
				"fr"
				, Ntro.viewLoaderWeb()
				.setHtmlUrl("/views/queue/student_queue/queue.html")
				.setCssUrl("/views/queue/student_queue/queue.css")
				.setTranslationsUrl("/i18n/fr/string.json")
				.setTargetClass(StudentQueueViewWeb.class));
		
		ViewLoaders.registerViewLoader(TeacherQueueView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/queue/teacher_queue/queue.html")
			     	.setCssUrl("/views/queue/teacher_queue/queue.css")
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
				.setHtmlUrl("/views/appointment/teacher_appointment/appointment.html")
				.setCssUrl("/views/appointment/teacher_appointment/appointment.css")
				.setTranslationsUrl("/i18n/fr/string.json")
				.setTargetClass(TeacherAppointmentViewWeb.class));
		
		ViewLoaders.registerViewLoader(StudentAppointmentView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/appointment/student_appointment/appointment.html")
			     	.setCssUrl("/views/appointment/student_appointment/appointment.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(StudentAppointmentViewWeb.class));
		
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
		
		ViewLoaders.registerViewLoader(HomeView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/home/home.html")
			     	.setCssUrl("/views/home/home.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(HomeViewWeb.class));

	}
}

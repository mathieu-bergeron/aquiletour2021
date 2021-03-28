package ca.aquiletour.web;

import ca.aquiletour.core.pages.course.student.views.CourseViewStudent;
import ca.aquiletour.core.pages.course.student.views.TaskViewStudent;
import ca.aquiletour.core.pages.course.teacher.views.CourseViewTeacher;
import ca.aquiletour.core.pages.course.teacher.views.TaskViewTeacher;
import ca.aquiletour.core.pages.course.views.CourseView;
import ca.aquiletour.core.pages.course.views.TaskView;
import ca.aquiletour.core.pages.dashboards.student.StudentCourseSummaryView;
import ca.aquiletour.core.pages.dashboards.student.StudentDashboardView;
import ca.aquiletour.core.pages.dashboards.teacher.TeacherCourseSummaryView;
import ca.aquiletour.core.pages.dashboards.teacher.TeacherDashboardView;
import ca.aquiletour.core.pages.git.CommitListView;
import ca.aquiletour.core.pages.git.commit_list.CommitView;
import ca.aquiletour.core.pages.home.HomeView;
import ca.aquiletour.core.pages.login.LoginView;
import ca.aquiletour.core.pages.queue.student.StudentAppointmentView;
import ca.aquiletour.core.pages.queue.student.StudentQueueView;
import ca.aquiletour.core.pages.queue.teacher.TeacherAppointmentView;
import ca.aquiletour.core.pages.queue.teacher.TeacherQueueView;
import ca.aquiletour.core.pages.queues.QueueSummaryView;
import ca.aquiletour.core.pages.queues.QueuesView;
import ca.aquiletour.core.pages.root.RootView;
import ca.aquiletour.web.pages.course.student.CourseViewWebStudent;
import ca.aquiletour.web.pages.course.student.TaskViewWebStudent;
import ca.aquiletour.web.pages.course.teacher.CourseViewWebTeacher;
import ca.aquiletour.web.pages.course.teacher.TaskViewWebTeacher;
import ca.aquiletour.web.pages.dashboard.student.CourseSummaryViewWebStudent;
import ca.aquiletour.web.pages.dashboard.student.StudentDashboardViewWeb;
import ca.aquiletour.web.pages.dashboard.teacher.CourseSummaryViewWebTeacher;
import ca.aquiletour.web.pages.dashboard.teacher.TeacherDashboardViewWeb;
import ca.aquiletour.web.pages.git.CommitListViewWeb;
import ca.aquiletour.web.pages.git.CommitViewWeb;
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
			     	.setHtmlUrl("/views/dashboard/teacher/dashboard_teacher.html")
			     	.setCssUrl("/views/dashboard/teacher/dashboard_teacher.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(TeacherDashboardViewWeb.class));

		ViewLoaders.registerViewLoader(StudentDashboardView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/dashboard/student/dashboard_student.html")
			     	.setCssUrl("/views/dashboard/student/dashboard_student.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(StudentDashboardViewWeb.class));

		ViewLoaders.registerViewLoader(TeacherCourseSummaryView.class,
				"fr"
				, Ntro.viewLoaderWeb()
				.setHtmlUrl("/views/course_summary/teacher/course_summary_teacher.html")
		     	.setCssUrl("/views/course_summary/teacher/course_summary_teacher.css")
				.setTranslationsUrl("/i18n/fr/string.json")
				.setTargetClass(CourseSummaryViewWebTeacher.class));
		
		ViewLoaders.registerViewLoader(StudentCourseSummaryView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/course_summary/student/course_summary_student.html")
			     	.setCssUrl("/views/course_summary/student/course_summary_student.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(CourseSummaryViewWebStudent.class));

		ViewLoaders.registerViewLoader(StudentQueueView.class,
				"fr"
				, Ntro.viewLoaderWeb()
				.setHtmlUrl("/views/queue/student/queue_student.html")
				.setCssUrl("/views/queue/student/queue_student.css")
				.setTranslationsUrl("/i18n/fr/string.json")
				.setTargetClass(StudentQueueViewWeb.class));
		
		ViewLoaders.registerViewLoader(TeacherQueueView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/queue/teacher/queue_teacher.html")
			     	.setCssUrl("/views/queue/teacher/queue_teacher.css")
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
				.setHtmlUrl("/views/appointment/teacher/appointment_teacher.html")
				.setCssUrl("/views/appointment/teacher/appointment_teacher.css")
				.setTranslationsUrl("/i18n/fr/string.json")
				.setTargetClass(TeacherAppointmentViewWeb.class));
		
		ViewLoaders.registerViewLoader(StudentAppointmentView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/appointment/student/appointment_student.html")
			     	.setCssUrl("/views/appointment/student/appointment_student.css")
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
		
		ViewLoaders.registerViewLoader(CommitListView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/commitList/git_progression_commit_list.html")
			     	.setCssUrl("/views/commitList/git_progression_commit_list.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(CommitListViewWeb.class));
		
		ViewLoaders.registerViewLoader(CommitView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/commit/git_progression_commit.html")
			     	.setCssUrl("/views/commit/git_progression_commit.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(CommitViewWeb.class));

		ViewLoaders.registerViewLoader(CourseViewTeacher.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/course/teacher/course_teacher.html")
			     	.setCssUrl("/views/course/teacher/course_teacher.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(CourseViewWebTeacher.class));

		ViewLoaders.registerViewLoader(TaskViewTeacher.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/task/teacher/task_teacher.html")
			     	.setCssUrl("/views/task/teacher/task_teacher.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(TaskViewWebTeacher.class));

		ViewLoaders.registerViewLoader(CourseViewStudent.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/course/student/course_student.html")
			     	.setCssUrl("/views/course/student/course_student.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(CourseViewWebStudent.class));

		ViewLoaders.registerViewLoader(TaskViewStudent.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/task/student/task_student.html")
			     	.setCssUrl("/views/task/student/task_student.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(TaskViewWebStudent.class));
	}
}

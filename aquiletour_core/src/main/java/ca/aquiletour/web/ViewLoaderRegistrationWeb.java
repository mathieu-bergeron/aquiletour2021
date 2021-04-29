package ca.aquiletour.web;

import ca.aquiletour.core.pages.course.student.views.CourseViewStudent;
import ca.aquiletour.core.pages.course.student.views.TaskViewStudent;
import ca.aquiletour.core.pages.course.teacher.views.CourseViewTeacher;
import ca.aquiletour.core.pages.course.teacher.views.TaskViewTeacher;
import ca.aquiletour.core.pages.course_list.views.CourseListView;
import ca.aquiletour.core.pages.course_list.student.views.CourseItemViewStudent;
import ca.aquiletour.core.pages.course_list.student.views.CourseListViewStudent;
import ca.aquiletour.core.pages.course_list.teacher.views.CourseItemViewTeacher;
import ca.aquiletour.core.pages.course_list.teacher.views.CourseListViewTeacher;
import ca.aquiletour.core.pages.course_list.views.CourseItemView;
import ca.aquiletour.core.pages.dashboards.student.DashboardCourseViewStudent;
import ca.aquiletour.core.pages.dashboards.student.DashboardViewStudent;
import ca.aquiletour.core.pages.dashboards.teacher.DashboardCourseViewTeacher;
import ca.aquiletour.core.pages.dashboards.teacher.TeacherDashboardView;
import ca.aquiletour.core.pages.git.CommitListView;
import ca.aquiletour.core.pages.git.commit_list.CommitView;
import ca.aquiletour.core.pages.group_list.views.GroupView;
import ca.aquiletour.core.pages.group_list.views.GroupListView;
import ca.aquiletour.core.pages.home.HomeView;
import ca.aquiletour.core.pages.login.LoginView;
import ca.aquiletour.core.pages.open_queue_list.OpenQueueListView;
import ca.aquiletour.core.pages.open_queue_list.OpenQueueView;
import ca.aquiletour.core.pages.queue.student.AppointmentViewStudent;
import ca.aquiletour.core.pages.queue.student.StudentQueueView;
import ca.aquiletour.core.pages.queue.teacher.AppointmentViewTeacher;
import ca.aquiletour.core.pages.queue.teacher.TeacherQueueView;
import ca.aquiletour.core.pages.root.RootView;
import ca.aquiletour.core.pages.semester_list.views.SemesterListView;
import ca.aquiletour.core.pages.semester_list.views.SemesterView;
import ca.aquiletour.web.pages.course.student.CourseViewWebStudent;
import ca.aquiletour.web.pages.course.student.TaskViewWebStudent;
import ca.aquiletour.web.pages.course.teacher.CourseViewWebTeacher;
import ca.aquiletour.web.pages.course.teacher.TaskViewWebTeacher;
import ca.aquiletour.web.pages.course_list.CourseListViewWeb;
import ca.aquiletour.web.pages.course_list.student.CourseItemViewWebTeacher;
import ca.aquiletour.web.pages.course_list.student.CourseListViewWebTeacher;
import ca.aquiletour.web.pages.course_list.teacher.CourseItemViewWebStudent;
import ca.aquiletour.web.pages.course_list.teacher.CourseListViewWebStudent;
import ca.aquiletour.web.pages.course_list.CourseItemViewWeb;
import ca.aquiletour.web.pages.dashboard.DashboardItemViewWeb;
import ca.aquiletour.web.pages.dashboard.student.DashboardItemViewWebStudent;
import ca.aquiletour.web.pages.dashboard.student.DashboardViewWebStudent;
import ca.aquiletour.web.pages.dashboard.teacher.DashboardItemViewWebTeacher;
import ca.aquiletour.web.pages.dashboard.teacher.DashboardViewWebTeacher;
import ca.aquiletour.web.pages.git.CommitListViewWeb;
import ca.aquiletour.web.pages.git.CommitViewWeb;
import ca.aquiletour.web.pages.group_list.GroupViewWeb;
import ca.aquiletour.web.pages.group_list.GroupListViewWeb;
import ca.aquiletour.web.pages.home.HomeViewWeb;
import ca.aquiletour.web.pages.login.LoginViewWeb;
import ca.aquiletour.web.pages.queue.student.AppointmentViewWebStudent;
import ca.aquiletour.web.pages.queue.student.QueueViewWebStudent;
import ca.aquiletour.web.pages.queue.teacher.AppointmentViewWebTeacher;
import ca.aquiletour.web.pages.queue.teacher.QueueViewWebTeacher;
import ca.aquiletour.web.pages.queues.QueueSummaryViewWeb;
import ca.aquiletour.web.pages.queues.QueuesViewWeb;
import ca.aquiletour.web.pages.root.RootViewWeb;
import ca.aquiletour.web.pages.semester_list.SemesterListViewWeb;
import ca.aquiletour.web.pages.semester_list.SemesterViewWeb;
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
			     	.setTargetClass(DashboardViewWebTeacher.class));

		ViewLoaders.registerViewLoader(DashboardViewStudent.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/dashboard/student/dashboard_student.html")
			     	.setCssUrl("/views/dashboard/student/dashboard_student.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(DashboardViewWebStudent.class));

		ViewLoaders.registerViewLoader(DashboardCourseViewTeacher.class,
				"fr"
				, Ntro.viewLoaderWeb()
				.setHtmlUrl("/partials/dashboard_item/teacher/dashboard_item_teacher.html")
		     	.setCssUrl("/partials/dashboard_item/teacher/dashboard_item_teacher.css")
				.setTranslationsUrl("/i18n/fr/string.json")
				.setTargetClass(DashboardItemViewWebTeacher.class));
		
		ViewLoaders.registerViewLoader(DashboardCourseViewStudent.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/partials/dashboard_item/student/dashboard_item_student.html")
			     	.setCssUrl("/partials/dashboard_item/student/dashboard_item_student.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(DashboardItemViewWebStudent.class));

		ViewLoaders.registerViewLoader(StudentQueueView.class,
				"fr"
				, Ntro.viewLoaderWeb()
				.setHtmlUrl("/views/queue/student/queue_student.html")
				.setCssUrl("/views/queue/student/queue_student.css")
				.setTranslationsUrl("/i18n/fr/string.json")
				.setTargetClass(QueueViewWebStudent.class));
		
		ViewLoaders.registerViewLoader(TeacherQueueView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/queue/teacher/queue_teacher.html")
			     	.setCssUrl("/views/queue/teacher/queue_teacher.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(QueueViewWebTeacher.class));

		ViewLoaders.registerViewLoader(OpenQueueListView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/queues/queues.html")
			     	.setCssUrl("/views/queues/queues.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(QueuesViewWeb.class));

		ViewLoaders.registerViewLoader(OpenQueueView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/partials/queue_summary/queue_summary.html")
			     	.setCssUrl("/partials/queue_summary/queue_summary.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(QueueSummaryViewWeb.class));

		ViewLoaders.registerViewLoader(AppointmentViewTeacher.class,
				"fr"
				, Ntro.viewLoaderWeb()
				.setHtmlUrl("/partials/appointment/teacher/appointment_teacher.html")
				.setCssUrl("/partials/appointment/teacher/appointment_teacher.css")
				.setTranslationsUrl("/i18n/fr/string.json")
				.setTargetClass(AppointmentViewWebTeacher.class));
		
		ViewLoaders.registerViewLoader(AppointmentViewStudent.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/partials/appointment/student/appointment_student.html")
			     	.setCssUrl("/partials/appointment/student/appointment_student.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(AppointmentViewWebStudent.class));

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
			     	.setHtmlUrl("/views/commit_list/git_progression_commit_list.html")
			     	.setCssUrl("/views/commit_list/git_progression_commit_list.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(CommitListViewWeb.class));
		
		ViewLoaders.registerViewLoader(CommitView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/partials/commit/git_progression_commit.html")
			     	.setCssUrl("/partials/commit/git_progression_commit.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(CommitViewWeb.class));

		ViewLoaders.registerViewLoader(CourseViewTeacher.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/course/teacher/course_teacher.html")
			     	.setCssUrl("/views/course/teacher/course_teacher.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(CourseViewWebTeacher.class));

		ViewLoaders.registerViewLoader(CourseViewStudent.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/course/student/course_student.html")
			     	.setCssUrl("/views/course/student/course_student.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(CourseViewWebStudent.class));

		ViewLoaders.registerViewLoader(TaskViewTeacher.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/partials/task/teacher/task_teacher.html")
			     	.setCssUrl("/partials/task/teacher/task_teacher.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(TaskViewWebTeacher.class));


		ViewLoaders.registerViewLoader(TaskViewStudent.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/partials/task/student/task_student.html")
			     	.setCssUrl("/partials/task/student/task_student.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(TaskViewWebStudent.class));

		ViewLoaders.registerViewLoader(SemesterListView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/semester_list/semester_list.html")
			     	.setCssUrl("/views/semester_list/semester_list.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(SemesterListViewWeb.class));

		ViewLoaders.registerViewLoader(SemesterView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/partials/semester/semester.html")
			     	.setCssUrl("/partials/semester/semester.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(SemesterViewWeb.class));

		ViewLoaders.registerViewLoader(CourseListViewTeacher.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/course_list/teacher/course_list_teacher.html")
			     	.setCssUrl("/views/course_list/teacher/course_list_teacher.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(CourseListViewWebTeacher.class));

		ViewLoaders.registerViewLoader(CourseListViewStudent.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/course_list/student/course_list_student.html")
			     	.setCssUrl("/views/course_list/student/course_list_student.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(CourseListViewWebStudent.class));
		
		ViewLoaders.registerViewLoader(CourseItemViewTeacher.class,
				"fr"
				, Ntro.viewLoaderWeb()
				.setHtmlUrl("/partials/course_item/teacher/course_item_teacher.html")
		     	.setCssUrl("/partials/course_item/teacher/course_item_teacher.css")
				.setTranslationsUrl("/i18n/fr/string.json")
				.setTargetClass(CourseItemViewWebTeacher.class));

		ViewLoaders.registerViewLoader(CourseItemViewStudent.class,
				"fr"
				, Ntro.viewLoaderWeb()
				.setHtmlUrl("/partials/course_item/student/course_item_student.html")
		     	.setCssUrl("/partials/course_item/student/course_item_student.css")
				.setTranslationsUrl("/i18n/fr/string.json")
				.setTargetClass(CourseItemViewWebStudent.class));

		ViewLoaders.registerViewLoader(GroupListView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/group_list/group_list.html")
			     	.setCssUrl("/views/group_list/group_list.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(GroupListViewWeb.class));
		
		ViewLoaders.registerViewLoader(GroupView.class,
				"fr"
				, Ntro.viewLoaderWeb()
				.setHtmlUrl("/partials/group_item/group_item.html")
		     	.setCssUrl("/partials/group_item/group_item.css")
				.setTranslationsUrl("/i18n/fr/string.json")
				.setTargetClass(GroupViewWeb.class));
		
	}
}

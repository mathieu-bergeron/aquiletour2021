package ca.aquiletour.web;

import ca.aquiletour.core.pages.course.student.views.CourseViewStudent;
import ca.aquiletour.core.pages.course.student.views.TaskViewStudent;
import ca.aquiletour.core.pages.course.teacher.views.CourseViewTeacher;
import ca.aquiletour.core.pages.course.teacher.views.TaskViewTeacher;
import ca.aquiletour.core.pages.dashboard.student.views.DashboardCourseViewStudent;
import ca.aquiletour.core.pages.dashboard.student.views.DashboardViewStudent;
import ca.aquiletour.core.pages.dashboard.teacher.views.DashboardCourseViewTeacher;
import ca.aquiletour.core.pages.dashboard.teacher.views.DashboardViewTeacher;
import ca.aquiletour.core.pages.course_list.student.views.CourseListItemViewStudent;
import ca.aquiletour.core.pages.course_list.student.views.CourseListViewStudent;
import ca.aquiletour.core.pages.course_list.teacher.views.CourseListItemViewTeacher;
import ca.aquiletour.core.pages.course_list.teacher.views.CourseListViewTeacher;
import ca.aquiletour.core.pages.git.commit_list.views.CommitListView;
import ca.aquiletour.core.pages.git.commit_list.views.CommitView;
import ca.aquiletour.core.pages.git.late_students.LateStudentsView;
import ca.aquiletour.core.pages.git.student_summaries.StudentSummariesView;
import ca.aquiletour.core.pages.git.student_summaries.StudentSummaryView;
import ca.aquiletour.core.pages.group_list.views.GroupView;
import ca.aquiletour.core.pages.group_list.views.GroupListView;
import ca.aquiletour.core.pages.home.HomeView;
import ca.aquiletour.core.pages.login.LoginView;
import ca.aquiletour.core.pages.open_queue_list.OpenQueueListView;
import ca.aquiletour.core.pages.open_queue_list.OpenQueueView;
import ca.aquiletour.core.pages.queue.student.views.AppointmentViewStudent;
import ca.aquiletour.core.pages.queue.student.views.QueueViewStudent;
import ca.aquiletour.core.pages.queue.teacher.views.AppointmentViewTeacher;
import ca.aquiletour.core.pages.queue.teacher.views.QueueViewTeacher;
import ca.aquiletour.core.pages.root.RootView;
import ca.aquiletour.core.pages.semester_list.admin.views.SemesterListViewAdmin;
import ca.aquiletour.core.pages.semester_list.admin.views.SemesterViewAdmin;
import ca.aquiletour.core.pages.semester_list.teacher.views.SemesterListViewTeacher;
import ca.aquiletour.core.pages.semester_list.teacher.views.SemesterViewTeacher;
import ca.aquiletour.web.pages.course.student.CourseViewWebStudent;
import ca.aquiletour.web.pages.course.student.TaskViewWebStudent;
import ca.aquiletour.web.pages.course.teacher.CourseViewWebTeacher;
import ca.aquiletour.web.pages.course.teacher.TaskViewWebTeacher;
import ca.aquiletour.web.pages.course_list.student.CourseListItemViewWebTeacher;
import ca.aquiletour.web.pages.course_list.student.CourseListViewWebTeacher;
import ca.aquiletour.web.pages.course_list.teacher.CourseListItemViewWebStudent;
import ca.aquiletour.web.pages.course_list.teacher.CourseListViewWebStudent;
import ca.aquiletour.web.pages.dashboard.student.DashboardItemViewWebStudent;
import ca.aquiletour.web.pages.dashboard.student.DashboardViewWebStudent;
import ca.aquiletour.web.pages.dashboard.teacher.DashboardItemViewWebTeacher;
import ca.aquiletour.web.pages.dashboard.teacher.DashboardViewWebTeacher;
import ca.aquiletour.web.pages.git.commit_list.CommitListViewWeb;
import ca.aquiletour.web.pages.git.commit_list.CommitViewWeb;
import ca.aquiletour.web.pages.git.late_students.LateStudentsViewWeb;
import ca.aquiletour.web.pages.git.student_summaries.StudentSummariesViewWeb;
import ca.aquiletour.web.pages.git.student_summaries.StudentSummaryViewWeb;
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
import ca.aquiletour.web.pages.semester_list.SemesterListViewWebAdmin;
import ca.aquiletour.web.pages.semester_list.SemesterListViewWebTeacher;
import ca.aquiletour.web.pages.semester_list.SemesterViewWebAdmin;
import ca.aquiletour.web.pages.semester_list.SemesterViewWebTeacher;
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

		ViewLoaders.registerViewLoader(DashboardViewTeacher.class,
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

		ViewLoaders.registerViewLoader(QueueViewStudent.class,
				"fr"
				, Ntro.viewLoaderWeb()
				.setHtmlUrl("/views/queue/student/queue_student.html")
				.setCssUrl("/views/queue/student/queue_student.css")
				.setTranslationsUrl("/i18n/fr/string.json")
				.setTargetClass(QueueViewWebStudent.class));
		
		ViewLoaders.registerViewLoader(QueueViewTeacher.class,
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
			     	.setHtmlUrl("/views/git/commit_list/commit_list.html")
			     	.setCssUrl("/views/git/commit_list/commit_list.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(CommitListViewWeb.class));

		ViewLoaders.registerViewLoader(LateStudentsView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/git/late_students/late_students.html")
			     	.setCssUrl("/views/git/late_students/late_students.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(LateStudentsViewWeb.class));

		ViewLoaders.registerViewLoader(StudentSummariesView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/git/student_summaries/student_summaries.html")
			     	.setCssUrl("/views/git/student_summaries/student_summaries.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(StudentSummariesViewWeb.class));
		
		ViewLoaders.registerViewLoader(CommitView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/partials/git/commit/commit.html")
			     	.setCssUrl("/partials/git/commit/commit.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(CommitViewWeb.class));

		ViewLoaders.registerViewLoader(StudentSummaryView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/partials/git/student_summary/student_summary.html")
			     	.setCssUrl("/partials/git/student_summary/student_summary.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(StudentSummaryViewWeb.class));

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

		ViewLoaders.registerViewLoader(SemesterListViewAdmin.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/semester_list/admin/semester_list_admin.html")
			     	.setCssUrl("/views/semester_list/admin/semester_list_admin.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(SemesterListViewWebAdmin.class));

		ViewLoaders.registerViewLoader(SemesterListViewTeacher.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/semester_list/teacher/semester_list_teacher.html")
			     	.setCssUrl("/views/semester_list/teacher/semester_list_teacher.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(SemesterListViewWebTeacher.class));

		ViewLoaders.registerViewLoader(SemesterViewAdmin.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/partials/semester/admin/semester_admin.html")
			     	.setCssUrl("/partials/semester/admin/semester_admin.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(SemesterViewWebAdmin.class));

		ViewLoaders.registerViewLoader(SemesterViewTeacher.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/partials/semester/teacher/semester_teacher.html")
			     	.setCssUrl("/partials/semester/teacher/semester_teacher.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(SemesterViewWebTeacher.class));

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
		
		ViewLoaders.registerViewLoader(CourseListItemViewTeacher.class,
				"fr"
				, Ntro.viewLoaderWeb()
				.setHtmlUrl("/partials/course_item/teacher/course_item_teacher.html")
		     	.setCssUrl("/partials/course_item/teacher/course_item_teacher.css")
				.setTranslationsUrl("/i18n/fr/string.json")
				.setTargetClass(CourseListItemViewWebTeacher.class));

		ViewLoaders.registerViewLoader(CourseListItemViewStudent.class,
				"fr"
				, Ntro.viewLoaderWeb()
				.setHtmlUrl("/partials/course_item/student/course_item_student.html")
		     	.setCssUrl("/partials/course_item/student/course_item_student.css")
				.setTranslationsUrl("/i18n/fr/string.json")
				.setTargetClass(CourseListItemViewWebStudent.class));

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

package ca.aquiletour.core.pages.root;

import java.util.List;

import ca.aquiletour.core.pages.course.views.CourseView;
import ca.aquiletour.core.pages.course_list.views.CourseListView;
import ca.aquiletour.core.pages.dashboard.views.DashboardView;
import ca.aquiletour.core.pages.git.CommitListView;
import ca.aquiletour.core.pages.group_list.views.GroupListView;
import ca.aquiletour.core.pages.dashboards.DashboardView;
import ca.aquiletour.core.pages.git.commit_list.CommitListView;
import ca.aquiletour.core.pages.git.late_students.LateStudentsView;
import ca.aquiletour.core.pages.git.student_summaries.StudentSummariesView;
import ca.aquiletour.core.pages.home.HomeView;
import ca.aquiletour.core.pages.login.LoginView;
import ca.aquiletour.core.pages.open_queue_list.OpenQueueListView;
import ca.aquiletour.core.pages.queue.views.QueueView;
import ca.aquiletour.core.pages.semester_list.views.SemesterListView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroView;
import ca.ntro.messages.NtroMessage;

public interface RootView extends NtroView {
	
	void showDashboard(DashboardView dashboardView);
	void showQueue(QueueView queueView);
	void showLogin(LoginView loginView);
	void showQueues(OpenQueueListView currentView);
	void showHome(HomeView homeView);
	void showGit(CommitListView gitView);
	void showGitCommitList(CommitListView gitCommitListView);
	void showGitLateStudents(LateStudentsView gitLateStudentsView);
	void showGitStudentSummaries(StudentSummariesView gitStudentSummariesView);
	void showCourse(CourseView currentView);
	void showCalendarList(SemesterListView currentView);
	void showCourseList(CourseListView currentView);
	void showGroupList(GroupListView currentView);

	void displayErrorMessage(String message);
	void displayPrimaryMessage(String message);
	void onContextChange(NtroContext<?,?> context);
	void displayUserScreenName(String screenName);
	void showLoginMenu(String messageToUser, List<NtroMessage> delayedMessages);
}

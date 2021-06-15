package ca.aquiletour.core.pages.root;

import java.util.List;

import ca.aquiletour.core.pages.course.views.CourseView;
import ca.aquiletour.core.pages.course_list.views.CourseListView;
import ca.aquiletour.core.pages.dashboard.views.DashboardView;
import ca.aquiletour.core.pages.group_list.views.GroupListView;
import ca.aquiletour.core.pages.git.commit_list.views.CommitListView;
import ca.aquiletour.core.pages.git.late_students.LateStudentsView;
import ca.aquiletour.core.pages.git.student_summaries.StudentSummariesView;
import ca.aquiletour.core.pages.home.HomeView;
import ca.aquiletour.core.pages.login.LoginView;
import ca.aquiletour.core.pages.queue.views.QueueView;
import ca.aquiletour.core.pages.queue_list.views.QueueListView;
import ca.aquiletour.core.pages.semester_list.views.SemesterListView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroView;
import ca.ntro.messages.NtroMessage;

public interface RootView extends NtroView {
	
	void showDashboard(Class<? extends NtroView> subViewClass, DashboardView dashboardView);
	void showQueue(Class<? extends NtroView> subViewClass, QueueView queueView);
	void showLogin(Class<? extends NtroView> subViewClass, LoginView loginView);
	void showQueues(Class<? extends NtroView> subViewClass, QueueListView currentView);
	void showHome(Class<? extends NtroView> subViewClass, HomeView homeView);
	void showGitCommitList(Class<? extends NtroView> subViewClass, CommitListView gitCommitListView);
	void showGitLateStudents(Class<? extends NtroView> subViewClass, LateStudentsView gitLateStudentsView);
	void showGitStudentSummaries(Class<? extends NtroView> subViewClass, StudentSummariesView gitStudentSummariesView);
	void showCourse(Class<? extends NtroView> subViewClass, CourseView currentView);
	void showSemesterList(Class<? extends NtroView> subViewClass, SemesterListView currentView);
	void showCourseList(Class<? extends NtroView> subViewClass, CourseListView currentView);
	void showGroupList(Class<? extends NtroView> subViewClass, GroupListView currentView);

	void displayErrorMessage(String message);
	void displayPrimaryMessage(String message);
	void onContextChange(NtroContext<?,?> context);
	void displayUserScreenName(String screenName);
	void showLoginMenu(String messageToUser, List<NtroMessage> delayedMessages);
	void showPasswordMenu();
}

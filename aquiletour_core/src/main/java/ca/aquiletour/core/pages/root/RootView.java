package ca.aquiletour.core.pages.root;

import ca.aquiletour.core.pages.course.views.CourseView;
import ca.aquiletour.core.pages.course_list.views.CourseListView;
import ca.aquiletour.core.pages.dashboard.views.DashboardView;
import ca.aquiletour.core.pages.git.CommitListView;
import ca.aquiletour.core.pages.group_list.views.GroupListView;
import ca.aquiletour.core.pages.home.HomeView;
import ca.aquiletour.core.pages.login.LoginView;
import ca.aquiletour.core.pages.open_queue_list.OpenQueueListView;
import ca.aquiletour.core.pages.queue.views.QueueView;
import ca.aquiletour.core.pages.semester_list.views.SemesterListView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroView;

public interface RootView extends NtroView {
	
	void showDashboard(DashboardView dashboardView);
	void showQueue(QueueView queueView);
	void showLogin(LoginView loginView);
	void showQueues(OpenQueueListView currentView);
	void showHome(HomeView homeView);

	void onContextChange(NtroContext<?,?> context);

	void showGit(CommitListView gitView);
	void showCourse(CourseView currentView);
	void showCalendarList(SemesterListView currentView);
	void showCourseList(CourseListView currentView);
	void displayErrorMessage(String message);
	void displayPrimaryMessage(String message);
	void showGroupList(GroupListView currentView);

	void displayUserScreenName(String screenName);
}

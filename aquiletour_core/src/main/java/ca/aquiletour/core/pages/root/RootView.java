package ca.aquiletour.core.pages.root;

import ca.aquiletour.core.pages.admin.calendar_list.views.CalendarListView;
import ca.aquiletour.core.pages.course.views.CourseView;
import ca.aquiletour.core.pages.dashboards.DashboardView;
import ca.aquiletour.core.pages.git.CommitListView;
import ca.aquiletour.core.pages.home.HomeView;
import ca.aquiletour.core.pages.login.LoginView;
import ca.aquiletour.core.pages.open_queue_list.OpenQueueListView;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroView;

public interface RootView extends NtroView {
	
	void showDashboard(DashboardView dashboardView);
	void showQueue(QueueView queueView);
	void showLogin(LoginView loginView);
	void showQueues(OpenQueueListView currentView);
	void showHome(HomeView homeView);
	void adjustLoginLinkText(NtroContext<?> context);
	void showGit(CommitListView gitView);
	void showCourse(CourseView currentView);
	void showCalendarList(CalendarListView currentView);
}

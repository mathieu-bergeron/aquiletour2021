package ca.aquiletour.core.pages.root;

import ca.aquiletour.core.pages.dashboards.DashboardView;
import ca.aquiletour.core.pages.git.CommitListView;
import ca.aquiletour.core.pages.home.HomeView;
import ca.aquiletour.core.pages.login.LoginView;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.queues.QueuesView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroView;

public interface RootView extends NtroView {
	
	void showDashboard(DashboardView dashboardView);
	void showQueue(QueueView queueView);
	void showLogin(LoginView loginView);
	void showQueues(QueuesView currentView);
	void showHome(HomeView homeView);
	void adjustLoginLinkText(NtroContext<?> context);
	void showGit(CommitListView gitView);
}

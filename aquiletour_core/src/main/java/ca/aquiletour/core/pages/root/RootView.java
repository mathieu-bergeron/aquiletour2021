package ca.aquiletour.core.pages.root;

import ca.aquiletour.core.pages.dashboard.DashboardView;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.ntro.core.mvc.NtroView;

public interface RootView extends NtroView {
	
	void showDashboard(DashboardView dashboardView);
	void showQueue(QueueView queueView);

}

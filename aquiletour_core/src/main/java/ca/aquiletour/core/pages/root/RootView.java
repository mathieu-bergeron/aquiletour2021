package ca.aquiletour.core.pages.root;

import ca.aquiletour.core.pages.dashboard.DashboardView;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.settings.SettingsView;
import ca.ntro.core.mvc.NtroView;

public interface RootView extends NtroView {
	
	void showSettings(SettingsView settingsView);
	void showDashboard(DashboardView dashboardView);
	void showQueue(QueueView queueView);

}

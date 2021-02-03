package ca.aquiletour.server.pages.root;

import ca.aquiletour.core.pages.queue.QueueController;
import ca.aquiletour.server.NtroWindowServer;
import ca.aquiletour.server.pages.dashboard.DashboardControllerServer;
import ca.aquiletour.server.pages.queue.QueueControllerServer;
import ca.aquiletour.server.pages.settings.SettingsControllerServer;
import ca.aquiletour.web.pages.dashboard.DashboardControllerWeb;
import ca.aquiletour.web.pages.queue.QueueControllerWeb;
import ca.aquiletour.web.pages.root.RootControllerWeb;
import ca.aquiletour.web.pages.settings.SettingsControllerWeb;
import ca.ntro.web.NtroWindowWeb;

public class RootControllerServer extends RootControllerWeb {
	
	private NtroWindowServer window = new NtroWindowServer();

	@Override
	protected NtroWindowWeb getWindow() {
		return window;
	}

	@Override
	public SettingsControllerWeb createSettingsController() {
		return new SettingsControllerServer(this);
	}

	@Override
	public DashboardControllerWeb createDashboardController() {
		return new DashboardControllerServer(this);
	}

	@Override
	public QueueControllerWeb createQueueController() {
		// TODO Auto-generated method stub
		return new QueueControllerServer(this);
	}
}

package ca.aquiletour.server.pages.root;

import ca.aquiletour.core.pages.root.ShowDashboardTask;
import ca.aquiletour.core.pages.root.ShowSettingsTask;
import ca.aquiletour.server.NtroWindowServer;
import ca.aquiletour.server.pages.dashboard.DashboardControllerServer;
import ca.aquiletour.server.pages.settings.SettingsControllerServer;
import ca.aquiletour.web.pages.dashboard.DashboardControllerWeb;
import ca.aquiletour.web.pages.rootpage.RootControllerWeb;
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
		return new SettingsControllerServer();
	}

	@Override
	public DashboardControllerWeb createDashboardController() {
		return new DashboardControllerServer();
	}
}

package ca.aquiletour.server.pages.root;

import ca.aquiletour.core.pages.dashboard.DashboardController;
import ca.aquiletour.core.pages.settings.SettingsController;
import ca.aquiletour.server.NtroWindowServer;
import ca.aquiletour.server.pages.dashboard.DashboardControllerServer;
import ca.aquiletour.server.pages.settings.SettingsControllerServer;
import ca.aquiletour.web.pages.rootpage.RootControllerWeb;
import ca.ntro.web.NtroWindowWeb;

public class RootControllerServer extends RootControllerWeb {
	
	private NtroWindowServer window = new NtroWindowServer();

	@Override
	protected NtroWindowWeb getWindow() {
		return window;
	}

	@Override
	public SettingsController createSettingsController() {
		return new SettingsControllerServer();
	}

	@Override
	public DashboardController createDashboardController() {
		return new DashboardControllerServer();
	}

}

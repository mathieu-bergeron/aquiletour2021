package ca.aquiletour.jsweet.pages.root;

import ca.aquiletour.jsweet.NtroWindowJSweet;
import ca.aquiletour.jsweet.pages.dashboard.DashboardControllerJSweet;
import ca.aquiletour.jsweet.pages.settings.SettingsControllerJSweet;
import ca.aquiletour.web.pages.dashboard.DashboardControllerWeb;
import ca.aquiletour.web.pages.root.RootControllerWeb;
import ca.aquiletour.web.pages.settings.SettingsControllerWeb;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.NtroWindowWeb;

public class RootControllerJSweet extends RootControllerWeb {
	
	private NtroWindowJSweet window = new NtroWindowJSweet();

	@Override
	protected NtroWindowWeb getWindow() {
		return window;
	}

	@Override
	public SettingsControllerWeb createSettingsController() {
		T.call(this);
		
		return new SettingsControllerJSweet(this);
	}

	@Override
	public DashboardControllerWeb createDashboardController() {
		return new DashboardControllerJSweet(this);
	}
}
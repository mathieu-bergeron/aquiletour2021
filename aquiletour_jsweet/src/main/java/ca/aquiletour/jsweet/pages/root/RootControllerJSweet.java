package ca.aquiletour.jsweet.pages.root;

import ca.aquiletour.core.pages.root.ShowDashboardTask;
import ca.aquiletour.core.pages.root.ShowSettingsTask;
import ca.aquiletour.jsweet.NtroWindowJSweet;
import ca.aquiletour.web.pages.dashboard.DashboardControllerWeb;
import ca.aquiletour.web.pages.rootpage.RootControllerWeb;
import ca.aquiletour.web.pages.settings.SettingsControllerWeb;
import ca.ntro.web.NtroWindowWeb;

public class RootControllerJSweet extends RootControllerWeb {
	
	private NtroWindowJSweet window = new NtroWindowJSweet();

	@Override
	protected NtroWindowWeb getWindow() {
		return window;
	}

	@Override
	public SettingsControllerWeb createSettingsController() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DashboardControllerWeb createDashboardController() {
		// TODO Auto-generated method stub
		return null;
	}
}

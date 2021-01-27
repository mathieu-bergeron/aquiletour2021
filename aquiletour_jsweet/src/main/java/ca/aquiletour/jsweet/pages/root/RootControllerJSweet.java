package ca.aquiletour.jsweet.pages.root;

import ca.aquiletour.core.pages.dashboard.DashboardController;
import ca.aquiletour.core.pages.settings.SettingsController;
import ca.aquiletour.jsweet.NtroWindowJSweet;
import ca.aquiletour.web.pages.rootpage.RootControllerWeb;
import ca.ntro.web.NtroWindowWeb;

public class RootControllerJSweet extends RootControllerWeb {
	
	private NtroWindowJSweet window = new NtroWindowJSweet();

	@Override
	protected NtroWindowWeb getWindow() {
		return window;
	}

	@Override
	public SettingsController createSettingsController() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DashboardController createDashboardController() {
		// TODO Auto-generated method stub
		return null;
	}

}

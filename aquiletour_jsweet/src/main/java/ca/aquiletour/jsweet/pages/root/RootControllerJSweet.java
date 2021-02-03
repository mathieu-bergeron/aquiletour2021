package ca.aquiletour.jsweet.pages.root;

import ca.aquiletour.core.pages.queue.QueueController;
import ca.aquiletour.jsweet.NtroWindowJSweet;
import ca.aquiletour.jsweet.pages.dashboard.DashboardControllerJSweet;
import ca.aquiletour.jsweet.pages.queue.QueueControllerJSweet;
import ca.aquiletour.jsweet.pages.settings.SettingsControllerJSweet;
import ca.aquiletour.web.pages.dashboard.DashboardControllerWeb;
import ca.aquiletour.web.pages.queue.QueueControllerWeb;
import ca.aquiletour.web.pages.root.RootControllerWeb;
import ca.aquiletour.web.pages.settings.SettingsControllerWeb;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.NtroWindowWeb;

public class RootControllerJSweet extends RootControllerWeb {
	
	private NtroWindowJSweet window = new NtroWindowJSweet();

	@Override
	protected NtroWindowWeb getWindow() {
		T.call(this);
		
		MustNot.beNull(window);

		return window;
	}

	@Override
	public SettingsControllerWeb createSettingsController() {
		T.call(this);
		
		return new SettingsControllerJSweet(this);
	}

	@Override
	public DashboardControllerWeb createDashboardController() {
		T.call(this);
		return new DashboardControllerJSweet(this);
	}

	@Override
	public QueueControllerWeb createQueueController() {
		T.call(this);
		// TODO Auto-generated method stub
		return new QueueControllerJSweet(this);
	}
}

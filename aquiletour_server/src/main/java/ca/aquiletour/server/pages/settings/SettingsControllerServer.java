package ca.aquiletour.server.pages.settings;


import ca.aquiletour.core.pages.root.RootController;
import ca.aquiletour.web.pages.settings.SettingsControllerWeb;
import ca.ntro.core.system.trace.T;

public class SettingsControllerServer extends SettingsControllerWeb {

	public SettingsControllerServer(RootController parentController) {
		super(parentController);
		T.call(this);
	}

}

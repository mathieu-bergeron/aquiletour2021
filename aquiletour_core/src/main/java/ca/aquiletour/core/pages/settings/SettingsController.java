package ca.aquiletour.core.pages.settings;

import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.mvc.ViewLoaders;
import ca.ntro.core.system.trace.T;

public class SettingsController extends NtroController<RootController> {

	@Override
	protected void initialize() {
		T.call(this);

		setViewLoader(ViewLoaders.getViewLoader(SettingsView.class, "fr"));

		addParentViewMessageHandler(ShowSettingsMessage.class, new ShowSettingsHandler());
	}

}

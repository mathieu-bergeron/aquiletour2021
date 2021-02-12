package ca.aquiletour.core.pages.settings;

import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public class SettingsController extends NtroController<RootController> {

	@Override
	protected void onCreate() {
		T.call(this);

		setViewLoader(SettingsView.class, currentContext().getLang());

		addParentViewMessageHandler(ShowSettingsMessage.class, new ShowSettingsHandler());
	}

	@Override
	protected void onChangeContext(NtroContext previousContext) {
		T.call(this);

	}

	@Override
	protected void onFailure(Exception e) {
		T.call(this);
		
	}


}

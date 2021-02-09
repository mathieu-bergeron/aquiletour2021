package ca.aquiletour.core.pages.settings;

import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public class SettingsController extends NtroController<RootController> {

	@Override
	protected void onCreate(NtroContext context) {
		T.call(this);

		setViewLoader(SettingsView.class, context.getLang());

		addParentViewMessageHandler(ShowSettingsMessage.class, new ShowSettingsHandler());
	}

	@Override
	protected void onChangeContext(NtroContext previousContext, NtroContext context) {
		T.call(this);

	}

	@Override
	protected void onFailure(Exception e) {
		T.call(this);
		
	}


}

package ca.aquiletour.core.pages.root;

import ca.aquiletour.core.pages.settings.SettingsView;
import ca.ntro.core.Ntro;
import ca.ntro.core.mvc.view.ViewReceptor;
import ca.ntro.core.system.trace.T;

public class RootViewReceptor extends ViewReceptor<RootView> {
	
	private RootView view;

	@Override
	public void onViewLoaded(RootView view) {
		T.call(this);
		
		this.view = view;

		Ntro.window().installRootView(view);
	}

	public void displaySettings(SettingsView settingsView) {
		T.call(this);
		
		view.installSubView(settingsView);
	}
}

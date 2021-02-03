package ca.aquiletour.core.pages.settings;

import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ParentViewMessageHandler;
import ca.ntro.core.system.trace.T;

public class ShowSettingsHandler extends ParentViewMessageHandler<RootView,
                                                                  SettingsView,
                                                                  ShowSettingsMessage> {

	@Override
	protected void handle(RootView parentView, 
			              SettingsView currentView, 
			              ShowSettingsMessage message) {
		T.call(this);
		
		parentView.showSettings(currentView);
	}
}

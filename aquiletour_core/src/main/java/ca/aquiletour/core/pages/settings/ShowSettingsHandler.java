package ca.aquiletour.core.pages.settings;

import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ViewMessageHandler;
import ca.ntro.core.system.trace.T;

public class ShowSettingsHandler extends ViewMessageHandler<SettingsController,
                                                            SettingsView,
                                                            ShowSettingsMessage> {

	@Override
	protected void handle(SettingsController controller, 
			              SettingsView view, 
			              ShowSettingsMessage message) {
		T.call(this);
		
		// XXX: the parentController is always fully loaded, hence we can 
		//      simply call getView(), which we CANNOT call on current controller
		RootView rootView = (RootView) controller.getParentController().getView();
		rootView.installSubView(view);
	}
	

}

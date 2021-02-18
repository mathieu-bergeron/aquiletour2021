package ca.aquiletour.core.pages.login;

import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ParentViewMessageHandler;
import ca.ntro.core.system.trace.T;

public class ShowLoginHandler extends ParentViewMessageHandler<RootView,
                                                                  LoginView,
                                                                  ShowLoginMessage> {

	@Override
	protected void handle(RootView parentView, 
			              LoginView currentView, 
			              ShowLoginMessage message) {
		T.call(this);
		
		// FIXME: parentView needs to be the one installed in
		//        the parent controller. We cannot do viewLoader.createView()

		parentView.showLogin(currentView);
	}
}

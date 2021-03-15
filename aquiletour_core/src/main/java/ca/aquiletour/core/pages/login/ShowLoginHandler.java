package ca.aquiletour.core.pages.login;

import ca.aquiletour.core.pages.root.AiguilleurRootView;
import ca.ntro.core.mvc.ParentViewMessageHandler;
import ca.ntro.core.system.trace.T;

public class ShowLoginHandler extends ParentViewMessageHandler<AiguilleurRootView,
                                                                  LoginView,
                                                                  ShowLoginMessage> {

	@Override
	protected void handle(AiguilleurRootView parentView, 
			              LoginView currentView, 
			              ShowLoginMessage message) {
		T.call(this);
		
		
		// FIXME: parentView needs to be the one installed in
		//        the parent controller. We cannot do viewLoader.createView()

		parentView.showLogin(currentView);
	}
}

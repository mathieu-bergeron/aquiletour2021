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
		
		String messageToUser = message.getMessageToUser();

		if(messageToUser != null) {

			currentView.displayLoginMessage(messageToUser);

		}else {

			currentView.hideLoginMessage();

		}
		
		parentView.showLogin(currentView);
	}
}

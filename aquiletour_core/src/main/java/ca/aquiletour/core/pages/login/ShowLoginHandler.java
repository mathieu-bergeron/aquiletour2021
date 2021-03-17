package ca.aquiletour.core.pages.login;

import ca.aquiletour.core.models.users.StudentGuest;
import ca.aquiletour.core.models.users.TeacherGuest;
import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ParentViewMessageHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

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
		}
		
		if(Ntro.userService().currentUser() instanceof TeacherGuest || Ntro.userService().currentUser() instanceof StudentGuest) {
			
			currentView.displayStep2();

		} else {

			currentView.displayStep1();
		}

		parentView.showLogin(currentView);
	}
}

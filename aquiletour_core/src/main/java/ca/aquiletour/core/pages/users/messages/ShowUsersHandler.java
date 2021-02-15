package ca.aquiletour.core.pages.users.messages;

import ca.aquiletour.core.pages.root.RootView;
import ca.aquiletour.core.pages.users.UsersView;
import ca.ntro.core.mvc.ParentViewMessageHandler;
import ca.ntro.core.system.trace.T;

public class ShowUsersHandler extends ParentViewMessageHandler<RootView, UsersView, ShowUsersMessage> {
	
	@Override
	protected void handle(RootView parentView, UsersView currentView, ShowUsersMessage message) {
		T.call(this);
		
		parentView.showUsers(currentView);
		
	}

}

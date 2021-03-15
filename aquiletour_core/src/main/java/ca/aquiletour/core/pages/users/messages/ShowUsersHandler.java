package ca.aquiletour.core.pages.users.messages;

import ca.aquiletour.core.pages.root.AiguilleurRootView;
import ca.aquiletour.core.pages.users.UsersView;
import ca.ntro.core.mvc.ParentViewMessageHandler;
import ca.ntro.core.system.trace.T;

public class ShowUsersHandler extends ParentViewMessageHandler<AiguilleurRootView, UsersView, ShowUsersMessage> {
	
	@Override
	protected void handle(AiguilleurRootView parentView, UsersView currentView, ShowUsersMessage message) {
		T.call(this);

		parentView.showUsers(currentView);
		
	}

}

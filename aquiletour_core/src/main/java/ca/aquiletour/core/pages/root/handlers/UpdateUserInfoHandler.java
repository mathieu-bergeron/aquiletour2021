package ca.aquiletour.core.pages.root.handlers;

import ca.aquiletour.core.messages.user.UpdateUserInfoMessage;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ViewMessageHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.users.NtroSession;

public class UpdateUserInfoHandler extends ViewMessageHandler<RootView, UpdateUserInfoMessage> {

	@Override
	protected void handle(RootView view, UpdateUserInfoMessage message) {
		T.call(this);
		
		view.displayUserScreenName(message.getScreenName());

		NtroSession session = Ntro.currentSession();
		User user = (User) session.getUser();
		user.setFirstname(message.getScreenName());
		user.setLastname("");

		// XXX: saves session back to cookie
		Ntro.sessionService().registerCurrentSession(session);
		
		Ntro.backendService().sendMessageToBackend(message);
	}

}

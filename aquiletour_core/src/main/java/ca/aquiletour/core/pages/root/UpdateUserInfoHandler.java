package ca.aquiletour.core.pages.root;

import ca.aquiletour.core.messages.user.UpdateUserInfoMessage;
import ca.ntro.core.mvc.ViewMessageHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class UpdateUserInfoHandler extends ViewMessageHandler<RootView, UpdateUserInfoMessage> {

	@Override
	protected void handle(RootView view, UpdateUserInfoMessage message) {
		T.call(this);
		
		view.displayUserScreenName(message.getScreenName());
		
		Ntro.backendService().sendMessageToBackend(message);
	}

}

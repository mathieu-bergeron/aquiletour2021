package ca.aquiletour.server.backend.login;

import ca.aquiletour.core.messages.UserLogsOutMessage;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class UserLogsOutHandler extends BackendMessageHandler<UserLogsOutMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, UserLogsOutMessage message) {
		T.call(this);
		
		Ntro.userService().registerCurrentUser(AuthenticateSessionUserHandler.createGuestSession(modelStore));
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, UserLogsOutMessage message) {
		T.call(this);
	}
}

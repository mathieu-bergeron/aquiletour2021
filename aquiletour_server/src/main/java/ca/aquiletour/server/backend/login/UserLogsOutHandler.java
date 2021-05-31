package ca.aquiletour.server.backend.login;

import ca.aquiletour.core.messages.user.UserLogsOutMessage;
import ca.aquiletour.server.backend.users.UserManager;
import ca.ntro.backend.BackendError;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class UserLogsOutHandler extends BackendMessageHandler<UserLogsOutMessage> {
	
	@Override
	public void handleNow(ModelStoreSync modelStore, UserLogsOutMessage message) throws BackendError {
		T.call(this);
		
		UserManager.resetUserAfterLogout(modelStore, message.getUser());

		Ntro.currentSession().setUser(SessionManager.createGuestSession(modelStore));
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, UserLogsOutMessage message) throws BackendError {
		T.call(this);

		SessionManager.deleteSession(modelStore, message.getAuthToken(), message.getUser());
	}
}

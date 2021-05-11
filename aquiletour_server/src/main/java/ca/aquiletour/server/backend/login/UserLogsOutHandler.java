package ca.aquiletour.server.backend.login;

import ca.aquiletour.core.messages.user.UserLogsOutMessage;
import ca.aquiletour.server.backend.users.UserManager;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class UserLogsOutHandler extends BackendMessageHandler<UserLogsOutMessage> {
	
	private String authToken;

	@Override
	public void handleNow(ModelStoreSync modelStore, UserLogsOutMessage message) {
		T.call(this);
		
		authToken = Ntro.currentUser().getAuthToken();
		
		UserManager.resetUserAfterLogout(modelStore, message.getUser());

		Ntro.currentSession().setUser(InitializeSessionHandler.createGuestSession(modelStore));
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, UserLogsOutMessage message) {
		T.call(this);

		SessionManager.deleteSession(modelStore, authToken);
	}
}

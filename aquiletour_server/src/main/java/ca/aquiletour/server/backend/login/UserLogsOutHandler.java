package ca.aquiletour.server.backend.login;

import ca.aquiletour.core.messages.user.UserLogsOutMessage;
import ca.aquiletour.server.backend.users.UserManager;
import ca.aquiletour.server.registered_sockets.RegisteredSockets;
import ca.ntro.backend.BackendError;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.ntro_messages.NtroPleaseReconnectSocketMessage;
import ca.ntro.messages.ntro_messages.NtroUpdateSessionMessage;
import ca.ntro.services.ModelStoreSync;
import ca.ntro.services.Ntro;

public class UserLogsOutHandler extends BackendMessageHandler<UserLogsOutMessage> {
	
	@Override
	public void handleNow(ModelStoreSync modelStore, UserLogsOutMessage message) throws BackendError {
		T.call(this);
		
		String authToken = message.getUser().getAuthToken();
		
		UserManager.resetUserAfterLogout(modelStore, message.getUser());

		Ntro.currentSession().setUser(SessionManager.createGuestSession(modelStore));
		
		NtroUpdateSessionMessage updateSessionMessage = Ntro.messages().create(NtroUpdateSessionMessage.class);
		updateSessionMessage.setSession(Ntro.currentSession());
		RegisteredSockets.sendMessageToSocket(authToken, updateSessionMessage);

		NtroPleaseReconnectSocketMessage pleaseReconnectSocketMessage = Ntro.messages().create(NtroPleaseReconnectSocketMessage.class);
		RegisteredSockets.sendMessageToSocket(authToken, pleaseReconnectSocketMessage);
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, UserLogsOutMessage message) throws BackendError {
		T.call(this);

		SessionManager.deleteSession(modelStore, message.getAuthToken(), message.getUser());
	}
}

package ca.aquiletour.server.backend.login;


import ca.aquiletour.core.messages.user.UserSendsLoginCodeMessage;
import ca.aquiletour.core.models.session.SessionData;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.server.registered_sockets.RegisteredSockets;
import ca.ntro.backend.BackendError;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;
import ca.ntro.messages.ntro_messages.NtroUpdateSessionMessage;
import ca.ntro.services.ModelStoreSync;
import ca.ntro.services.Ntro;
import ca.ntro.users.NtroSession;

public class UserSendsLoginCodeHandler extends BackendMessageHandler<UserSendsLoginCodeMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, UserSendsLoginCodeMessage message) throws BackendError {
		T.call(this);

		String loginCode = message.getLoginCode().replace(" ", "");
		String authToken = message.getUser().getAuthToken();
		String userId = message.getUser().getId();

		User userToRegister = null;

		if(SessionManager.ifLoginCodeValid(modelStore, authToken, loginCode)) {
			
			userToRegister = SessionManager.createAuthenticatedUser(modelStore, authToken,  userId, message.getUser());
			
		}else {
			
			userToRegister = (User) message.getUser();
		}

		Ntro.currentSession().setUser(userToRegister);

		NtroUpdateSessionMessage updateSessionMessage = Ntro.messages().create(NtroUpdateSessionMessage.class);
		updateSessionMessage.setSession(Ntro.currentSession());
		RegisteredSockets.sendMessageToUser(userToRegister, updateSessionMessage);
		
		for(NtroMessage delayedMessage : message.getDelayedMessages()) {
			Ntro.messages().send(delayedMessage);
		}
	}


	@Override
	public void handleLater(ModelStoreSync modelStore, UserSendsLoginCodeMessage message) {
		T.call(this);
	}

}

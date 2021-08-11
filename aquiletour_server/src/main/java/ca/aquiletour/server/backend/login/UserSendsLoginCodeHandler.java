package ca.aquiletour.server.backend.login;


import ca.aquiletour.core.messages.user.UserSendsLoginCodeMessage;
import ca.aquiletour.core.models.user.StudentGuest;
import ca.aquiletour.core.utils.TextProcessing;
import ca.aquiletour.server.backend.users.UserManager;
import ca.aquiletour.server.registered_sockets.RegisteredSocketsSockJS;
import ca.ntro.backend.BackendError;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;
import ca.ntro.messages.ntro_messages.NtroUpdateSessionMessage;
import ca.ntro.services.ModelStoreSync;
import ca.ntro.services.Ntro;

public class UserSendsLoginCodeHandler extends BackendMessageHandler<UserSendsLoginCodeMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, UserSendsLoginCodeMessage message) throws BackendError {
		T.call(this);

		String loginCode = message.getLoginCode().replace(" ", "");
		String authToken = message.getUser().getAuthToken();
		String userId = message.getUser().getId();
		String firstName = message.getFirstName();
		String lastName = message.getLastName();
		
		if(message.getUser().isStudent()) {
			
				if(!TextProcessing.isValidName(firstName)) {
					throw new BackendError("SVP entrer votre prénom et nom complet.");
				}

				else if(!TextProcessing.isValidName(lastName)) {
					throw new BackendError("SVP entrer votre prénom et nom complet.");
				}
		}

		if(SessionManager.ifLoginCodeValid(modelStore, authToken, loginCode)) {

			SessionManager.createAuthenticatedUser(modelStore, authToken,  userId, message.getUser());
			
			UserManager.updateUserName(modelStore, firstName, lastName, userId);
			
			SessionManager.updateUser(modelStore, userId);
			
			NtroUpdateSessionMessage updateSessionMessage = Ntro.messages().create(NtroUpdateSessionMessage.class);
			updateSessionMessage.setSession(Ntro.currentSession());
			RegisteredSocketsSockJS.sendMessageToSockets(authToken, updateSessionMessage);

		}

		for(NtroMessage delayedMessage : message.getDelayedMessages()) {
			Ntro.messages().send(delayedMessage);
		}
	}


	@Override
	public void handleLater(ModelStoreSync modelStore, UserSendsLoginCodeMessage message) {
		T.call(this);
	}
}

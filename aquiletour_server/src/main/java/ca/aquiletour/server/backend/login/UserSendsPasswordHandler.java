package ca.aquiletour.server.backend.login;

import ca.aquiletour.core.messages.user.UserSendsPasswordMessage;
import ca.aquiletour.core.models.session.SessionData;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.root.messages.ShowLoginMenuMessage;
import ca.aquiletour.server.backend.users.UserManager;
import ca.aquiletour.server.registered_sockets.RegisteredSockets;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendError;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;
import ca.ntro.messages.ntro_messages.NtroUpdateSessionMessage;
import ca.ntro.services.ModelStoreSync;
import ca.ntro.services.Ntro;

public class UserSendsPasswordHandler extends BackendMessageHandler<UserSendsPasswordMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, UserSendsPasswordMessage message) throws BackendError {
		T.call(this);

		String authToken = message.getUser().getAuthToken();
		String userId = message.getUser().getId();

		if(UserManager.isUserPasswordValid(modelStore, message.getPassword(), message.getUser())) {

			SessionManager.createAuthenticatedUser(modelStore, authToken,  userId, message.getUser());

			NtroUpdateSessionMessage updateSessionMessage = Ntro.messages().create(NtroUpdateSessionMessage.class);
			updateSessionMessage.setSession(Ntro.currentSession());
			RegisteredSockets.sendMessageToUser(Ntro.currentUser(), updateSessionMessage);

			for(NtroMessage delayedMessage : message.getDelayedMessages()) {
				Ntro.messages().send(delayedMessage);
			}
			
		}else {

			ShowLoginMenuMessage showLoginMenuMessage = Ntro.messages().create(ShowLoginMenuMessage.class);
			showLoginMenuMessage.setDelayedMessages(message.getDelayedMessages());

			SessionManager.updateSession(modelStore, authToken, session -> {
				SessionData sessionData = null;
				if(session.getSessionData() instanceof SessionData) {
					sessionData = (SessionData) session.getSessionData();
				}
				
				if(sessionData != null) {
					sessionData.incrementFailedPasswordAttemps();

					if(sessionData.hasReachedMaxPasswordAttemps()) {
						User user = (User) Ntro.currentSession().getUser();
						user.setHasPassword(false);
						showLoginMenuMessage.setMessageToUser("SVP re-valider votre courriel.");
						
						String loginCode = UserInitiatesLoginHandler.sendLoginCode(user);
						sessionData.setLoginCode(loginCode);
					}else {
						showLoginMenuMessage.setMessageToUser("Mot de passe erroné.");
					}

					Ntro.messages().send(showLoginMenuMessage);
				}
			});
		}
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, UserSendsPasswordMessage message) {
		T.call(this);
		
	}

}

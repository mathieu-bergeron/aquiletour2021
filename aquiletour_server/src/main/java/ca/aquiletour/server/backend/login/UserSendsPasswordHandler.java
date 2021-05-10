package ca.aquiletour.server.backend.login;

import ca.aquiletour.core.messages.user.UserSendsPassword;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.server.RegisteredSockets;
import ca.aquiletour.server.backend.users.UserUpdater;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendMessageHandlerError;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;
import ca.ntro.messages.ntro_messages.NtroSetUserMessage;
import ca.ntro.services.Ntro;
import ca.ntro.users.NtroSession;

public class UserSendsPasswordHandler extends BackendMessageHandler<UserSendsPassword> {

	@Override
	public void handleNow(ModelStoreSync modelStore, UserSendsPassword message) throws BackendMessageHandlerError {
		T.call(this);

		String authToken = message.getUser().getAuthToken();
		String userId = message.getUser().getId();

		User userToRegister = null;

		NtroSession session = InitializeSessionHandler.getStoredSession(modelStore, authToken);
		
		if(UserUpdater.isUserPasswordValid(modelStore, message.getPassword(), message.getUser())) {
			
			userToRegister = UserSendsLoginCodeHandler.registerStudentOrTeacher(modelStore, authToken,  userId, session);
			
		}else {
			
			userToRegister = (User) message.getUser();
		}

		Ntro.currentSession().setUser(userToRegister);

		NtroSetUserMessage setUserNtroMessage = Ntro.messages().create(NtroSetUserMessage.class);
		setUserNtroMessage.setUser(userToRegister);
		RegisteredSockets.sendMessageToUser(userToRegister, setUserNtroMessage);
		
		for(NtroMessage delayedMessage : message.getDelayedMessages()) {
			Ntro.messages().send(delayedMessage);
		}
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, UserSendsPassword message) {
		T.call(this);
		
	}

}

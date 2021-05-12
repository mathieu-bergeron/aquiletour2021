package ca.aquiletour.server.backend.login;

import java.util.List;

import ca.aquiletour.core.messages.user.UserSendsLoginCodeMessage;
import ca.aquiletour.core.models.session.SessionData;
import ca.aquiletour.core.models.user.Admin;
import ca.aquiletour.core.models.user.Student;
import ca.aquiletour.core.models.user.StudentGuest;
import ca.aquiletour.core.models.user.Teacher;
import ca.aquiletour.core.models.user.TeacherGuest;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.server.AquiletourConfig;
import ca.aquiletour.server.RegisteredSockets;
import ca.aquiletour.server.backend.queue.QueueUpdater;
import ca.aquiletour.server.backend.users.UserManager;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;
import ca.ntro.messages.ntro_messages.NtroSetUserMessage;
import ca.ntro.services.Ntro;
import ca.ntro.users.NtroSession;

public class UserSendsLoginCodeHandler extends BackendMessageHandler<UserSendsLoginCodeMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, UserSendsLoginCodeMessage message) {
		T.call(this);

		String loginCode = message.getLoginCode().replace(" ", "");
		String authToken = message.getUser().getAuthToken();
		String userId = message.getUser().getId();

		User userToRegister = null;

		NtroSession session = SessionManager.getStoredSession(modelStore, authToken);
		SessionData sessionData = null;
		
		if(session != null) {
			sessionData = (SessionData) session.getSessionData();
		}

		if(sessionData != null 
				&& sessionData.getLoginCode().equals(loginCode)) {
			
			userToRegister = SessionManager.createAuthenticatedUser(modelStore, authToken,  userId, session);
			
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
	public void handleLater(ModelStoreSync modelStore, UserSendsLoginCodeMessage message) {
		T.call(this);
	}

}

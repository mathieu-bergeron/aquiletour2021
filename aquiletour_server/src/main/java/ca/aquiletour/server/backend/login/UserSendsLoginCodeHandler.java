package ca.aquiletour.server.backend.login;

import ca.aquiletour.core.messages.UserSendsLoginCodeMessage;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.server.RegisteredSockets;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.ntro_messages.SetUserNtroMessage;
import ca.ntro.services.Ntro;
import ca.ntro.users.Session;

public class UserSendsLoginCodeHandler extends BackendMessageHandler<UserSendsLoginCodeMessage> {

	@Override
	public void handle(ModelStoreSync modelStore, UserSendsLoginCodeMessage message) {
		String loginCode = message.getLoginCode().replace(" ", "");
		String authToken = message.getUser().getAuthToken();
		String userId = message.getUser().getId();

		User userToRegister = null;

		Session session = AuthenticateSessionUserHandler.getStoredSession(modelStore, authToken);
		
		if(session != null 
				&& session.getLoginCode().equals(loginCode)) {
			
			userToRegister = registerStudentOrTeacher(modelStore, authToken,  userId, session);
			
		}else {
			
			userToRegister = (User) message.getUser();
		}

		Ntro.userService().registerCurrentUser(userToRegister);

		SetUserNtroMessage setUserNtroMessage = Ntro.messages().create(SetUserNtroMessage.class);
		setUserNtroMessage.setUser(userToRegister);
		RegisteredSockets.sendMessageToUser(userToRegister, setUserNtroMessage);
	}

	private User registerStudentOrTeacher(ModelStoreSync modelStore, String authToken, String userId, Session session) {

		User existingUser = modelStore.getModel(User.class, "TODO", userId);
		
		User sessionUser = existingUser.toSessionUser();
		
		sessionUser.setAuthToken(authToken);
		existingUser.setAuthToken(authToken);
		
		session.setUser(sessionUser);
		modelStore.save(session);

		return existingUser;
	}

}

package ca.aquiletour.server.backend.login;

import ca.aquiletour.core.messages.UserSendsLoginCodeMessage;
import ca.aquiletour.core.models.users.User;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.users.Session;

public class UserSendsLoginCodeHandler extends BackendMessageHandler<UserSendsLoginCodeMessage> {

	@Override
	public void handle(ModelStoreSync modelStore, UserSendsLoginCodeMessage message) {
		String loginCode = message.getLoginCode();
		String authToken = message.getUser().getAuthToken();
		String userId = message.getUser().getId();

		T.values("loginCode", loginCode);

		User userToRegister = null;


		Session session = AuthenticateSessionUserHandler.getStoredSession(modelStore, authToken);
		
		if(session != null) {
			
			userToRegister = registerStudentOrTeacher(modelStore, authToken,  userId, session);
			
		}else {
			
			userToRegister = (User) message.getUser();
		}

		Ntro.userService().registerCurrentUser(userToRegister);
	}

	private User registerStudentOrTeacher(ModelStoreSync modelStore, String authToken, String userId, Session session) {

		User existingUser = modelStore.getModel(User.class, "TODO", userId);
		
		User sessionUser = existingUser.toSessionUser();
		
		sessionUser.setAuthToken(authToken);
		
		session.setUser(sessionUser);
		modelStore.save(session);

		return existingUser;
	}

}
